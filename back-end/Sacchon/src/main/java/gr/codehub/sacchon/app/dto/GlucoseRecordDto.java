package gr.codehub.sacchon.app.dto;

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

    private int id;
    private LocalTime time;
    private BigDecimal measurement;
    private GlucoseDto glucose;

    public GlucoseRecordDto(GlucoseRecord glucoseRecord) {
        if (glucoseRecord == null)
            return;
        id = glucoseRecord.getId();
        time = glucoseRecord.getTime();
        measurement = glucoseRecord.getMeasurement();
        glucose = new GlucoseDto(glucoseRecord.getGlucose());
    }

    public GlucoseRecord asGlucoseMeasurementRecord() {
        return new GlucoseRecord(id, time, measurement, glucose.asGlucose());
    }

}
