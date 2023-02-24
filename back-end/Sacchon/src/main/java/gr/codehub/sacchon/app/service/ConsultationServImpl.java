package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ConsultationException;
import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.ConsultationStatus;
import gr.codehub.sacchon.app.repository.CarbsRepository;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConsultationServImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;

    @Override
    public ConsultationDto createConsultation(ConsultationReceivedDto consultationDto){
        //validation
        Consultation consultation = consultationDto.asConsultation();
        return new ConsultationDto(consultationRepository.save(consultation));
    }

    @Override
    public List<ConsultationDto> readConsultation(){
        return consultationRepository
                .findAll()
                .stream()
                .map(ConsultationDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultationDto readConsultation(long id) throws ConsultationException {
        return new ConsultationDto(readConsultationDb(id));
    }

    @Override
    public ConsultationModifiedDto readConsultationModified(long id) throws ConsultationException {
        return new ConsultationModifiedDto(readConsultationDb(id));
    }

    @Override
    public List<ConsultationDto> readConsultationByPatientId(long id) {
        return consultationRepository
                .findConsultationByPatientId(id)
                .stream()
                .map(ConsultationDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultationBasicInfoDto> findConsultationInfoByPatientId(long id) {
        return consultationRepository
                .findConsultationInfoByPatientId(id)
                .stream()
                .map(ConsultationBasicInfoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findPatientWithActiveConsultation(LocalDate dateGiven) {
        return consultationRepository.findPatientWithActiveConsultation(dateGiven);
    }

    // private method created for internal use
    private Consultation readConsultationDb(long id) throws ConsultationException{
        Optional<Consultation> consultationOptional = consultationRepository.findById(id);
        if (consultationOptional.isPresent())
            return consultationOptional.get();
        throw new ConsultationException("Consultation with id " + id + " does not exist!");
    }

    @Override
    public boolean updateConsultation(ConsultationReceivedDto consultationDto, long id){
        boolean action;
        try {
            Consultation dbConsultation = readConsultationDb(id);
//            dbConsultation.setDoctorFirstName(consultationDto.getDoctorDto().asDoctor().getFirstName());
//            dbConsultation.setDoctorLastName(consultationDto.getDoctorDto().asDoctor().getLastName());
//            dbConsultation.setDoctorEmail(consultationDto.getDoctorDto().asDoctor().getEmail());
//            dbConsultation.setDateCreated(consultationDto.getDateCreated());
            dbConsultation.setSeenConsultation(consultationDto.isSeenConsultation());
            dbConsultation.setMedications(consultationDto.getMedications());
            dbConsultation.setDetails(consultationDto.getDetails());
//            dbConsultation.setDoctor(consultationDto.getDoctorDto().asDoctor());
//            dbConsultation.setDoctorDto(consultationDto.getDoctorDto()); // how??
//            dbConsultation.setPatient(consultationDto.getPatientDto().asPatient());
            consultationRepository.save(dbConsultation);
            action = true;
        } catch (ConsultationException e){
            action = false;
        }
        return action;
    }

    @Override
    public boolean deleteConsultation(long id) {
        boolean action;
        try {
            Consultation dbConsultation = readConsultationDb(id);
            consultationRepository.delete(dbConsultation);
            action = true;
        } catch (ConsultationException e) {
            action = false;
        }
        return action;
    }

    @Override
    public ConsultationReceivedDto updateConsultationFromDoctorByPatientId(ConsultationReceivedDto consultationReceivedDto) {

        consultationReceivedDto.asConsultation();

//        consultationRepository.deleteMedicationsByConsultationId(consultationReceivedDto.getId());
//        consultationRepository.addMedicationsByConsultationId(consultationReceivedDto.getId(), ??? );

        consultationRepository.updateConsultationFromDoctorByPatientId(
                consultationReceivedDto.getId(),
                consultationReceivedDto.getDetails(),
                consultationReceivedDto.isSeenConsultation());

        return consultationReceivedDto;
    }

    @Override
    public List<AllConsultationsReceivedForOnePatientDto> getAllConsultationsReceivedForPatient(long patientId) {
        List<Object[]> results = consultationRepository.findAllConsultationsReceivedForPatient(patientId);
        List<AllConsultationsReceivedForOnePatientDto> dtos = new ArrayList<>();
        for (Object[] result : results) {
            AllConsultationsReceivedForOnePatientDto dto = new AllConsultationsReceivedForOnePatientDto();
            dto.setDoctor_first_name((String) result[0]);
            dto.setDoctor_last_name((String) result[1]);
            dto.setDoctor_email((String) result[2]);
            dto.setDate_created(((java.sql.Date) result[3]).toLocalDate());
            dto.setDetails((String) result[4]);
            dtos.add(dto);
        }
        return dtos;
    }

//    @Override
//    public List<Long> findPatientsWaitingForConsultation(LocalDate dateBefore) {
//        List<Long> patientIds = consultationRepository.findPatientsWaitingForConsultation(dateBefore);
//
//        patientIds.forEach(patient -> {
//            // consultationRepository.findConsultationByPatientId()
//            carbsRepository.findCarbsByPatientId(patient);
//        });
//
//
//        return null;
//    }


    @Override
    public ConsultationPureDto findConsultationIdInAndStatusSpecificDate(long patientId, LocalDate dateGiven, LocalDate previousDays) {
        Long consultationId = consultationRepository.findConsultationIdInSpecificDate(patientId, dateGiven, previousDays);
        LocalDate latestConsultation = consultationRepository.findLatestConsultationByPatientId(patientId);

        assert dateGiven.isAfter(previousDays);

        if ((dateGiven.isAfter(latestConsultation) || dateGiven.isEqual(latestConsultation)) &&
                previousDays.isBefore(latestConsultation))
            return new ConsultationPureDto(consultationId, ConsultationStatus.ACTIVE.name());

        if (dateGiven.isBefore(latestConsultation) && previousDays.isBefore(latestConsultation))
            if (consultationId != null)
                return new ConsultationPureDto(consultationId, ConsultationStatus.EXPIRED.name());

        if ((dateGiven.isAfter(latestConsultation) || dateGiven.isEqual(latestConsultation)) &&
                (previousDays.isEqual(latestConsultation) || previousDays.isAfter(latestConsultation)))
            return new ConsultationPureDto(-1, ConsultationStatus.AWAITING.name());

        return new ConsultationPureDto(-1, ConsultationStatus.NONE.name());
    }
}
