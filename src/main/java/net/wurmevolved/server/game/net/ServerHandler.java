package net.wurmevolved.server.game.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.logic.ActionHandler;
import net.wurmevolved.server.game.logic.ChatHandler;
import net.wurmevolved.server.game.logic.ItemHandler;
import net.wurmevolved.server.game.logic.MovementHandler;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.UnknownPacket;
import net.wurmevolved.server.game.net.packets.client.*;
import net.wurmevolved.server.game.net.packets.server.LoginResponsePacket;

public class ServerHandler extends SimpleChannelInboundHandler<AbstractPacket> {

    private ChannelGroup channelGroup;
    private World world;

    private Player player;
    private ChatHandler chatHandler;
    private MovementHandler movementHandler;
    private ActionHandler actionHandler;
    private ItemHandler itemHandler;

    public ServerHandler(ChannelGroup channelGroup, World world) {
        this.channelGroup = channelGroup;
        this.world = world;
    }

    public void init(Player player) {
        this.player = player;
        movementHandler = new MovementHandler(world, player);
        chatHandler = new ChatHandler(world, player);
        actionHandler = new ActionHandler(world, player);
        itemHandler = new ItemHandler(world, player);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket msg) throws Exception {
        if (player == null) {
            if (msg instanceof LoginPacket) {
                LoginPacket msgLogin = (LoginPacket) msg;
                init(world.getPlayers().load(msgLogin.getUsername()));
                world.updatePosition(player.getPos());
                player.setChannel(ctx.channel());
                player.send(new LoginResponsePacket(
                        true, "Welcome to WurmEvolved", player.getPos(), player.getModel(), player.getType(),
                        player.getFaceStyle(), player.getKingdom()));
                movementHandler.initLocal();
                movementHandler.initServer();
                movementHandler.update();
                itemHandler.init();
                chatHandler.sendLocal(String.format("%s has joined the game!", player.getUsername()));
            }
        } else {
            if (msg instanceof ClientMessagePacket) {
                chatHandler.handle((ClientMessagePacket) msg);
            } else if (msg instanceof MovementPacket) {
                movementHandler.handle((MovementPacket) msg);
            } else if (msg instanceof ToggleButtonPacket) {
                ToggleButtonPacket msgToggle = (ToggleButtonPacket) msg;
                System.out.println(String.format("Toggle button #%d: %s", msgToggle.getButtonID(), Boolean.toString(msgToggle.isToggleOn())));
            } else if (msg instanceof RequestActionPacket) {
                actionHandler.handle((RequestActionPacket) msg);
            }
        }
        if (msg instanceof UnknownPacket) {
            System.out.println(String.format("Unknown packet with ID %d", ((UnknownPacket) msg).getType()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (movementHandler != null) {
            movementHandler.leaveWorld();
            chatHandler.sendLocal(String.format("%s has left the world!", player.getUsername()));
        }

        super.channelInactive(ctx);
    }

}