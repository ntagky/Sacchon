package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.GlucoseException;
import gr.codehub.sacchon.app.model.Glucose;
import gr.codehub.sacchon.app.repository.GlucoseRecordRepository;
import gr.codehub.sacchon.app.repository.GlucoseRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GlucoseServImpl implements GlucoseService {
    private final PatientRepository patientRepository;
    private final GlucoseRepository glucoseRepository;
    private final GlucoseRecordRepository glucoseRecordRepository;

    @Override
    public GlucoseDto createGlucose(GlucoseDto glucoseDto) {
        return new GlucoseDto(
                glucoseRepository.save(glucoseDto.asGlucose())
        );
    }
    @Override
    public List<GlucoseDto> readGlucose() {
        return glucoseRepository
                .findAll()
                .stream()
                .map(GlucoseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<GlucoseFromPersonDto> readGlucoseByPatientId(long id) {
        return glucoseRepository
                .findGlucoseByPatientId(id)
                .stream()
                .map(GlucoseFromPersonDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BigDecimal> readDailyAverageGlucoseByPatientIdOnSpecificDates(long id, LocalDate startingDate, LocalDate endingDate) {
        List<Long> glucoseIds = glucoseRepository
                .findGlucoseDatesAndIdsByPatientIdOnSpecificDates(id, startingDate, endingDate)
                .stream()
                .toList();

        List<BigDecimal> averageDailyGlucose = new ArrayList<>();
        glucoseIds.forEach(glId ->
            averageDailyGlucose.add(
                    glucoseRecordRepository
                        .findAverageGlucoseRecordsByGlucoseId(glId)
            )
        );
        return averageDailyGlucose;
    }

    private Glucose readGlucoseDb(long id) throws GlucoseException {
        Optional<Glucose> glucoseOptional = glucoseRepository.findById(id);
        if (glucoseOptional.isPresent())
            return glucoseOptional.get();
        throw new GlucoseException("Glucose with id " + id + " does not exist.");
    }
    @Override
    public GlucoseDto readGlucoseById(long id) throws GlucoseException {
        return new GlucoseDto(readGlucoseDb(id));
    }
    @Override
    public boolean updateGlucose(GlucoseDto glucoseDto, long id) {
        GlucoseDto newGlucoseDto = new GlucoseDto();
        newGlucoseDto.setId(glucoseDto.getId());
        newGlucoseDto.setMeasurement(glucoseDto.getMeasurement());
        newGlucoseDto.setPatientDto(glucoseDto.getPatientDto());
        newGlucoseDto.setDate(glucoseDto.getDate());
        glucoseRepository.save(newGlucoseDto.asGlucose());
        glucoseRepository.save(newGlucoseDto.asGlucose());
        return true;
    }
    @Override
    public boolean deleteGlucoseById(long id) throws GlucoseException {
        glucoseRepository.delete(readGlucoseDb(id));
        return true;
    }

    @Override
    public boolean deleteGlucoseByPatientIdAndDate(long patientId, LocalDate date) {
        Long glucoseId = glucoseRepository.findGlucoseInSpecificDateByPatientId(patientId, date).getId();
        if (glucoseId == null)
            return false;
        System.out.println("Glucose id=" + glucoseId);
        glucoseRecordRepository.deleteGlucoseRecordByGlucoseId(glucoseId);
        System.out.println("Before");
        glucoseRepository.deleteGlucoseByPatientIdAndDate(patientId, date);
        return true;
    }

    @Override
    public long createGlucoseByPatientId(long id, GlucoseInitiatorDto glucoseInitiatorDto) {
        Glucose glucose = glucoseRepository.save(
                glucoseInitiatorDto.asGlucose(patientRepository.findById(id).get())
        );

        glucoseRecordRepository.save(glucoseInitiatorDto.asGlucoseRecord(glucose));
        return glucose.getId();
    }

    @Override
    public Long findGlucoseIdInSpecificDateByPatientId(long patientId, LocalDate givenDate) {
        return glucoseRepository.findGlucoseIdInSpecificDateByPatientId(patientId, givenDate);
    }

    @Override
    public Long createGlucoseByPatientIdAtDate(long patientId, LocalDate date, GlucoseRecordUpdaterDto glucoseRecordUpdaterDto) {
        Glucose glucose = glucoseRepository.save(new Glucose(
                0L,
                date,
                null,
                patientRepository.findById(patientId).get()
        ));
        return glucoseRecordRepository.save(glucoseRecordUpdaterDto.asGlucoseRecord(glucose)).getId();
    }

}