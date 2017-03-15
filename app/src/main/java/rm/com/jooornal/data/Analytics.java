package rm.com.jooornal.data;

import java.util.Map;

public interface Analytics {
  void send(Map<String, String> params);
}
