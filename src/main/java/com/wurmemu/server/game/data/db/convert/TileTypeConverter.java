package com.wurmemu.server.game.data.db.convert;

import com.wurmemu.common.constants.TileType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TileTypeConverter implements AttributeConverter<TileType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TileType attribute) {
        return attribute.getType() & 0xFF;
    }

    @Override
    public TileType convertToEntityAttribute(Integer dbData) {
        return TileType.get(dbData.byteValue());
    }

}
