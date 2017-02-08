package rm.com.jooornal.inject.qualifiers;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * аннотация для настроек уведомлений о днях рождения
 */
@Qualifier @Retention(RUNTIME) public @interface BirthdayNotifications {
}