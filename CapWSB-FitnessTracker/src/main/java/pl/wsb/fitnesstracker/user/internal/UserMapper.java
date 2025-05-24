package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDtoApi;

@Component
public class UserMapper {

    public UserDtoApi toDto(User user) {
        if (user == null) return null;

        return new UserDtoApi(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
    }

    public User toEntity(UserDtoApi userDtoApi) {
        if (userDtoApi == null) return null;

        return new User(

                userDtoApi.id(),
                userDtoApi.firstName(),
                userDtoApi.lastName(),
                userDtoApi.birthdate(),
                userDtoApi.email()
        );
    }

}
