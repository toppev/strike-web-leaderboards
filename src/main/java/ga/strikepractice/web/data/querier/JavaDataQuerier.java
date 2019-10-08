package ga.strikepractice.web.data.querier;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This {@link DataQuerier} can query if it has same {@link ClassLoader} as the plugin.
 */
public class JavaDataQuerier implements DataQuerier {

    @Override
    public Map<String, Integer> querySorted(String key, int limit) {
        // The public api doesn't have the stuff needed so just use reflections
        try {
            Class defaultPlayerStats = Class.forName("ga.strikepractice.stats.DefaultPlayerStats");
            Field field = defaultPlayerStats.getDeclaredField("top");
            field.setAccessible(true);
            // Just cast it and catch the cast if it fails for some reason
            Map<String, LinkedHashMap<String, Double>> map = (Map) field.get(null);
            Map<String, Double> top = map.get(key);
            // Convert Map<String, Double> to Map<String, Integer> and return it
            Map result = top.entrySet().stream().collect(Collectors.toMap(Function.identity(), e -> e.getValue().intValue()));
            return result;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | ClassCastException e) {
            throw new IllegalStateException("Can not use " + this.getClass().getName() + ". Couldn't find StrikePractice statistics", e);
        }
    }
}
