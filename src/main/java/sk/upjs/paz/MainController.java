
package sk.upjs.paz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.Locale;

public class MainController {

    @FXML
    private Label resultLabel;

    @FXML
    private void generateRandom() {
        try {
            UserService userService =
                    UserService.loadFromCsv();

            SubjectService subjectService =
                    new SubjectService();

            subjectService.loadFromCsv(
                    userService.getUsers()
            );

            AttendanceService attendanceService =
                    new AttendanceService();

            attendanceService.loadFromCsv(
                    userService.getUsers(),
                    subjectService.getSubjects()
            );

            GenderRatio gr =
                    userService.calculateGenderRatio();

            String result = String.format(
                    Locale.US,
                    "Dievcata: %.1f %%\n" +
                            "Chlapci: %.1f %%\n" +
                            "Osoby: %d | Predmety: %d | Prezencky: %d",
                    gr.girls() * 100,
                    gr.boys() * 100,
                    userService.getUsers().size(),
                    subjectService.getSubjects().size(),
                    attendanceService.getAttendances().size()
            );

            resultLabel.setText(result);

        } catch (Exception e) {
            resultLabel.setText(
                    "Chyba: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
