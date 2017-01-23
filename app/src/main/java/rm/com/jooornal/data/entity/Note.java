package rm.com.jooornal.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import rm.com.jooornal.data.JoornalDatabase;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess")
@Table(database = JoornalDatabase.class)
public final class Note extends BaseModel implements Parcelable {
  @PrimaryKey(autoincrement = true) public long id = System.currentTimeMillis();
  @ForeignKey(stubbedRelationship = true)  public Student student;
  @Column public String name = "";
  @Column public String text = "";
  @Column public long due = 0L;

  public Note() {}

  protected Note(Parcel in) {
    id = in.readLong();
    student = in.readParcelable(Student.class.getClassLoader());
    name = in.readString();
    text = in.readString();
    due = in.readLong();
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
    dest.writeLong(id);
    dest.writeParcelable(student, flags);
    dest.writeString(name);
    dest.writeString(text);
    dest.writeLong(due);
  }
}
