package net.wurmevolved.server.game.data.db.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.concurrent.atomic.AtomicLong;

@Converter(autoApply = true)
public class AtomicLongConverter implements AttributeConverter<AtomicLong, Long> {

    @Override
    public Long convertToDatabaseColumn(AtomicLong attribute) {
        return attribute.get();
    }

    @Override
    public AtomicLong convertToEntityAttribute(Long dbData) {
        return new AtomicLong(dbData);
    }

}
