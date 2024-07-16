package ru.otus.socialnetwork.repository;

import ru.otus.socialnetwork.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
    UserEntity save(UserEntity entity);
    void deleteById(Long id);
    UserEntity updateById(Long id, UserEntity book);
}
