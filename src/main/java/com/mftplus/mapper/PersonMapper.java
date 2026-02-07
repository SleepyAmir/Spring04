package com.mftplus.mapper;

import com.mftplus.dto.PersonDto;
import com.mftplus.model.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // این باعث می‌شود بتوانیم آن را @Autowired کنیم
public interface PersonMapper {
    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
}