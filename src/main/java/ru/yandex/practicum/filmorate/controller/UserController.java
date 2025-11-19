package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    HashMap<Long, User> users = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.trace("Start create User");

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);

        log.info("Completed create user : " + user.getId());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User updateUser) {
        log.trace("Start update user" + updateUser.getId());
        User oldUser = users.get(updateUser.getId());

        if (updateUser.getName() == null || updateUser.getName().isBlank()) {
            log.debug("updateUser name = null. Name oldUser does not change: " + oldUser.getName());
            oldUser.setName(updateUser.getLogin());
        } else {
            log.debug("User id-" + updateUser.getId() + " has been assigned a new name " + updateUser.getName());
            oldUser.setName(updateUser.getName());
        }

        oldUser.setLogin(updateUser.getLogin());
        oldUser.setEmail(updateUser.getEmail());

        if (!(updateUser.getBirthday() == null)) {
            log.debug("User id-" + updateUser.getId() + " has been assigned a new birthday" + updateUser.getBirthday());
            oldUser.setBirthday(updateUser.getBirthday());
        }
        log.info("User id-" + updateUser.getId() + " has been update: name={}, login{}, email{}, birthday{}",
                oldUser.getName(), oldUser.getLogin(), oldUser.getEmail(), oldUser.getBirthday());
        return oldUser;
    }

    @GetMapping
    public Collection<User> listUsers() {
        log.info("List all users: " + users.values());
        return users.values();
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        log.debug("Has been next id user: " + (currentMaxId + 1));
        return ++currentMaxId;
    }
}

