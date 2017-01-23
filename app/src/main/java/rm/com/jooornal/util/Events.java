package rm.com.jooornal.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import java.util.concurrent.TimeUnit;

/**
 * Created by alex
 */

public final class Events {

  private Events() {
  }

  public static long addEventToCalender(ContentResolver cr, String title, String addInfo,
      String place, long startDate, boolean isBirthday) {
    final String eventUriStr = "content://com.android.calendar/events";
    final ContentValues event = new ContentValues();
    // id, We need to choose from our mobile for primary its 1
    event.put(CalendarContract.Events.CALENDAR_ID, 1);
    event.put(CalendarContract.Events.TITLE, title);
    event.put(CalendarContract.Events.DESCRIPTION, addInfo);
    event.put(CalendarContract.Events.EVENT_LOCATION, place);
    event.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC/GMT +4:00");

    if (isBirthday) {
      event.put(CalendarContract.Events.RRULE, "FREQ=YEARLY");
    }

    event.put(CalendarContract.Events.DTSTART, startDate + TimeUnit.DAYS.toMillis(1) / 2);
    event.put(CalendarContract.Events.DTEND, startDate + TimeUnit.DAYS.toMillis(1));
    event.put(CalendarContract.Events.ALL_DAY, 1);
    event.put(CalendarContract.Events.HAS_ALARM, 1);

    final Uri eventUri = cr.insert(Uri.parse(eventUriStr), event);
    Conditions.checkNotNull(eventUri);

    return Long.parseLong(eventUri.getLastPathSegment());
  }
}
