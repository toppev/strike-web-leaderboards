package ga.strikepractice.web.data;

import ga.strikepractice.web.data.querier.DataQuerier;
import ga.strikepractice.web.data.querier.FakeDataQuerier;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Data
@Builder
public class Query {

    private static DataQuerier defaultDataQuerier = new FakeDataQuerier();

    @Builder.Default
    private int limit = 10;
    @NonNull
    private String dataColumn;

    public QueryResult execute() {
        List<PlayerDataEntry> entries = fetchData().entrySet().stream().map(e -> new PlayerDataEntry(e.getKey(), e.getValue())).collect(Collectors.toList());
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
