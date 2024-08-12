package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
        @Positive
        private Long id;
        @NotBlank
        private String name;
        @Size(min = 1, max = 200)
        private String description;
        private LocalDate releaseDate;
        @Positive
        private long duration;
        private Set<Long> likes;

        public void addLike(Long userId) {
                likes.add(userId);
        }

        public void removeLike(Long userId) {
                likes.remove(userId);
        }

        public int getLikesQuantity() {
                return likes.size();
        }
}
