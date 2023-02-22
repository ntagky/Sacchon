package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ChiefDoctorDto;
import gr.codehub.sacchon.app.dto.PatientConsultationCountDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;
import gr.codehub.sacchon.app.model.ChiefDoctor;
import gr.codehub.sacchon.app.repository.CarbsRepository;
import gr.codehub.sacchon.app.repository.ChiefDoctorRepository;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChiefDoctorServImpl implements ChiefDoctorService {
    private final ChiefDoctorRepository doctorRepository;
    private final ConsultationRepository consultationRepository;
    private final CarbsRepository carbsRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<ConsultationsGivenByDoctor>
    getConsultationsBetweenDatesGiven(long id, LocalDate startingDate, LocalDate endingDate){
       return doctorRepository.findConsultationsBetweenGivenDates(id,startingDate,endingDate);
    }

    @Override
    public ChiefDoctorDto createChiefDoctor(ChiefDoctorDto doctorDto){
        //validation
        ChiefDoctor doctor = doctorDto.asChiefDoctor();
        return new ChiefDoctorDto(doctorRepository.save(doctor));
    }

    @Override
    public List<ChiefDoctorDto> readChiefDoctor(){
        return doctorRepository
                .findAll()
                .stream()
                .map(ChiefDoctorDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ChiefDoctorDto readChiefDoctor(long id) throws ChiefDoctorException {
        return new ChiefDoctorDto(readChiefDoctorDb(id));
    }

    // private method created for internal use
    private ChiefDoctor readChiefDoctorDb(long id) throws ChiefDoctorException{
        Optional<ChiefDoctor> chiefDoctorOptional = doctorRepository.findById(id);
        if (chiefDoctorOptional.isPresent())
            return chiefDoctorOptional.get();
        throw new ChiefDoctorException("ChiefDoctor with id " + id + "is not found!");
    }


    @Override
    public boolean updateChiefDoctor(ChiefDoctorDto doctor, long id){
        boolean action;
        try {
            ChiefDoctor dbChiefDoctor = readChiefDoctorDb(id);
            doctorRepository.save(dbChiefDoctor);
            action = true;
        } catch (ChiefDoctorException e){
            action = false;
        }
        return action;
    }

    @Override
    public boolean deleteChiefDoctor(long id) {
        boolean action;
        try {
            ChiefDoctor dbChiefDoctor = readChiefDoctorDb(id);
            doctorRepository.delete(dbChiefDoctor);
            action = true;
        } catch (ChiefDoctorException e) {
            action = false;
        }
        return action;
    }

    @Override
    public List<PatientDto> readPatientsIdWaitingForConsultation(LocalDate dateBefore) {
        List<Long> patientIdsReachedLimit = consultationRepository
                .findPatientsIdWaitingForConsultation(dateBefore)
                .stream()
                .distinct()
                .toList();

        List<Long> patientIdToBeRetrieved = new ArrayList<>();
        patientIdsReachedLimit.forEach(id -> {
            if (carbsRepository.findCountOfCarbsByPatientIdAfterDate(id, dateBefore)  > 30 )
                patientIdToBeRetrieved.add(id);
            }
        );

        return patientRepository.findAllById(patientIdToBeRetrieved)
                .stream()
                .map(PatientDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientConsultationCountDto> readPatientsWithConsultationBetween(LocalDate startingDate, LocalDate endingDate) {
        List<Long> patientIds = consultationRepository.findPatientsIdWithConsultationWithinRange(startingDate, endingDate);

        Map<Long, Long> patientMap = patientIds.stream().collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        List<PatientConsultationCountDto> patientConsultationCountDtoList = new ArrayList<>();

        patientIds.stream().distinct().forEach(id ->
            patientConsultationCountDtoList.add(
                    new PatientConsultationCountDto(
                            patientRepository.findById(id).get(),
                            patientMap.get(id)
                    )
            )
        );
        return patientConsultationCountDtoList;
    }

    @Override
    public List<PatientDto> readInactivePatientsWithinRange(LocalDate startingDate, LocalDate endingDate) {
        List<Long> patientIdsWithActivity = carbsRepository.findCarbsWithinRangeFromPatientId(startingDate, endingDate);
        List<Long> patientIdsWithAccount = patientRepository.readPatientsBySignedDateBefore(endingDate);

        List<PatientDto> inactivePatients = new ArrayList<>();
        patientIdsWithAccount.forEach(
                idWithAccount -> {
                    if (patientIdsWithActivity.stream().noneMatch(id -> id.equals(idWithAccount)))
                        inactivePatients.add(
                                new PatientDto(patientRepository.findById(idWithAccount).get())
                        );
                }
        );

        return inactivePatients;
    }

}
