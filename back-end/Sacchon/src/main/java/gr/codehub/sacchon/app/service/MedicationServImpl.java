package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.MedicationDto;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicationServImpl implements MedicationService{
    private final MedicationRepository medicationRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    public MedicationDto updateMedicationByConsultationId(MedicationDto medicationDto) {

        long consId = medicationRepository.getConsIdByMedId(medicationDto.getId());
        consultationRepository.updateSeenConsultationById(consId);

        medicationRepository.updateMedicationByConsultationId(
                medicationDto.getId(),
                medicationDto.getMedName(),
                medicationDto.getDosage());

        return medicationDto;
    }
}
