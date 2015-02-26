package com.wurmemu.server.game

import com.wurmemu.server.game.map.TerrainBuffer

class World {

    TerrainBuffer terrainBuffer

    World() {
        terrainBuffer = new TerrainBuffer()
    }

}
