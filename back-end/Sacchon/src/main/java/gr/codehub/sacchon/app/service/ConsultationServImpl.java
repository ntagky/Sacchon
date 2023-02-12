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
public class ConsultationServImpl implements ConsultationServices{
    private final ConsultationRepository doctorRepository;

    @Override
    public ConsultationDto createConsultation(ConsultationDto consultationDto){
        //validation
        Consultation doctor = consultationDto.asConsultation();
        return new ConsultationDto(doctorRepository.save(doctor));
    }

    @Override
    public List<ConsultationDto> readConsultation(){
        return doctorRepository
                .findAll()
                .stream()
                .map(ConsultationDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultationDto readConsultation(int id) throws ConsultationException {
        return new ConsultationDto(readConsultationDb(id));
    }

    // private method created for internal use
    private Consultation readConsultationDb(int id) throws ConsultationException{
        Optional<Consultation> consultationOptional = doctorRepository.findById(id);
        if (consultationOptional.isPresent())
            return consultationOptional.get();
        throw new ConsultationException("Consultation with id " + id + "is not found!");
    }

    @Override
    public boolean updateConsultation(ConsultationDto consultation, int id){
        boolean action;
        try {
            Consultation dbConsultation = readConsultationDb(id);
            dbConsultation.setDoctorName(consultation.getDoctorName()); // ???
            dbConsultation.setDateCreated(consultation.getDateCreated());
            dbConsultation.setDateChanged(consultation.getDateChanged());
            dbConsultation.setSeenConsultation(consultation.getSeenConsultation());
            dbConsultation.setDetails(consultation.getDetails());
            doctorRepository.save(dbConsultation);
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
            doctorRepository.delete(dbConsultation);
            action = true;
        } catch (ConsultationException e) {
            action = false;
        }
        return action;
    }
}
