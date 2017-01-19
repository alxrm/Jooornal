package rm.com.jooornal.data.entity;

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
public final class Sms extends BaseModel {
  @PrimaryKey public long id = System.currentTimeMillis();
  @ForeignKey public Student student;
  @ForeignKey public Phone phone;
  @Column public long time;
  @Column public String text;
}
