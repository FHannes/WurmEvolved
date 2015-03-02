package com.wurmemu.server.game.data.db.convert

import javax.persistence.AttributeConverter
import javax.persistence.Converter
import java.util.concurrent.atomic.AtomicLong

@Converter(autoApply = true)
class AtomicLongConverter implements AttributeConverter<AtomicLong, Long> {

    @Override
    Long convertToDatabaseColumn(AtomicLong attribute) {
        attribute.get()
    }

    @Override
    AtomicLong convertToEntityAttribute(Long dbData) {
        new AtomicLong(dbData)
    }

}
