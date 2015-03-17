package net.wurmevolved.server.game.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

/**
 * This class is responsible for registering the server with the login server.
 */
public class RegisterServer {

    private String regHost;
    private String name;
    private String host;

    public RegisterServer(String regHost, String name, String host) {
        this.regHost = regHost;
        this.name = name;
        this.host = host;
    }

    public void register() {
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new WurmEncoder());
                            pipeline.addLast("decoder", new WurmDecoder());
                        }
                    });
            Channel channel = bootstrap.connect(regHost, 48002).sync().channel();
            channel.write(new RegisterPacket());
            group.shutdownGracefully().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Packet(0)
    private class RegisterPacket extends AbstractPacket {

        @Override
        public void encode(ByteBuf out) {
            writeString(out, "");
            writeString(out, "");
            writeString(out, name);
            writeString(out, host);
        }

    }

}
