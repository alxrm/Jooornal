package rm.com.jooornal.data;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = JoornalDatabase.NAME, version = JoornalDatabase.VERSION,
    generatedClassSeparator = JoornalDatabase.SEPARATOR) public final class JoornalDatabase {
  static final String NAME = "main_db";
  static final int VERSION = 1;
  static final String SEPARATOR = "_";
}
