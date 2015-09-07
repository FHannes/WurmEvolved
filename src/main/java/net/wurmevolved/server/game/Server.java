package net.wurmevolved.server.game;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.wurmevolved.server.game.map.LegacyLoader;
import net.wurmevolved.server.game.net.ServerHandler;
import net.wurmevolved.server.game.net.WurmDecoder;
import net.wurmevolved.server.game.net.WurmEncoder;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class Server {

    private String hostname;
    private int port;

    private ChannelGroup channelGroup;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private Channel serverChannel;

    private World world;

    public Server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public World getWorld() {
        return world;
    }

    public void start() {
        (world = new World()).load();

        try {
            URL mapResource = Server.class.getResource("/surface.tmp.map");
            if (mapResource != null) {
                File mapFile = Paths.get(mapResource.toURI()).toFile();
                if (mapFile.exists()) {
                    new LegacyLoader(mapFile, 1024).load(world.getTerrainBuffer());
                }
            }

            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new WurmEncoder());
                            pipeline.addLast("decoder", new WurmDecoder());
                            pipeline.addLast("handler", new ServerHandler(channelGroup, world));
                        }
                    });

            System.out.println("Started server");

            serverChannel = bootstrap.bind(hostname, port).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (channelGroup != null && serverChannel != null) {
            System.out.println("Stopping server");

            Future futureBoss = bossGroup.shutdownGracefully();
            Future futureWorker = workerGroup.shutdownGracefully();
            try {
                futureBoss.await();
                futureWorker.await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                channelGroup.close();
            }
        }
    }

}
