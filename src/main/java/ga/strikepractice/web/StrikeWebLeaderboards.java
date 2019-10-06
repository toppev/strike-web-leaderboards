package ga.strikepractice.web;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ga.strikepractice.web.data.PlayerDataEntry;
import ga.strikepractice.web.data.Query;
import ga.strikepractice.web.data.QueryResult;
import ga.strikepractice.web.data.StandardDataColumn;

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
        Query query = Query.builder().dataColumn(dataColumn).dataType(Integer.class).limit(10).build();
        QueryResult result = query.execute();
        return result.getData();
    }

    public static class Leaderboard extends Div {

        public Leaderboard(StandardDataColumn dataColumn, LeaderboardGrid grid) {
            this(dataColumn.toString().replace("_", " "), grid);
        }

        public Leaderboard(String dataColumn, LeaderboardGrid grid) {
            getElement().getStyle().set("position", "relative");
            Label title = new Label(dataColumn);
            title.getStyle().set("width", "100%");
            title.getStyle().set("text-align", "center");
            title.getStyle().set("display", "inline-block");
            add(title, grid);
        }
    }
}

