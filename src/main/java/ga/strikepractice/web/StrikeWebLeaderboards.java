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

@Route("")
@CssImport("./styles/custom.css")
public class StrikeWebLeaderboards extends FlexLayout {

    private static final String[] eloKits;

    static {
        // TODO: fetch from config or database
        eloKits = new String[]{"nodebuff", "builduhc"};
    }

    public StrikeWebLeaderboards() {
        this.getStyle().set("flex-wrap", "wrap");
        this.getStyle().set("justify-content", "flex-start");
        for (StandardDataColumn dataColumn : StandardDataColumn.values()) {
            Leaderboard leaderboard = new Leaderboard(dataColumn, new LeaderboardGrid(getData(dataColumn.getColumnName())));
            add(leaderboard);
        }
        for (String eloKit : eloKits) {
            Leaderboard leaderboard = new Leaderboard(eloKit, new LeaderboardGrid(getData(eloKit)));
            add(leaderboard);
        }
    }

    private List<PlayerDataEntry> getData(String dataColumn) {
        Query query = Query.builder().dataColumn(dataColumn).limit(10).build();
        QueryResult result = query.execute();
        return result.getData();
    }

}

