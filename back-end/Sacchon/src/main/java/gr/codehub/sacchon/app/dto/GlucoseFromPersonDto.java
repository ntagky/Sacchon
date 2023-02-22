package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Glucose;
import gr.codehub.sacchon.app.model.Measurement;
import gr.codehub.sacchon.app.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GlucoseFromPersonDto implements Measurement<List<GlucoseRecordFromDayDto>> {

    private Long id;
    private final String UNITS = "mg/dL";
    private LocalDate date;
    private List<GlucoseRecordFromDayDto> measurement;

    public GlucoseFromPersonDto(Glucose glucose) {
        if (glucose == null)
            return;
        id = glucose.getId();
        date = glucose.getDate();
        measurement = glucose.getMeasurement()
                .stream()
                .map(GlucoseRecordFromDayDto::new)
                .collect(Collectors.toList());
    }

    public Glucose asGlucose(Patient patient) {
        return new Glucose(
                id,
                date,
                null,
                patient
        );
    }

}
