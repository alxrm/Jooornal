package rm.com.jooornal.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import rm.com.jooornal.data.JoornalDatabase;
import rm.com.jooornal.util.Guids;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess")
@Table(database = JoornalDatabase.class)
public final class Note extends BaseModel implements Parcelable {
  @PrimaryKey public String id = Guids.randomGuid();
  @ForeignKey(stubbedRelationship = true)  public Student student;
  @Column public String name = "";
  @Column public String text = "";
  @Column public long time = System.currentTimeMillis();
  @Column public long due = 0L;
  @Column public long noteEventId = -1L;

  public Note() {}

  protected Note(Parcel in) {
    id = in.readString();
    student = in.readParcelable(Student.class.getClassLoader());
    name = in.readString();
    text = in.readString();
    time = in.readLong();
    due = in.readLong();
    noteEventId = in.readLong();
  }

  public static final Creator<Note> CREATOR = new Creator<Note>() {
    @Override public Note createFromParcel(Parcel in) {
      return new Note(in);
    }

    @Override public Note[] newArray(int size) {
      return new Note[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeParcelable(student, flags);
    dest.writeString(name);
    dest.writeString(text);
    dest.writeLong(time);
    dest.writeLong(due);
    dest.writeLong(noteEventId);
  }
}
