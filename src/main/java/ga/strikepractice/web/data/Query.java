package ga.strikepractice.web.data;

import ga.strikepractice.web.data.querier.DataQuerier;
import ga.strikepractice.web.data.querier.DatabaseDataQuerier;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

@Data
@Builder
public class Query {

    private static DataQuerier defaultDataQuerier = new DatabaseDataQuerier();

    @Builder.Default
    private int limit = 10;
    @NonNull
    private String dataColumn;

    public QueryResult execute() {
        Map<String, Integer> map = fetchData();
        List<PlayerDataEntry> entries = new ArrayList<>();
        int rank = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            entries.add(new PlayerDataEntry(entry.getKey(), entry.getValue(), ++rank));
        }
        return new QueryResult(entries);
    }

    /**
     * Executes the actual query
     *
     * @return a sorted map with the usernames as keys and the corresponding values
     */
    private Map<String, Integer> fetchData() {
        return defaultDataQuerier.querySorted(dataColumn, limit);
    }

    public void executeAsync(Consumer<QueryResult> resultConsumer) {
        ForkJoinPool.commonPool().submit(() -> resultConsumer.accept(execute()));
    }


}
