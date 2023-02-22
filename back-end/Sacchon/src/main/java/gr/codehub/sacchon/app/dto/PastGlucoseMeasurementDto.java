package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class PastGlucoseMeasurementDto {
    private BigDecimal measurement;
    private LocalTime time;
    private LocalDate date;



    public PastGlucoseMeasurementDto(BigDecimal measurement, LocalTime time, LocalDate date) {
        this.measurement = measurement;
        this.time = time;
        this.date = date;
    }

    public BigDecimal getMeasurement() {
        return measurement;
    }

    public void setMeasurement(BigDecimal measurement) {
        this.measurement = measurement;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}