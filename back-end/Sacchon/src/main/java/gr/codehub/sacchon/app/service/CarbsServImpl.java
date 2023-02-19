package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.CarbsDto;
import gr.codehub.sacchon.app.dto.CarbsFromPersonDto;
import gr.codehub.sacchon.app.exception.CarbsException;
import gr.codehub.sacchon.app.model.Carbs;
import gr.codehub.sacchon.app.repository.CarbsRepository;
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
    public CarbsDto readCarbsById(int id) throws CarbsException {
        return new CarbsDto(readCarbsDb(id));
    }

    @Override
    public List<CarbsFromPersonDto> readCarbsByPatientId(int id) {
        return carbsRepository
                .findCarbsByPatientId(id)
                .stream()
                .map(CarbsFromPersonDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Integer readAverageCarbsIntakeByPatientIdOnSpecificDates(int id, LocalDate startingDate, LocalDate endingDate) {
        return carbsRepository
                .findAverageCarbsByPatientIdOnSpecificDates(id, startingDate, endingDate);
    }

    private Carbs readCarbsDb(int id) throws CarbsException {
        Optional<Carbs> carbsOptional = carbsRepository.findById(id);
        if (carbsOptional.isPresent())
            return carbsOptional.get();
        throw new CarbsException("Carbs with id " + id + " does not exist.");
    }
    @Override
    public boolean updateCarbs(CarbsDto carbsDto, int id) throws CarbsException {
        CarbsDto dbCarbs = readCarbsById(id);
        dbCarbs.setDate(carbsDto.getDate());
        dbCarbs.setMeasurement(carbsDto.getMeasurement());
        dbCarbs.setPatientDto(carbsDto.getPatientDto());
        carbsRepository.save(dbCarbs.asCarbs());
        return true;
    }
    @Override
    public boolean deleteCarbsById(int id) throws CarbsException {
        carbsRepository.delete(readCarbsDb(id));
        return true;
    }
}
