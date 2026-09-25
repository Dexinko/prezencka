package sk.upjs.paz;

import javax.management.relation.Role;
import java.time.LocalDate;

public record User(
        Long id,
        String name,
        String surname,
        Gender gender,
        LocalDate birthdate,
        Role role
        ) {
    public enum Role{
        Student,
        Teacher,
        ADMIN
    }

    public enum Gender{
        MALE,
        FEMALE,
        OTHER,
        UNKNOWN
    }

}
