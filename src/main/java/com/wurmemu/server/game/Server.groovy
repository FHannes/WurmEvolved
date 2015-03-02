package com.wurmemu.server.game

import com.wurmemu.server.game.map.LegacyLoader
import com.wurmemu.server.game.net.ServerHandler
import com.wurmemu.server.game.net.WurmDecoder
import com.wurmemu.server.game.net.WurmEncoder
import com.wurmemu.server.game.net.packets.ClientPackets
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.util.concurrent.GlobalEventExecutor

import java.nio.file.Paths

class Server {

    String hostname
    int port

    ChannelGroup channelGroup

    World world

    boolean start() {
        (world = new World()).load()

        URL mapResource = Server.class.getResource("/surface.map")
        if (mapResource != null) {
            File mapFile = Paths.get(mapResource.toURI()).toFile()
            if (mapFile.exists()) {
                new LegacyLoader(mapFile: mapFile, size: 1024).load(world.terrainBuffer)
            }
        }

        def bossGroup = new NioEventLoopGroup()
        def workerGroup = new NioEventLoopGroup()

        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)

        try {
            def bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    def pipeline = ch.pipeline()
                    pipeline.addLast("encoder", new WurmEncoder())
                    pipeline.addLast("decoder", new WurmDecoder())
                    pipeline.addLast("handler", new ServerHandler(channelGroup: channelGroup, world: world))
                }
            })

            println("Started server")

            bootstrap.bind(hostname, port).sync().channel().closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
        true
    }

    void stop() {
        if (channelGroup != null) {
            channelGroup.close()
        }
    }

    static {
        ClientPackets.register()
    }

}
