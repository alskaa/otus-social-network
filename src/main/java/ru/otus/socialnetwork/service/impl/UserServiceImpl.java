package ru.otus.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.socialnetwork.exception.NotFoundException;
import ru.otus.socialnetwork.mapper.UserMapper;
import ru.otus.socialnetwork.model.dto.UserDto;
import ru.otus.socialnetwork.model.entity.UserEntity;
import ru.otus.socialnetwork.repository.UserRepository;
import ru.otus.socialnetwork.service.UserService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserDetailsManager userDetailsManager;
    private final UserMapper mapper;

    @Override
    public UserDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow((() -> new NotFoundException(id)));
    }

    @Override
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = mapper.mapToEntity(userDto);
        userEntity = repository.save(userEntity);
        return mapper.mapToDto(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        userDetailsManager.deleteUser(userEntity.getUsername());
        repository.deleteById(id);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        UserEntity userEntity = mapper.mapToEntity(userDto);
        userEntity = repository.updateById(id, userEntity);
        return mapper.mapToDto(userEntity);
    }

    @Override
    public List<UserDto> search(String firstName, String lastName) {
        List<UserEntity> userEntities = repository.search(firstName, lastName);
        return mapper.mapToDto(userEntities);
    }
}
