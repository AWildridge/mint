package us.evelus.world;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.command.CommandDispatcher;
import us.evelus.world.model.Graphic;
import us.evelus.world.model.Player;
import us.evelus.world.model.World;
import us.evelus.world.model.mob.StateSymbol;
import us.evelus.world.model.observer.Observer;
import us.evelus.world.net.InboundDatagramMessageHandler;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.handler.TickMessageHandler;
import us.evelus.world.net.msg.codec.handler.CommandMessageHandler;
import us.evelus.world.net.msg.codec.handler.InteractMessageHandler;
import us.evelus.world.net.msg.codec.handler.SpawnPlayerMessageHandler;
import us.evelus.world.plugin.PluginContext;
import us.evelus.world.plugin.PluginLoader;

import java.io.IOException;

public final class WorldServer {

    private static final Logger logger = LoggerFactory.getLogger(WorldServer.class);

    private final EventLoopGroup loopGroup = new NioEventLoopGroup();
    private final CodecRepository codecRepository = new CodecRepository();
    private final CommandDispatcher commandDispatcher = new CommandDispatcher();
    private final PluginLoader pluginLoader = new PluginLoader();
    private final Bootstrap bootstrap = new Bootstrap();
    private final World world = new World();

    public void init() {

        // Register all of the handlers
        codecRepository.register(new CommandMessageHandler(commandDispatcher));

        codecRepository.register(new InteractMessageHandler(world));
        codecRepository.register(new SpawnPlayerMessageHandler(world));
        codecRepository.register(new TickMessageHandler(world));

        try {
            StateSymbol.load("./data/config.db");
        } catch(ClassNotFoundException ex) {
            logger.error("Failed to load the SQLite driver", ex);
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
            bootstrap.group(loopGroup).channel(NioDatagramChannel.class).handler(new InboundDatagramMessageHandler(codecRepository));
            bootstrap.bind(port).sync();
            logger.info("World server bound to port " + port);
        } catch(Exception ex) {
            logger.error("Failed to bind port " + port, ex);
        }
    }

    public void test() {
        Observer observer = new Observer();
        Player player = new Player();
        player.displayGraphic(new Graphic());
        world.addPlayer(player);
        world.addObserver(observer);
        world.tick();
    }

    public static void main(String[] args) {
        WorldServer server = new WorldServer();
        server.init();
        server.test();
        server.bind(5555);
    }
}
