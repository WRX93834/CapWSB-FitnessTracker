package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingRequestDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTrainings());
    }



    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getByActivity(@RequestParam String activityType) {
        return ResponseEntity.ok(trainingService.getAllTrainingByActivityType(activityType));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(trainingService.getAllTrainingsForDedicatedUser(userId));
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingRequestDto requestDto) {
        // Convert TrainingRequestDto to TrainingDto
        TrainingDto trainingDto = new TrainingDto(
                requestDto.id(),
                new pl.wsb.fitnesstracker.user.api.UserDtoApi(requestDto.userId(), null, null, null, null),
                requestDto.startTime(),
                requestDto.endTime(),
                requestDto.activityType(),
                requestDto.distance(),
                requestDto.averageSpeed()
        );
        return ResponseEntity.status(201).body(trainingService.createTraining(trainingDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long id, @RequestBody TrainingRequestDto requestDto) {
        // Convert TrainingRequestDto to TrainingDto
        TrainingDto trainingDto = new TrainingDto(
                requestDto.id(),
                new pl.wsb.fitnesstracker.user.api.UserDtoApi(requestDto.userId(), null, null, null, null),
                requestDto.startTime(),
                requestDto.endTime(),
                requestDto.activityType(),
                requestDto.distance(),
                requestDto.averageSpeed()
        );
        return ResponseEntity.ok(trainingService.updateTraining(id, trainingDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getFinishedAfter(@PathVariable String afterTime) {
        return ResponseEntity.ok(trainingService.getAllFinishedTrainingsAfter(afterTime));
    }


}
