package org.acme.model.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.acme.model.Perfil;

@Converter(autoApply = true)
public class PerfilConverter implements AttributeConverter<Perfil, Long> {
    @Override
    public Long convertToDatabaseColumn(Perfil perfil) {
        return (perfil == null) ? null : perfil.ID;
    }

    @Override
    public Perfil convertToEntityAttribute(Long id) {
        return Perfil.valueOf(id);
    }
}
