package ru.otus.socialnetwork.model.dto;

import lombok.Data;
import ru.otus.socialnetwork.model.enumeration.Sex;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private Sex sex;
    private String biography;
    private String city;
}
