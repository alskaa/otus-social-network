package ru.otus.socialnetwork.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import ru.otus.socialnetwork.model.enumeration.Sex;

import java.util.Date;

@Data
public class UserEntity {
    @Id
    private Long id;
    private String login;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private Sex sex;
    private String biography;
    private String city;
}
