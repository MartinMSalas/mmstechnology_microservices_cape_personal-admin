package com.mmstechnology.personal.mapper;

import com.mmstechnology.personal.dto.PersonalDto;
import com.mmstechnology.personal.model.Personal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalMapper {
    PersonalDto toDto(Personal personal);
    Personal toEntity(PersonalDto dto);
}
