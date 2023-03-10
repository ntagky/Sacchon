package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Glucose;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@AllArgsConstructor
@Setter
@Getter
public class GlucoseRecordDto {

    private Long id;
    private LocalTime time;
    private BigDecimal measurement;
    private Glucose glucose;

    public GlucoseRecordDto(GlucoseRecord glucoseRecord) {
        if (glucoseRecord == null)
            return;
        time = glucoseRecord.getTime();
        measurement = glucoseRecord.getMeasurement();
    }

    public GlucoseRecord asGlucoseMeasurementRecord() {
        return new GlucoseRecord(id, time, measurement, glucose);
    }

}
