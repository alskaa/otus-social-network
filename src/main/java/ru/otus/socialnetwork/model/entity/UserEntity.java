package ru.otus.socialnetwork.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import ru.otus.socialnetwork.model.enumeration.Sex;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Sex sex;
    private String biography;
    private String city;
}
