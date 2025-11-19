import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    UserController userController = new UserController();
    User user = new User();
    private Validator validator;
    private Set<ConstraintViolation<User>> violations;

    @BeforeEach
    public void setUser() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user.setName("Alex");
        user.setEmail("sh.sn@yandex.ru");
        user.setBirthday(LocalDate.of(1999, 7, 15));
        user.setLogin("javaDev");
        //Ручная валидация корректного юзера. violations должен быть пустой
        violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNullName() {
        user.setName("");
        userController.create(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void testNullEmail() {
        user.setEmail("");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNoCorrectEmail() {
        user.setEmail("@имейл");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullLogin() {
        user.setLogin("");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testLoginWithSpace() {
        user.setLogin("i am iron man");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBirthdayInFuture() {
        user.setBirthday(LocalDate.of(2100, 01, 01));
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }


}
