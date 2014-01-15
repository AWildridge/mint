package us.evelus.world.model.region;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.model.Position;

import java.sql.*;

public final class RegionLoader {

    private static final Logger logger = LoggerFactory.getLogger(RegionLoader.class);
    private static Connection connection;

    private static PreparedStatement queryStatement;

    public static void init(String database) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        try {
            // Initialize the connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:" + database);

            // Prepare the query statement
            queryStatement = connection.prepareStatement("SELECT obj_data, tile_data, area_data, trigger_data FROM regions WHERE loc = ? LIMIT 1");
        } catch(SQLException ex) {
            logger.error("Failed to open a connection to the database", ex);
            return;
        }

        logger.info("Region loader successfully initialized!");
    }

    public static Region load(Position position) {

        try {

            // Execute the query to grab the data for the region
            queryStatement.setInt(1, position.hash18());
            ResultSet results = queryStatement.executeQuery();

            // If there are no results for the query return null
            if(!results.next()) {
                return null;
            }

            // Create and decode the data for the specified region
            Region region = new Region(position);

            // Read the tile data first so that the clipping map is properly set
            region.decodeTileData(unpackData(results.getBytes("tile_data")));

            region.decodeObjectData(unpackData(results.getBytes("obj_data")));
            region.decodeAreaData(unpackData(results.getBytes("area_data")));
            region.decodeTriggerData(unpackData(results.getBytes("trigger_data")));

            return region;
        } catch(SQLException | RuntimeException ex) {
            logger.error("Failed to load region " + position.hash18(), ex);
            return null;
        }
    }

    public boolean save(Region region) {
        return false;
    }

    private static ByteBuf packData(ByteBuf buf) {
        return null;
    }

    private static ByteBuf unpackData(byte[] bytes) {
        return Base64.decode(Unpooled.wrappedBuffer(bytes));
    }
}
