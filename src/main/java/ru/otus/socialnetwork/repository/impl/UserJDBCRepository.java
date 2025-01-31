package ru.otus.socialnetwork.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.socialnetwork.model.entity.UserEntity;
import ru.otus.socialnetwork.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Repository
public class UserJDBCRepository implements UserRepository {

    private final static String searchTemplate = "%s%%";

    private final JdbcTemplate masterJdbcTemplate;
    private final JdbcTemplate replicaJdbcTemplate;
    private final GeneratedKeyHolder keyHolder;
    private final BeanPropertyRowMapper<UserEntity> userRowMapper;

    public UserJDBCRepository(@Qualifier("masterJdbcTemplate") JdbcTemplate masterJdbcTemplate,
                              @Qualifier("replicaJdbcTemplate") JdbcTemplate replicaJdbcTemplate) {
        this.masterJdbcTemplate = masterJdbcTemplate;
        this.replicaJdbcTemplate = replicaJdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
        this.userRowMapper = BeanPropertyRowMapper.newInstance(UserEntity.class);
    }


    @Override
    public Optional<UserEntity> findById(Long id) {
        try {
            return Optional.ofNullable(
                    replicaJdbcTemplate.queryForObject(
                            "select * from user_info where id = ?",
                            userRowMapper,
                            id)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try {
            return Optional.ofNullable(
                    replicaJdbcTemplate.queryForObject(
                            "select * from user_info where username = ?",
                            userRowMapper,
                            username)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserEntity save(UserEntity entity) {

        masterJdbcTemplate.update(conn -> {

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "insert into user_info (username, first_name, last_name, birth_date, sex, biography, city) values(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setObject(4, entity.getBirthDate());
            preparedStatement.setObject(5, entity.getSex());
            preparedStatement.setString(6, entity.getBiography());
            preparedStatement.setString(7, entity.getCity());


            return preparedStatement;

        }, keyHolder);


        long id = (long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        return masterJdbcTemplate.queryForObject(
                "select * from user_info where id = ?",
                userRowMapper,
                id);

    }

    @Override
    public void deleteById(Long id) {
        masterJdbcTemplate.update(
                "delete from user_info where id = ?",
                id);

    }

    @Override
    public UserEntity updateById(Long id, UserEntity entity) {

        masterJdbcTemplate.update(
                "update user_info set first_name = ?, last_name = ?, birth_date = ?, sex = ?, biography = ?, city = ? where id = ?",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                nonNull(entity.getSex()) ? entity.getSex().name() : null,
                entity.getBiography(),
                entity.getCity(),
                id);

        return masterJdbcTemplate.queryForObject(
                "select * from user_info where id = ?",
                userRowMapper,
                id);
    }

    @Override
    public List<UserEntity> search(String firstName, String lastName) {
        String firstNameTemplate = searchTemplate.formatted(firstName);
        String lastNameTemplate = searchTemplate.formatted(lastName);

        if (nonNull(firstName) && nonNull(lastName)) {
            return replicaJdbcTemplate.query(
                    "select * from user_info where lower(first_name) like lower(?) and lower(last_name) like lower(?) order by id",
                    userRowMapper,
                    firstNameTemplate,
                    lastNameTemplate);
        }

        if (nonNull(firstName)){
            return replicaJdbcTemplate.query(
                    "select * from user_info where lower(first_name) like lower(?) order by id",
                    userRowMapper,
                    firstNameTemplate);
        }

        if (nonNull(lastName)) {
            return replicaJdbcTemplate.query(
                    "select * from user_info where lower(last_name) like lower(?) order by id",
                    userRowMapper,
                    lastNameTemplate);
        }

        return List.of();
    }
}