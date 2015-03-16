package net.wurmevolved.server.game;

import net.wurmevolved.server.game.map.LegacyLoader;
import net.wurmevolved.server.game.net.ServerHandler;
import net.wurmevolved.server.game.net.WurmDecoder;
import net.wurmevolved.server.game.net.WurmEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

class Server {

    private String hostname;
    private int port;

    private ChannelGroup channelGroup;

    private World world;

    public Server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public boolean start() {
        (world = new World()).load();

        try {
            URL mapResource = Server.class.getResource("/surface.map");
            if (mapResource != null) {
                File mapFile = Paths.get(mapResource.toURI()).toFile();
                if (mapFile.exists()) {
                    new LegacyLoader(mapFile, 1024).load(world.getTerrainBuffer());
                }
            }

            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();

            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

            try {
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

                bootstrap.bind(hostname, port).sync().channel().closeFuture().sync();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void stop() {
        if (channelGroup != null) {
            channelGroup.close();
        }
    }

}
