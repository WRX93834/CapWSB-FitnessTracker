package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record UserDtoApi(
        @Nullable Long id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email
) {}
