package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.exception.ConsultationException;
import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConsultationServImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;

    @Override
    public ConsultationDto createConsultation(ConsultationDto consultationDto){
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
    public ConsultationDto readConsultation(int id) throws ConsultationException {
        return new ConsultationDto(readConsultationDb(id));
    }

//    @Override
//    public List<ConsultationDto> readConsultationByPatientId(int id) {
//        return consultationRepository
//                .findConsultationByPatientId(id)
//                .stream()
//                .map(ConsultationDto::new)
//                .collect(Collectors.toList());
//    }

    // private method created for internal use
    private Consultation readConsultationDb(int id) throws ConsultationException{
        Optional<Consultation> consultationOptional = consultationRepository.findById(id);
        if (consultationOptional.isPresent())
            return consultationOptional.get();
        throw new ConsultationException("Consultation with id " + id + " does not exist!");
    }

    @Override
    public boolean updateConsultation(ConsultationDto consultationDto, int id){
        boolean action;
        try {
            Consultation dbConsultation = readConsultationDb(id);
            dbConsultation.setDoctorName(consultationDto.getDoctorDto().asDoctor().getLastName());
            dbConsultation.setDoctorEmail(consultationDto.getDoctorDto().asDoctor().getEmail());
            dbConsultation.setDateCreated(consultationDto.getDateCreated());
            dbConsultation.setSeenConsultation(consultationDto.isSeenConsultation());
            dbConsultation.setMedications(consultationDto.getMedications());
            dbConsultation.setDetails(consultationDto.getDetails());
            dbConsultation.setDoctor(consultationDto.getDoctorDto().asDoctor());
//            dbConsultation.setDoctorDto(consultationDto.getDoctorDto()); // how??
            dbConsultation.setPatient(consultationDto.getPatientDto().asPatient());
            consultationRepository.save(dbConsultation);
            action = true;
        } catch (ConsultationException e){
            action = false;
        }
        return action;
    }

    @Override
    public boolean deleteConsultation(int id) {
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
}
