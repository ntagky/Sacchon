package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.MedicationDto;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MedicationDto> findMedications(long consultationId) {
        return medicationRepository
                .findMedicationsByConsId(consultationId)
                .stream()
                .map(MedicationDto::new)
                .collect(Collectors.toList());
    }
}
