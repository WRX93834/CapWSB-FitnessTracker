package pl.wsb.fitnesstracker.training.api;

import java.util.List;

public interface TrainingService {
    // Pobierz wszystkie treningi
    List<TrainingDto> getAllTrainings();

    // Utwórz nowy trening
    TrainingDto createTraining(TrainingDto trainingDto);

    // Pobierz wszystkie zakończone treningi po dacie
    List<TrainingDto> getAllFinishedTrainingsAfter(String fromDate);

    // Pobierz wszystkie treningi po typie aktywności (np. "RUNNING")
    List<TrainingDto> getAllTrainingByActivityType(String activityType);

    // Zaktualizuj trening o danym id
    TrainingDto updateTraining(Long id, TrainingDto trainingDto);

    // Pobierz wszystkie treningi danego użytkownika
    List<TrainingDto> getAllTrainingsForDedicatedUser(Long userId);

    // Usuń trening po id
    void deleteTraining(Long id);
}
