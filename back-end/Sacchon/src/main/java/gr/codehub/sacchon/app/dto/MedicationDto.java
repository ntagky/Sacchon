package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Medication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto {
    private Long id;
    private String medName;
    private String dosage;

    public MedicationDto(Medication medication){
        if(medication!=null){
            id = medication.getId();
            medName = medication.getMedName();
            dosage = medication.getDosage();
        }
    }

    public Medication asMedication(){
        Medication medication = new Medication();
        medication.setId(id);
        medication.setMedName(medName);
        medication.setDosage(dosage);
        return medication;
    }
}
