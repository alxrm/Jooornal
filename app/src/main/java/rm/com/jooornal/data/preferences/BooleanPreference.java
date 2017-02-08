package rm.com.jooornal.data.preferences;

import android.content.SharedPreferences;

/**
 * класс, описывающий настройки переключателей(вкл./выкл.)
 */
public final class BooleanPreference {

  private final SharedPreferences preferences;
  private final String key;
  private final Boolean defaultValue;

  public BooleanPreference(SharedPreferences preferences, String key) {
    this(preferences, key, false);
  }

  public BooleanPreference(SharedPreferences preferences, String key, Boolean defaultValue) {
    this.preferences = preferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  /**
   * получить текущее значение настройки
   *
   * @return флаг с текущим значением
   */
  public boolean get() {
    return preferences.getBoolean(key, defaultValue);
  }

  /**
   * узнать, выставлялась ли настройка ранее
   *
   * @return флаг, обозначающий, что такая настройка уже выставлялась
   */
  public boolean isSet() {
    return preferences.contains(key);
  }

  /**
   * установить значение настройки
   *
   * @param value новое значение
   */
  public void set(boolean value) {
    preferences.edit().putBoolean(key, value).apply();
  }

  /**
   * удалить настройку из хранилища
   */
  public void delete() {
    preferences.edit().remove(key).apply();
  }
}