package rm.com.jooornal.data.entity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import java.util.List;
import rm.com.jooornal.data.Call_Table;
import rm.com.jooornal.data.JoornalDatabase;
import rm.com.jooornal.data.Note_Table;
import rm.com.jooornal.data.Phone_Table;
import rm.com.jooornal.data.Sms_Table;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess") @Table(database = JoornalDatabase.class)
public final class Student extends BaseModel {
  @PrimaryKey public long id = System.currentTimeMillis();
  @Column public String name;
  @Column public String surname;
  @Column public String patronymic;
  @Column public long birthDate;

  List<Phone> phones;
  List<Sms> smsList;
  List<Call> calls;
  List<Note> notes;

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
      smsList = SQLite.select().from(Sms.class).where(Sms_Table.student_id.eq(id)).queryList();
    }

    return smsList;
  }

  @OneToMany(methods = { OneToMany.Method.ALL }, variableName = "calls")
  public List<Call> getCalls() {
    if (calls == null || calls.isEmpty()) {
      calls = SQLite.select().from(Call.class).where(Call_Table.student_id.eq(id)).queryList();
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
}
