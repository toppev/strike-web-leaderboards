package ga.strikepractice.web.data;

import java.util.Map;

public interface DataQuerier {

    Map<String, Integer> querySorted(String key, int limit);

}
