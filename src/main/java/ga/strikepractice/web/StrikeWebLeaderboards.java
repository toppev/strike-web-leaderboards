package ga.strikepractice.web;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;
import ga.strikepractice.web.data.PlayerDataEntry;
import ga.strikepractice.web.data.Query;
import ga.strikepractice.web.data.QueryResult;
import ga.strikepractice.web.data.StandardDataColumn;
import ga.strikepractice.web.leaderboard.Leaderboard;
import ga.strikepractice.web.leaderboard.LeaderboardGrid;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Route("")
@CssImport("./styles/custom.css")
public class StrikeWebLeaderboards extends FlexLayout {

    private static final String[] eloKits;

    static {
        // TODO: fetch from config or database
        eloKits = new String[]{};
    }

    public StrikeWebLeaderboards() {
        this.getStyle().set("flex-wrap", "wrap");
        this.getStyle().set("justify-content", "flex-start");
        for (StandardDataColumn dataColumn : StandardDataColumn.values()) {
            Leaderboard leaderboard = createLeaderboard(dataColumn.toString(), dataColumn.getColumnName());
            add(leaderboard);
        }
        for (String eloKit : eloKits) {
            Leaderboard leaderboard = createLeaderboard(eloKits.toString().toUpperCase(), eloKit);
            add(leaderboard);
        }
    }

    private Leaderboard createLeaderboard(String name, String column) {
        Leaderboard leaderboard = new Leaderboard(column, new LeaderboardGrid());
        CompletableFuture.runAsync(() -> {
            leaderboard.getLeaderboardGrid().setItems(getData(column));
        });
        return leaderboard;
    }

    private List<PlayerDataEntry> getData(String dataColumn) {
        Query query = Query.builder().dataColumn(dataColumn).limit(10).build();
        QueryResult result = query.execute();
        return result.getData();
    }

}

