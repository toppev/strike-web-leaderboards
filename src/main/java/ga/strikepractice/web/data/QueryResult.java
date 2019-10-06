package ga.strikepractice.web.data;

import lombok.Data;

import java.util.List;

@Data
public class QueryResult {

    private final List<PlayerDataEntry> data;

}
