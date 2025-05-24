package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.UserDtoApi;
import pl.wsb.fitnesstracker.user.api.UserService;
import pl.wsb.fitnesstracker.user.api.SimpleUserDto;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    // Prosty DTO lub możesz zwrócić tylko mapowanie częściowe:
    @GetMapping("/simple")
    public ResponseEntity<List<SimpleUserDto>> getAllSimpleUsers() {
        var users = userService.findAllUsers();
        var dtos = users.stream()
                .map(user -> new SimpleUserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<UserDtoApi>> getAllUsers() {
        var users = userService.findAllUsers();
        var dtos = users.stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoApi> getUserById(@PathVariable Long id) {
        var user = userService.findUserById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping("/email")
    public ResponseEntity<List<UserDtoApi>> getUserByEmail(@RequestParam String email) {
        var user = userService.findUserByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(List.of(userMapper.toDto(user)));
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDtoApi>> getAllUsersOlderThan(@PathVariable LocalDate time) {
        // For this test, we're actually looking for users born before the given date
        // which makes them older than users born after that date
        var users = userService.findAllUsersOlderThan(time);
        var dtos = users.stream()
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos); // ZAWSZE 200, nawet jeśli lista pusta
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserDtoApi> addUser(@RequestBody UserDtoApi userDto) {
        var userEntity = userMapper.toEntity(userDto);
        var savedUser = userService.createUser(userEntity);
        return ResponseEntity.status(201).body(userMapper.toDto(savedUser)); // ZWRÓĆ 201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoApi> updateUser(@PathVariable Long id, @RequestBody UserDtoApi userDto) {
        var userEntity = userMapper.toEntity(userDto);
        // Ustawiamy id ręcznie (żeby update był poprawny):
        userEntity = new pl.wsb.fitnesstracker.user.api.User(
                id,
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getBirthdate(),
                userEntity.getEmail()
        );
        var updatedUser = userService.createUser(userEntity); // save() z JPA zrobi update, jeśli id istnieje
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
}
