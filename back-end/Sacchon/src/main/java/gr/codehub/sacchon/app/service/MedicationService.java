package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.MedicationDto;

public interface MedicationService {
    MedicationDto updateMedicationByConsultationId(MedicationDto medicationDto);
}
