package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.MedicationDto;

import java.util.List;

public interface MedicationService {
    MedicationDto updateMedicationByConsultationId(MedicationDto medicationDto);

    List<MedicationDto> findMedications(long consultationId);
}
