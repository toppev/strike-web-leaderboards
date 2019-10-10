package ga.strikepractice.web.leaderboard;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import ga.strikepractice.web.data.PlayerDataEntry;

import java.util.List;

public class LeaderboardGrid extends Grid<PlayerDataEntry> {

    private static final int SKIN_PIXELS = 20;

    public LeaderboardGrid(List<PlayerDataEntry> entries) {
        this();
        setItems(entries);
    }

    public LeaderboardGrid() {
        getStyle().set("flex", "none");
        setWidth("360px");
        setHeight("450px");
        setSelectionMode(SelectionMode.NONE);
        addColumn(PlayerDataEntry::getRank).setHeader("Rank").setFlexGrow(0).setWidth("70px");
        addComponentColumn(e -> new Image("https://minotar.net/avatar/" + e.getName() + "/" + SKIN_PIXELS, "")).setFlexGrow(0).setWidth(SKIN_PIXELS + 20 + "px");
        addColumn(PlayerDataEntry::getName).setHeader("Username");
        addColumn(PlayerDataEntry::getValue).setHeader("Amount");
    }
}
