package ga.strikepractice.web.data.querier;

import java.util.Map;

public interface DataQuerier {

    Map<String, Integer> querySorted(String key, int limit);

}
