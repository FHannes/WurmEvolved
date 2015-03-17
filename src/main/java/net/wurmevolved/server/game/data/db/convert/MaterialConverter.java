package net.wurmevolved.server.game.data.db.convert;

import net.wurmevolved.common.constants.Material;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MaterialConverter implements AttributeConverter<Material, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Material attribute) {
        return attribute.getId() & 0xFF;
    }

    @Override
    public Material convertToEntityAttribute(Integer dbData) {
        return Material.get(dbData.shortValue());
    }

}
