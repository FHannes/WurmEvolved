package net.wurmevolved.server.game.data.factory;

import net.wurmevolved.common.constants.EntityType;
import net.wurmevolved.common.constants.Kingdom;
import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.data.FaceStyle;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.PlayerDAO;

import java.util.Random;

public class PlayerFactory {

    private PlayerDAO dao;
    private IDFactory idFactory;
    private Random random;
    private ItemFactory itemFactory;

    public PlayerFactory() {
        dao = (PlayerDAO) DB.getInstance().getDAO("playerDAO");
        idFactory = new IDFactory("player", EntityType.PLAYER);
        random = new Random();
        itemFactory = new ItemFactory();
    }

    public Player makePlayer(String username) {
        Player player = new Player();
        player.setId(idFactory.makeID());
        player.setUsername(username);
        player.setType(PlayerType.REGULAR);
        Position playerPos = new Position();
        playerPos.update(512, 512);
        playerPos.setRot(0);
        playerPos.setLayer(Layer.SURFACE);
        player.setPos(playerPos);
        player.setMale(true);
        FaceStyle faceStyle = new FaceStyle();
        faceStyle.setComplexion((byte) random.nextInt(8));
        faceStyle.setEyeColor((byte) random.nextInt(8));
        faceStyle.setFacialHair((byte) random.nextInt(8));
        faceStyle.setEyeType((byte) random.nextInt(8));
        faceStyle.setHair((byte) random.nextInt(8));
        faceStyle.setHairColor((byte) random.nextInt(8));
        faceStyle.setLowerFace((byte) random.nextInt(8));
        faceStyle.setNose((byte) random.nextInt(8));
        faceStyle.setSkinColor((byte) random.nextInt(8));
        faceStyle.setUpperFace((byte) random.nextInt(8));
        player.setFaceStyle(faceStyle);
        player.setBody(itemFactory.makeBody());
        player.setInventory(itemFactory.makeInventory());
        player.setKingdom(Kingdom.FREEDOM);
        dao.save(player);
        return player;
    }

}
