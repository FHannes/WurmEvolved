package net.wurmevolved.server.game.data.db.convert;

import net.wurmevolved.common.constants.Action;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActionConverter implements AttributeConverter<Action, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Action attribute) {
        return attribute.getId() & 0xFFFF;
    }

    @Override
    public Action convertToEntityAttribute(Integer dbData) {
        return Action.get(dbData.shortValue());
    }

}
