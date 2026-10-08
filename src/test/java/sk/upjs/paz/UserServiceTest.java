package sk.upjs.paz;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class UserServiceTest {
    UserService userService;

    @Test
    void calculateGenderRatio_ok(){
        userService = new UserService(List.of(
                new User(null,"Alica", "Krajná", User.Gender.FEMALE, LocalDate.of(2000,10,30),User.Role.Student),
                new User(null,"Alica", "Krajná", User.Gender.FEMALE, LocalDate.of(2000,10,30),User.Role.Student),
                new User(null,"Ivan", "Krajný", User.Gender.MALE, LocalDate.of(2000,10,30),User.Role.Student)
        ));

        GenderRatio gr = userService.calculateGenderRatio();
        assertEquals((double) 2/3, gr.girls());
        assertEquals((double) 1/3, gr.boys());
        assertEquals(0.0,gr.unknown());
        assertEquals(0.0,gr.other());
    }

    @Test
    void calculateGenderRatio_empty(){
        userService = new UserService(Collections.emptyList());
        GenderRatio gr = userService.calculateGenderRatio();
        assertEquals(0.0, gr.girls());
        assertEquals(0.0, gr.boys());
        assertEquals(0.0, gr.other());
        assertEquals(0.0, gr.unknown());
    }
    @Test
    void calculateGenderRatio_null(){
        userService = new UserService(null);
        GenderRatio gr = userService.calculateGenderRatio();
        assertEquals(0.0, gr.girls());
        assertEquals(0.0, gr.boys());
        assertEquals(0.0, gr.other());
        assertEquals(0.0, gr.unknown());
    }
}