package sk.upjs.paz;

import java.util.List;

public record Subject(
        Long id,
        String name,
        int year,
        List<User> students
) {
}
