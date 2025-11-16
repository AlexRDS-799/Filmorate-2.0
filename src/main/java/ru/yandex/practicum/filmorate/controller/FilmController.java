package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    HashMap<Long, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.trace("Start add film");
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Completed add film: " + film.getName());
        return film;

    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film updateFilm) {
        log.trace("Start update film: id-" + updateFilm.getId());
        Film oldFilm = films.get(updateFilm.getId());
        oldFilm.setDescription(updateFilm.getDescription());
        oldFilm.setName(updateFilm.getName());
        oldFilm.setReleaseDate(updateFilm.getReleaseDate());
        oldFilm.setDuration(updateFilm.getDuration());
        log.info("Film id-" + oldFilm.getId() + "has been update: description={}, name={}, releaseDate={}, duration={}",
                oldFilm.getDescription(), oldFilm.getName(), oldFilm.getReleaseDate(), oldFilm.getDuration());
        return oldFilm;
    }

    @GetMapping
    public Collection<Film> listFilms() {
        log.info("List all films: " + films.values());
        return films.values();
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        log.debug("Has been next id user: " + (currentMaxId + 1));
        return ++currentMaxId;
    }
}
