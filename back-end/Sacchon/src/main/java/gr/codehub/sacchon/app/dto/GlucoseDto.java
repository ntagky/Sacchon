package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Glucose;
import gr.codehub.sacchon.app.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Setter
@Getter
public class GlucoseDto implements Measurement<List<GlucoseRecordDto>> {

    private int id;
    private final String UNITS = "mg/dL";
    private LocalDate date;
    private List<GlucoseRecordDto> measurement;

    public GlucoseDto(Glucose glucose) {
        if (glucose == null)
            return;
        date = glucose.getDate();
        measurement = glucose.getMeasurement()
                .stream()
                .map(GlucoseRecordDto::new)
                .collect(Collectors.toList());
    }

    public Glucose asGlucose() {
        return new Glucose(
                id,
                date,
                measurement
                        .stream()
                        .map(GlucoseRecordDto::asGlucoseMeasurementRecord)
                        .collect(Collectors.toList())
        );
    }

}
