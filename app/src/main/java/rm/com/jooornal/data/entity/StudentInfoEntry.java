package rm.com.jooornal.data.entity;

/**
 * Created by alex
 */

public final class StudentInfoEntry {

  /**
   * тип элемента описания студента, может быть двух типов:
   *
   * 1 - title
   * 2 - text
   */
  public final int type;
  public final String text;

  public StudentInfoEntry(int type, String text) {
    this.type = type;
    this.text = text;
  }
}
