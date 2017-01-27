package rm.com.jooornal.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.TimeUnit;

import static rm.com.jooornal.constant.Events.CALENDAR_DEFAULT;
import static rm.com.jooornal.constant.Events.EVENT_FREQUENCY_YEARLY;
import static rm.com.jooornal.constant.Events.EVENT_TIMEZONE_MSK;
import static rm.com.jooornal.constant.Events.EVENT_URI_PATH;

/**
 * Created by alex
 */

public final class Events {

  private Events() {
  }

  public static long addEventToCalender(@NonNull ContentResolver cr, @NonNull String title,
      @Nullable String description, long startDate, boolean isBirthday) {
    final ContentValues event = packEvent(title, description, startDate, isBirthday);
    final Uri eventUri = cr.insert(Uri.parse(EVENT_URI_PATH), event);
    Conditions.checkNotNull(eventUri);

    return Long.parseLong(eventUri.getLastPathSegment());
  }

  public static void updateCalendarEvent(@NonNull ContentResolver cr, long eventId,
      @NonNull String title, @Nullable String description, long startDate, boolean isBirthday) {
    final ContentValues event = packEvent(title, description, startDate, isBirthday);
    final Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);

    cr.update(updateUri, event, null, null);
  }

  public static void deleteCalendarEvent(@NonNull ContentResolver cr, long eventId) {
    final Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
    cr.delete(deleteUri, null, null);
  }

  @NonNull
  private static ContentValues packEvent(@NonNull String title, @Nullable String description,
      long startDate, boolean isBirthday) {
    final ContentValues event = new ContentValues();

    event.put(CalendarContract.Events.CALENDAR_ID, CALENDAR_DEFAULT);
    event.put(CalendarContract.Events.TITLE, title);
    event.put(CalendarContract.Events.DESCRIPTION, description);
    event.put(CalendarContract.Events.EVENT_TIMEZONE, EVENT_TIMEZONE_MSK);

    if (isBirthday) {
      event.put(CalendarContract.Events.RRULE, EVENT_FREQUENCY_YEARLY);
    }

    event.put(CalendarContract.Events.DTSTART, startDate);
    event.put(CalendarContract.Events.DTEND, startDate + TimeUnit.DAYS.toMillis(1));
    event.put(CalendarContract.Events.ALL_DAY, 1);
    event.put(CalendarContract.Events.HAS_ALARM, 1);

    return event;
  }
}
