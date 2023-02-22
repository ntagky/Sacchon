package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ChiefDoctorDto;
import gr.codehub.sacchon.app.dto.PatientConsultationCountDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;
import gr.codehub.sacchon.app.model.ChiefDoctor;
import gr.codehub.sacchon.app.repository.*;
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
    private final ChiefDoctorRepository chiefDoctorRepository;
    private final ConsultationRepository consultationRepository;
    private final CarbsRepository carbsRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<ConsultationsGivenByDoctor>
    getConsultationsBetweenDatesGiven(long id, LocalDate startingDate, LocalDate endingDate){
       return chiefDoctorRepository.findConsultationsBetweenGivenDates(id,startingDate,endingDate);
    }

    @Override
    public ChiefDoctorDto createChiefDoctor(ChiefDoctorDto doctorDto){
        //validation
        ChiefDoctor doctor = doctorDto.asChiefDoctor();
        return new ChiefDoctorDto(chiefDoctorRepository.save(doctor));
    }

    @Override
    public List<ChiefDoctorDto> readChiefDoctor(){
        return chiefDoctorRepository
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
        Optional<ChiefDoctor> chiefDoctorOptional = chiefDoctorRepository.findById(id);
        if (chiefDoctorOptional.isPresent())
            return chiefDoctorOptional.get();
        throw new ChiefDoctorException("ChiefDoctor with id " + id + "is not found!");
    }


    @Override
    public boolean updateChiefDoctor(ChiefDoctorDto doctor, long id){
        boolean action;
        try {
            ChiefDoctor dbChiefDoctor = readChiefDoctorDb(id);
            chiefDoctorRepository.save(dbChiefDoctor);
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
            chiefDoctorRepository.delete(dbChiefDoctor);
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

    @Override
    public List<DoctorDto> readInactiveDoctorsWithinRange(LocalDate startingDate, LocalDate endingDate) {
        List<Long> doctorIdsWithActivity = consultationRepository.findDoctorsIdWithConsultationWithinRange(startingDate, endingDate);
        List<Long> doctorIdsWithAccount = doctorRepository.readDoctorsBySignedDateBefore(endingDate);

        List<DoctorDto> inactiveDoctors = new ArrayList<>();
        doctorIdsWithAccount.forEach(
                idWithAccount -> {
                    if (doctorIdsWithActivity.stream().noneMatch(id -> id.equals(idWithAccount)))
                        inactiveDoctors.add(
                                new DoctorDto(doctorRepository.findById(idWithAccount).get())
                        );
                }
        );

        return inactiveDoctors;
    }

}
