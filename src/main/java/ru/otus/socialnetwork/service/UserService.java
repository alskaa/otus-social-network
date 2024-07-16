package ru.otus.socialnetwork.service;

import ru.otus.socialnetwork.model.dto.UserDto;

public interface UserService {
    UserDto findById(Long id);
    UserDto save(UserDto userDto);
    void deleteById(Long id);
    UserDto update(Long id, UserDto userDto);
}
