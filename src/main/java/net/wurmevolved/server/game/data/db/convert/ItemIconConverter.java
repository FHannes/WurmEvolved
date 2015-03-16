package net.wurmevolved.server.game.data.db.convert;

import net.wurmevolved.common.constants.ItemIcon;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemIconConverter implements AttributeConverter<ItemIcon, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ItemIcon attribute) {
        return attribute.getValue() & 0xFFFF;
    }

    @Override
    public ItemIcon convertToEntityAttribute(Integer dbData) {
        return ItemIcon.get(dbData.shortValue());
    }

}
