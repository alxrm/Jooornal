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
public final class Phone extends BaseModel implements Parcelable {
  @PrimaryKey public long id = System.currentTimeMillis();
  @ForeignKey(stubbedRelationship = true) public Student student;
  @Column public String alias = "";
  @Column public String phoneNumber = "";

  public Phone() {}

  protected Phone(Parcel in) {
    id = in.readLong();
    student = in.readParcelable(Student.class.getClassLoader());
    alias = in.readString();
    phoneNumber = in.readString();
  }

  public static final Creator<Phone> CREATOR = new Creator<Phone>() {
    @Override public Phone createFromParcel(Parcel in) {
      return new Phone(in);
    }

    @Override public Phone[] newArray(int size) {
      return new Phone[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeParcelable(student, flags);
    dest.writeString(alias);
    dest.writeString(phoneNumber);
  }
}
