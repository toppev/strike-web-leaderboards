package ga.strikepractice.web.data.querier;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Basic SQL database {@link DataQuerier}
 * <p>
 * Environment variables:
 * <ul>
 *     <li>STRIKE_WEB_HOST - the host</li>
 *     <li>STRIKE_WEB_PORT - the port</li>
 *     <li>STRIKE_WEB_DATABASE - the db name, by default "StrikePractice"</li>
 *     <li>STRIKE_WEB_USER - the user</li>
 *     <li>STRIKE_WEB_PASSWORD - the password</li>
 * </ul>
 */
@Slf4j
public class DatabaseDataQuerier implements DataQuerier {

    private final MySQLConnector connector = new MySQLConnector();

    public DatabaseDataQuerier() {
        connector.connect();
    }

    @Override
    public Map<String, Integer> querySorted(String key, int limit) {
        try {
            return queryDatabase(key, limit);
        } catch (SQLException e) {
            logger.error("Failed to query database key=" + key + ", limit=" + limit, e);
        }
        return new HashMap<>();
    }


    private LinkedHashMap<String, Integer> queryDatabase(String column, int limit) throws SQLException {
        LinkedHashMap<String, Integer> top = new LinkedHashMap<>();
        // TODO: Should cache this PreparedStatement, though it's  called from other threads so not sure if that's a good idea
        PreparedStatement ps = connector.conn.prepareStatement("SELECT username," + column + " from " + MySQLConnector.STATS_TABLE + " order by ? desc limit ?");
        // Indexes start at 1
        ps.setString(1, column);
        ps.setInt(2, limit);

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


    class MySQLConnector {

        private static final String STATS_TABLE = "stats";

        private Connection conn;

        private void connect() {
            String host = getVariableOrDefault("database.host","STRIKE_WEB_HOST", "localhost");
            String port = getVariableOrDefault("database.port", "STRIKE_WEB_PORT", "3306");
            String database = getVariableOrDefault("database.name", "STRIKE_WEB_DATABASE", "strikepractice");
            // Although I don't recommend running as root
            String user = getVariableOrDefault("database.user","STRIKE_WEB_USER", "root");
            String password = getVariableOrDefault("database.password","STRIKE_WEB_PASSWORD", "password123");
            logger.info("host: " + host);
            logger.info("port: " + port);
            logger.info("database: " + database);
            logger.info("user: " + user);
            logger.info("password: " + (password.equals("password123") ? "password123 (DEFAULT PASSWORD, CHANGE IT)" : ("<" + password.length() + " characters>")));
            try {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            } catch (SQLException e) {
                logger.error("Failed to connect to the StrikePractice database", e);
            }
        }

        private String getVariableOrDefault(String property, String envVariable, String defaultValue) {
            String prop = System.getProperty(property);
            String env = System.getenv(envVariable);
            // first property, then environment variable and lastly default value
            return prop != null ? prop : env != null ? env : defaultValue;
        }
    }
}
