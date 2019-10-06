package ga.strikepractice.web.leaderboard;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import ga.strikepractice.web.data.StandardDataColumn;

public class Leaderboard extends Div {

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
