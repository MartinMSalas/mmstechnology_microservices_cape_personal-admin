package com.mmstechnology.personal.mapper;

import com.mmstechnology.personal.dto.PersonalDto;
import com.mmstechnology.personal.dto.SituacionLaboralDto;
import com.mmstechnology.personal.model.Personal;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-19T11:26:20-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PersonalMapperImpl implements PersonalMapper {

    @Override
    public PersonalDto toDto(Personal personal) {
        if ( personal == null ) {
            return null;
        }

        Long id = null;
        String cuil = null;
        String dni = null;
        String nombres = null;
        String apellidos = null;
        String legajo = null;
        LocalDate fechaNacimiento = null;
        LocalDate fechaIngreso = null;
        String ultimaActualizacionRealizadaPor = null;
        List<SituacionLaboralDto> situaciones = null;

        PersonalDto personalDto = new PersonalDto( id, cuil, dni, nombres, apellidos, legajo, fechaNacimiento, fechaIngreso, ultimaActualizacionRealizadaPor, situaciones );

        return personalDto;
    }

    @Override
    public Personal toEntity(PersonalDto dto) {
        if ( dto == null ) {
            return null;
        }

        Personal personal = new Personal();

        return personal;
    }
}
