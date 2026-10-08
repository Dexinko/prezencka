
package sk.upjs.paz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SubjectService {

    private final List<Subject> subjects = new ArrayList<>();

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void loadFromCsv(List<User> users) throws IOException {

        Map<Long, User> usersById = new HashMap<>();

        for (User user : users) {
            usersById.put(user.id(), user);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/testdata/subjects.csv"
                                )
                        ),
                        StandardCharsets.UTF_8
                ))) {

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] p = line.split(",", -1);
                List<User> students = new ArrayList<>();

                for (String id : p[4].split("_")) {
                    if (id.isBlank()) continue;

                    User student = usersById.get(
                            Long.parseLong(id.trim())
                    );

                    if (student != null) {
                        students.add(student);
                    }
                }

                subjects.add(new Subject(
                        Long.parseLong(p[0].trim()),
                        p[1].trim(),
                        Integer.parseInt(p[2].trim()),
                        students
                ));
            }
        }
    }
}
