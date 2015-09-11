package net.wurmevolved.server.game.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.logic.handlers.*;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.UnknownPacket;
import net.wurmevolved.server.game.net.packets.client.*;
import net.wurmevolved.server.game.net.packets.server.AddUserPacket;
import net.wurmevolved.server.game.net.packets.server.LoginResponsePacket;
import net.wurmevolved.server.game.net.packets.server.RemoveCreaturePacket;
import net.wurmevolved.server.game.net.packets.server.RemoveUserPacket;

import java.util.LinkedList;
import java.util.List;

public class ServerHandler extends SimpleChannelInboundHandler<AbstractPacket> {

    private ChannelGroup channelGroup;
    private World world;

    private Player player;
    private List<LogicHandler> logicHandlers = new LinkedList<>();

    public ServerHandler(ChannelGroup channelGroup, World world) {
        this.channelGroup = channelGroup;
        this.world = world;
    }

    public void init(Player player) {
        this.player = player;

        logicHandlers.add(new ConnectionHandler(world, player));

        MovementHandler movementHandler = new MovementHandler(player);
        logicHandlers.add(movementHandler);

        TerrainHandler terrainHandler = new TerrainHandler(world, player);
        movementHandler.getObservers().add(terrainHandler);
        logicHandlers.add(terrainHandler);

        LocalHandler localHandler = new LocalHandler(world, player);
        movementHandler.getObservers().add(localHandler);
        logicHandlers.add(localHandler);

        logicHandlers.add(new ActionHandler(player));
        logicHandlers.add(new InventoryHandler(player));
        logicHandlers.add(new ChatHandler(world, player));
        logicHandlers.add(new GUIHandler());
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
                logicHandlers.forEach(LogicHandler::join);
            }
        } else {
            logicHandlers.forEach(lh -> lh.handle(msg));
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
        logicHandlers.forEach(LogicHandler::leave);

        super.channelInactive(ctx);
    }

}
