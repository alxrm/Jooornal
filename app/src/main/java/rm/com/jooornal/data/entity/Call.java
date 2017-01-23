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
public final class Call extends BaseModel implements Parcelable {
  @PrimaryKey(autoincrement = true) public long id = System.currentTimeMillis();
  @ForeignKey(stubbedRelationship = true) public Student student;
  @ForeignKey public Phone phone;
  @Column public long time = 0L;

  public Call() {}

  protected Call(Parcel in) {
    id = in.readLong();
    student = in.readParcelable(Student.class.getClassLoader());
    phone = in.readParcelable(Phone.class.getClassLoader());
    time = in.readLong();
  }

  public static final Creator<Call> CREATOR = new Creator<Call>() {
    @Override public Call createFromParcel(Parcel in) {
      return new Call(in);
    }

    @Override public Call[] newArray(int size) {
      return new Call[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeParcelable(student, flags);
    dest.writeParcelable(phone, flags);
    dest.writeLong(time);
  }
}
