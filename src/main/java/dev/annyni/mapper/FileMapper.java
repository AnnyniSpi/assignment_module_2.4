package dev.annyni.mapper;

import dev.annyni.dto.FileDto;
import dev.annyni.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);


    FileDto toDto(File file);

    File toEntity(FileDto fileDto);
}
