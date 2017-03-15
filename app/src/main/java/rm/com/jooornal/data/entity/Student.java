package rm.com.jooornal.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import java.util.List;
import rm.com.jooornal.data.JoornalDatabase;
import rm.com.jooornal.util.Guids;

import static rm.com.jooornal.constant.Events.EVENT_NULL_ID;

/**
 * класс, описывающий данные студента
 */
@SuppressWarnings("WeakerAccess") @Table(database = JoornalDatabase.class)
public final class Student extends BaseModel implements Parcelable {
  @PrimaryKey public String id = Guids.randomGuid();
  @Column public String name = "";
  @Column public String surname = "";
  @Column public String patronymic = "";
  @Column public long birthDate = 0L;
  @Column public long birthDayEventId = EVENT_NULL_ID;

  List<Phone> phones;
  List<Sms> smsList;
  List<Call> calls;
  List<Note> notes;

  public static final Creator<Student> CREATOR = new Creator<Student>() {
    @Override public Student createFromParcel(Parcel in) {
      return new Student(in);
    }

    @Override public Student[] newArray(int size) {
      return new Student[size];
    }
  };

  public Student() {
  }

  protected Student(Parcel in) {
    id = in.readString();
    name = in.readString();
    surname = in.readString();
    patronymic = in.readString();
    birthDate = in.readLong();
    birthDayEventId = in.readLong();
    phones = in.createTypedArrayList(Phone.CREATOR);
    smsList = in.createTypedArrayList(Sms.CREATOR);
    calls = in.createTypedArrayList(Call.CREATOR);
    notes = in.createTypedArrayList(Note.CREATOR);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(name);
    dest.writeString(surname);
    dest.writeString(patronymic);
    dest.writeLong(birthDate);
    dest.writeLong(birthDayEventId);
    dest.writeTypedList(phones);
    dest.writeTypedList(smsList);
    dest.writeTypedList(calls);
    dest.writeTypedList(notes);
  }

  @OneToMany(methods = { OneToMany.Method.ALL }, variableName = "phones")
  public List<Phone> getPhones() {
    if (phones == null || phones.isEmpty()) {
      phones = SQLite.select().from(Phone.class).where(Phone_Table.student_id.eq(id)).queryList();
    }

    return phones;
  }

  @OneToMany(methods = { OneToMany.Method.ALL }, variableName = "smsList")
  public List<Sms> getSmsList() {
    if (smsList == null || smsList.isEmpty()) {
      smsList = SQLite.select()
          .from(Sms.class)
          .where(Sms_Table.student_id.eq(id))
          .orderBy(Sms_Table.time, false)
          .queryList();
    }

    return smsList;
  }

  @OneToMany(methods = { OneToMany.Method.ALL }, variableName = "calls")
  public List<Call> getCalls() {
    if (calls == null || calls.isEmpty()) {
      calls = SQLite.select()
          .from(Call.class)
          .where(Call_Table.student_id.eq(id))
          .orderBy(Call_Table.time, false)
          .queryList();
    }

    return calls;
  }

  @OneToMany(methods = { OneToMany.Method.ALL }, variableName = "notes")
  public List<Note> getNotes() {
    if (notes == null || notes.isEmpty()) {
      notes = SQLite.select().from(Note.class).where(Note_Table.student_id.eq(id)).queryList();
    }

    return notes;
  }

  public void refreshPhones() {
    phones = SQLite.select().from(Phone.class).where(Phone_Table.student_id.eq(id)).queryList();
  }
}
