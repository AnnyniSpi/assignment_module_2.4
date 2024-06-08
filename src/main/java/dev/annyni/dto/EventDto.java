package dev.annyni.dto;

public record EventDto(Integer id,
                       UserDto userDto,
                       FileDto fileDto) {
}
