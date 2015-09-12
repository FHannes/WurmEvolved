package net.wurmevolved.server.game.data.db.convert;

import net.wurmevolved.common.constants.ActionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActionConverter implements AttributeConverter<ActionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ActionType attribute) {
        return attribute.getId() & 0xFFFF;
    }

    @Override
    public ActionType convertToEntityAttribute(Integer dbData) {
        return ActionType.get(dbData.shortValue());
    }

}
