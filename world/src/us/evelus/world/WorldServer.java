package us.evelus.world;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.command.CommandDispatcher;
import us.evelus.world.model.Player;
import us.evelus.world.model.Position;
import us.evelus.world.model.region.RegionLoader;
import us.evelus.world.model.World;
import us.evelus.world.net.GameChannelInitializer;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.handler.*;
import us.evelus.world.plugin.PluginContext;
import us.evelus.world.plugin.PluginLoader;

import java.io.IOException;

public final class WorldServer {

    private static final Logger logger = LoggerFactory.getLogger(WorldServer.class);

    private final EventLoopGroup loopGroup = new NioEventLoopGroup();
    private final CodecRepository codecRepository = new CodecRepository();
    private final CommandDispatcher commandDispatcher = new CommandDispatcher();
    private final PluginLoader pluginLoader = new PluginLoader();
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final World world = new World();

    public void init() {

        // Register all of the handlers
        codecRepository.register(new CommandMessageHandler(commandDispatcher));

        codecRepository.register(new InteractMessageHandler(world));
        codecRepository.register(new SpawnPlayerMessageHandler(world));
        codecRepository.register(new SpawnObserverMessageHandler(world));
        codecRepository.register(new QueryObserverMessageHandler(world));
        codecRepository.register(new TickMessageHandler(world));

        //try {
        //    StateSymbol.load("./data/config.db");
        //} catch(ClassNotFoundException ex) {
        //    logger.error("Failed to load the SQLite driver", ex);
        //}

        try {
            RegionLoader.init("./data/config.db");
        } catch(ClassNotFoundException ex) {
            logger.error("Failed to load the SQLite driver");
        }

        // Create and set the plugin context
        PluginContext context = new PluginContext(world);
        pluginLoader.setPluginContext(context);

        // Load all of the server plugins
        try {
            pluginLoader.load("data/plugins");
        } catch (IOException | RuntimeException ex) {
            logger.error("Failed to load the server plugins", ex);
        }
    }

    public void bind(int port) {
        try {
            bootstrap.group(loopGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new GameChannelInitializer(codecRepository));
            bootstrap.bind(port).sync();
            logger.info("World server bound to port " + port);
        } catch(Exception ex) {
            logger.error("Failed to bind port " + port, ex);
        }
    }

    public static void main(String[] args) {
        WorldServer server = new WorldServer();
        server.init();
        server.bind(5555);
    }
}
