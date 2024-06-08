package dev.annyni.mapper;

import dev.annyni.dto.EventDto;
import dev.annyni.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, FileMapper.class})
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDto toDto(Event event);

    Event toEntity(EventDto eventDto);
    
}
