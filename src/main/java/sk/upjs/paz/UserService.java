
package sk.upjs.paz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService {

    private final List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users == null ? List.of() : users;
    }

    public static UserService loadFromCsv() throws IOException {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                UserService.class.getResourceAsStream(
                                        "/testdata/users.csv"
                                )
                        ),
                        StandardCharsets.UTF_8
                ))) {

            reader.readLine(); // Preskocime hlavicku CSV

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] p = line.split(",", -1);

                User.Role role = switch (p[6].trim().toUpperCase()) {
                    case "STUDENT" -> User.Role.Student;
                    case "TEACHER" -> User.Role.Teacher;
                    case "ADMIN" -> User.Role.ADMIN;
                    default -> throw new IllegalArgumentException(
                            "Neznama rola: " + p[6]
                    );
                };

                users.add(new User(
                        Long.parseLong(p[0].trim()),
                        p[2].trim(),
                        p[3].trim(),
                        User.Gender.valueOf(p[4].trim().toUpperCase()),
                        LocalDate.parse(p[5].trim()),
                        role
                ));
            }
        }

        return new UserService(users);
    }

    public GenderRatio calculateGenderRatio() {
        if (users == null || users.isEmpty()) {
            return new GenderRatio(0.0, 0.0, 0.0, 0.0);
        }

        int boys = 0;
        int girls = 0;
        int unknown = 0;
        int other = 0;

        for (User u : users) {
            switch (u.gender()) {
                case MALE -> boys++;
                case FEMALE -> girls++;
                case OTHER -> other++;
                case UNKNOWN -> unknown++;
            }
        }

        int total = users.size();

        return new GenderRatio(
                (double) boys / total,
                (double) girls / total,
                (double) unknown / total,
                (double) other / total
        );
    }
}
