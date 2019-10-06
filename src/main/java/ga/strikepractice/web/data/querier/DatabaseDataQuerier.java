package ga.strikepractice.web.data.querier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseDataQuerier implements DataQuerier {

    private static final String STATS_TABLE = "stats";

    static {
        // TODO connect to db
    }

    @Override
    public Map<String, Integer> querySorted(String key, int limit) {

        return null;
    }


    private LinkedHashMap<String, Integer> queryDatabase(String column, int limit) throws SQLException {
        LinkedHashMap<String, Integer> top = new LinkedHashMap<>();
        String query = "SELECT username, ? from " + STATS_TABLE + " order by ? desc limit ?";
        PreparedStatement ps = null; // TODO;

        ps.setString(1, column);
        ps.setString(2, column);
        ps.setInt(3, limit);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                String player = rs.getString("username");
                if (!top.containsKey(player)) {
                    top.put(player, rs.getInt(column));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top;
    }
}
