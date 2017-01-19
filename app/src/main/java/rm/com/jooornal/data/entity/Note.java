package rm.com.jooornal.data.entity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import rm.com.jooornal.data.JoornalDatabase;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess")
@Table(database = JoornalDatabase.class)
public final class Note {
  @PrimaryKey public long id = System.currentTimeMillis();
  @ForeignKey public Student student;
  @Column public String name;
  @Column public String text;
  @Column public long due;
}
