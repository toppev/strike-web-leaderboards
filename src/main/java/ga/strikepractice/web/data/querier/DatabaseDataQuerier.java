package ga.strikepractice.web.data.querier;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Basic SQL database {@link DataQuerier}
 */
@Slf4j
public class DatabaseDataQuerier implements DataQuerier {

    private static final String STATS_TABLE = "stats";

    private static Connection conn;

    static {
        String host = System.getenv("STRIKE_WEB_HOST");
        String port = System.getenv("STRIKE_WEB_PORT");
        String database = System.getenv("STRIKE_WEB_DATABASE");
        if (database == null || database.isEmpty()) {
            // Default database name
            database = "StrikePractice";
        }
        String user = System.getenv("STRIKE_WEB_USER");
        String password = System.getenv("STRIKE_WEB_PASSWORD");
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
        } catch (SQLException e) {
            logger.error("Failed to connect to the StrikePractice database", e);
        }
    }

    @Override
    public Map<String, Integer> querySorted(String key, int limit) {
        try {
            return queryDatabase(key, limit);
        } catch (SQLException e) {
            logger.error("Failed to query database", e);
        }
        return new HashMap<>();
    }


    private LinkedHashMap<String, Integer> queryDatabase(String column, int limit) throws SQLException {
        LinkedHashMap<String, Integer> top = new LinkedHashMap<>();
        String query = "SELECT username, ? from " + STATS_TABLE + " order by ? desc limit ?";
        PreparedStatement ps = conn.prepareStatement(query);
        // Indexes start at 1
        ps.setString(1, column);
        ps.setString(2, column);
        ps.setInt(3, limit);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String player = rs.getString("username");
                if (!top.containsKey(player)) {
                    top.put(player, rs.getInt(column));
                }
            }
        }
        return top;
    }
}
