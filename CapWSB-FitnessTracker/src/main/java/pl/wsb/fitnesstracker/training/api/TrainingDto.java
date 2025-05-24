package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.user.api.UserDtoApi;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

public record TrainingDto(
        Long id,
        UserDtoApi user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}

