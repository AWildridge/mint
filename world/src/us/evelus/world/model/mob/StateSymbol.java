package us.evelus.world.model.mob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public final class StateSymbol {

    private static final Logger logger = LoggerFactory.getLogger(StateSymbol.class);
    private static final Map<String, StateSymbol> symbols = new HashMap<>();

    private final String name;
    private final int id;

    public StateSymbol(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public static StateSymbol fromName(String name) {
        if(!symbols.containsKey(name)) {
            throw new NullPointerException("no such symbol exists");
        }
        return symbols.get(name);
    }

    public static void register(StateSymbol symbol) {
        symbols.put(symbol.getName(), symbol);
    }

    public static void load(String database) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database)) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM state_symbols");
            while(results.next()) {
                StateSymbol symbol = new StateSymbol(results.getInt("STATE_ID"), results.getString("NAME"));
                register(symbol);
            }
        } catch(SQLException ex) {
            logger.error("Failed to load the state symbol database", ex);
        }

        logger.info("Loaded " + symbols.size() + " state symbols...");
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
