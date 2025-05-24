package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;

/**
 * DTO for receiving training requests from clients.
 * This matches the JSON structure used in the tests.
 */
public record TrainingRequestDto(
        Long id,
        Long userId,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}