package com.wurmemu.server.game.data.db.convert

import com.wurmemu.common.constants.TileType

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class TileTypeConverter implements AttributeConverter<TileType, Integer> {

    @Override
    Integer convertToDatabaseColumn(TileType attribute) {
        (attribute.type + 256) % 256
    }

    @Override
    TileType convertToEntityAttribute(Integer dbData) {
        TileType.get((byte) dbData)
    }

}
