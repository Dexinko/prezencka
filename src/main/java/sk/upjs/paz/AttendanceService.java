
package sk.upjs.paz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class AttendanceService {

    private final List<Attendance> attendances = new ArrayList<>();

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void loadFromCsv(
            List<User> users,
            List<Subject> subjects
    ) throws IOException {

        Map<Long, User> usersById = new HashMap<>();
        Map<Long, Subject> subjectsById = new HashMap<>();

        for (User user : users) {
            usersById.put(user.id(), user);
        }

        for (Subject subject : subjects) {
            subjectsById.put(subject.id(), subject);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/testdata/attendances.csv"
                                )
                        ),
                        StandardCharsets.UTF_8
                ))) {

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] p = line.split(",", -1);

                LocalDate date = LocalDate.parse(
                        p[1].trim().substring(0, 10)
                );

                Subject subject = subjectsById.get(
                        Long.parseLong(p[2].trim())
                );

                if (subject == null) {
                    throw new IllegalArgumentException(
                            "Neznamy predmet: " + p[2]
                    );
                }

                Set<User> present = new HashSet<>();

                for (String id : p[3].split("_")) {
                    if (id.isBlank()) continue;

                    User user = usersById.get(
                            Long.parseLong(id.trim())
                    );

                    if (user != null) {
                        present.add(user);
                    }
                }

                attendances.add(
                        new Attendance(date, subject, present)
                );
            }
        }
    }
}
