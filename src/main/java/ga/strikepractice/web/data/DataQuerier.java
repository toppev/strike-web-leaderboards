package ga.strikepractice.web.data;

import java.util.Map;

public interface DataQuerier {

    <T> Map<String, T> querySorted(String key, Class<T> type, int limit);

}
