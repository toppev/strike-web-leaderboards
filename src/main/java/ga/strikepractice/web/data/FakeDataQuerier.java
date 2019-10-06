package ga.strikepractice.web.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * {@link DataQuerier} used for testing
 */
public class FakeDataQuerier implements DataQuerier {

    /**
     * Sample usernames used in this {@link DataQuerier}
     */
    private static final String[] SAMPLE_NAMES = new String[]{"James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas"};
    private static final Random random = new Random();

    @Override
    public Map<String, Integer> querySorted(String key, int limit) {
        Map<String, Integer> map = new LinkedHashMap<>();
        // Go down from random number between 100-199
        Integer currentValue = random.nextInt(100) + 100;
        // Generate random data
        // The map size may be less than the limit
        for (int i = 0; i < limit; i++) {
            // Decrease a bit
            currentValue -= random.nextInt(10);
            String name = i >= SAMPLE_NAMES.length ? SAMPLE_NAMES[i % SAMPLE_NAMES.length] : SAMPLE_NAMES[i];
            if (!map.containsKey(name)) {
                map.put(name, currentValue);
            }
        }
        return map;
    }
}
