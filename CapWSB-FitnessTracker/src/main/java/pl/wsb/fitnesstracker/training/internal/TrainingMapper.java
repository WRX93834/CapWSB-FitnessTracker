package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.internal.UserMapper;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;

    // Training -> TrainingDto
    public TrainingDto toDto(Training training) {
        if (training == null) return null;
        return new TrainingDto(
                training.getId(),
                userMapper.toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    // TrainingDto -> Training (UWAGA! User musi być pobrany z bazy w serwisie!)
    public Training toEntity(TrainingDto dto, User user) {
        if (dto == null) return null;
        // Jeśli masz id w DTO, ustaw je – przy aktualizacji!
        Training training = new Training(
                user,
                dto.startTime(),
                dto.endTime(),
                dto.activityType(),
                dto.distance(),
                dto.averageSpeed()
        );
        if (dto.id() != null) {
            training.setId(dto.id());
        }
        return training;
    }

    public List<TrainingDto> toDtoList(List<Training> trainings) {
        return trainings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
