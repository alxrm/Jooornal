package rm.com.jooornal.data.provider;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.concurrent.ExecutorService;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Phone_Table;

/**
 * Created by alex
 */

public class PhoneProvider extends ParametrisedAsyncProvider<String, Phone> {

  public PhoneProvider(@NonNull ExecutorService executor, @NonNull Handler mainThreadHook) {
    super(executor, mainThreadHook);
  }

  @Override protected Phone get() {
    return SQLite.select()
        .from(Phone.class)
        .where(new SQLCondition[] { Phone_Table.phoneNumber.eq(currentParam) })
        .querySingle();
  }
}
