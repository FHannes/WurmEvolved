package com.wurmemu.server.game.net.packets.client

import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class MovementPacket extends Packet {

    float x
    float y
    float z
    float rotation
    byte detection
    byte layer

    @Override
    void encode(ByteBuf out) {
        //
    }

    static MovementPacket decode(ByteBuf frame) {
        def x = frame.readFloat()
        def y = frame.readFloat()
        def z = frame.readFloat()
        def rotation = frame.readFloat()
        def detection = frame.readByte()
        def layer = frame.readByte()
        (1..5).each {
            // TODO: Figure out these values
            frame.readFloat()
            frame.readFloat()
            frame.readFloat()
            frame.readFloat()
            frame.readByte()
            frame.readByte()
        }
        new MovementPacket(x: x, y: y, z: z, rotation: rotation, detection: detection, layer: layer)
    }

}
