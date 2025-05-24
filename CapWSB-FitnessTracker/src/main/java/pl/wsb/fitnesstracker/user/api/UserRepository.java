package pl.wsb.fitnesstracker.user.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wsb.fitnesstracker.user.api.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByBirthdateBefore(LocalDate date);
    @Query("SELECT u FROM User u WHERE u.birthdate <= :maxDate")
    List<User> findUsersOlderThan(java.time.LocalDate maxDate);

}
