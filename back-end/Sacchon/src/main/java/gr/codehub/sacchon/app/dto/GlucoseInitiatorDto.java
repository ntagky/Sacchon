package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Glucose;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import gr.codehub.sacchon.app.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GlucoseInitiatorDto {
    private long id;
    private LocalDate localDate;
    private int hour;
    private int minute;
    private int second;
    private BigDecimal measurement;

    public Glucose asGlucose(Patient patient) {
        Glucose glucose = new Glucose();
        glucose.setId(id);
        glucose.setDate(localDate);
        glucose.setMeasurement(null);
        glucose.setPatient(patient);
        return glucose;
    }

    public GlucoseRecord asGlucoseRecord(Glucose glucose) {
        return new GlucoseRecord(id, LocalTime.of(hour, minute, second), measurement, glucose);
    }
}
