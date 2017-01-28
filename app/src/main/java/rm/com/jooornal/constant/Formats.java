package rm.com.jooornal.constant;

import com.redmadrobot.inputmask.helper.Mask;

public interface Formats {
  String PATTERN_TIMED_DATE = "d MMM в H:mm";
  String PATTERN_REGULAR_DATE = "d MMM yyyy г.";
  String PHONE_PREFIX_HOME = "+74842";
  String PHONE_PREFIX_COUNTRY = "+7";
  String PHONE_PREFIX_HOME_REGEX = "\\+74842";
  String PHONE_PREFIX_COUNTRY_REGEX = "\\+7";

  Mask MASK_PHONE_CELL = new Mask("+7 ([000]) [000]-[00]-[00]");
  Mask MASK_PHONE_HOME = new Mask("[000]-[000]");

  int PHONE_LENGTH_FULL = 11;
  int PHONE_LENGTH_HOME = 6;
}
