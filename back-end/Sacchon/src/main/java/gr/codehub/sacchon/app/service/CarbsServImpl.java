package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.CarbsDto;
import gr.codehub.sacchon.app.dto.CarbsFromPersonDto;
import gr.codehub.sacchon.app.exception.CarbsException;
import gr.codehub.sacchon.app.model.Carbs;
import gr.codehub.sacchon.app.repository.CarbsRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarbsServImpl implements CarbsService {
    private final CarbsRepository carbsRepository;
    private final PatientRepository patientRepository;

    @Override
    public CarbsDto createCarbsIntake(CarbsDto carbsDto) {
        return new CarbsDto(
                carbsRepository.save(carbsDto.asCarbs())
        );
    }
    @Override
    public List<CarbsDto> readCarbs() {
        return carbsRepository
                .findAll()
                .stream()
                .map(CarbsDto::new)
                .collect(Collectors.toList());
    }
    @Override
    public CarbsDto readCarbsById(long id) throws CarbsException {
        return new CarbsDto(readCarbsDb(id));
    }

    @Override
    public List<CarbsFromPersonDto> readCarbsByPatientId(long id) {
        return carbsRepository
                .findCarbsByPatientId(id)
                .stream()
                .map(CarbsFromPersonDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Integer readAverageCarbsIntakeByPatientIdOnSpecificDates(long id, LocalDate startingDate, LocalDate endingDate) {
        return carbsRepository
                .findAverageCarbsByPatientIdOnSpecificDates(id, startingDate, endingDate);
    }

    private Carbs readCarbsDb(long id) throws CarbsException {
        Optional<Carbs> carbsOptional = carbsRepository.findById(id);
        if (carbsOptional.isPresent())
            return carbsOptional.get();
        throw new CarbsException("Carbs with id " + id + " does not exist.");
    }
    @Override
    public boolean updateCarbsById(long id, int measurement) {
        carbsRepository.updateCarbsById(id, measurement);
        return true;
    }
    @Override
    public boolean deleteCarbsById(long id) throws CarbsException {
        carbsRepository.delete(readCarbsDb(id));
        return true;
    }
    @Override
    public long createCarbsByPatientId(long patientId, CarbsFromPersonDto carbsDto) {
        return carbsRepository.save(
                carbsDto.asCarbs(patientRepository.findById(patientId).get())
        ).getId();
    }

    @Override
    public int readCarbsByPatientIdInSpecificDate(long patientId, LocalDate givenData) {
        Integer carbs = carbsRepository.readCarbsByPatientIdInSpecificDate(patientId, givenData);
        return carbs == null ? 0 : carbs;
    }
}
