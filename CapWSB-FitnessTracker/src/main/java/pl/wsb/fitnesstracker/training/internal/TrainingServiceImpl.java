package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.api.UserRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserRepository userRepository;

    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingMapper.toDtoList(trainingRepository.findAll());
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        Long userId = trainingDto.user() != null ? trainingDto.user().id() : null;
        if (userId == null) throw new IllegalArgumentException("User id required!");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var entity = trainingMapper.toEntity(trainingDto, user);
        var saved = trainingRepository.save(entity);
        return trainingMapper.toDto(saved);
    }

    @Override
    public List<TrainingDto> getAllFinishedTrainingsAfter(String fromDate) {
        Date date;
        try {
            // Try to parse with the full date-time format
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(fromDate);
        } catch (ParseException e) {
            try {
                // If that fails, try with just the date format
                date = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
            } catch (ParseException e2) {
                throw new IllegalArgumentException("Invalid date format: " + fromDate, e2);
            }
        }
        final Date finalDate = date;
        var list = trainingRepository.findAll().stream()
                .filter(t -> t.getEndTime().after(finalDate))
                .toList();
        return trainingMapper.toDtoList(list);
    }

    @Override
    public List<TrainingDto> getAllTrainingByActivityType(String activityType) {
        var type = ActivityType.valueOf(activityType);
        return trainingMapper.toDtoList(trainingRepository.findByActivityType(type));
    }

    @Override
    public TrainingDto updateTraining(Long id, TrainingDto trainingDto) {
        Long userId = trainingDto.user() != null ? trainingDto.user().id() : null;
        if (userId == null) throw new IllegalArgumentException("User id required!");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var entity = trainingMapper.toEntity(trainingDto, user);
        entity.setId(id);
        var updated = trainingRepository.save(entity);
        return trainingMapper.toDto(updated);
    }

    @Override
    public List<TrainingDto> getAllTrainingsForDedicatedUser(Long userId) {
        return trainingMapper.toDtoList(trainingRepository.findByUserId(userId));
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
