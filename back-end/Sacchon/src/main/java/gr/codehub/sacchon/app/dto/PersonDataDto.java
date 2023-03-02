package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDataDto {

    private LocalDate localDate;
    private String day;
    private int carbs;
    private BigDecimal averageGlucose;
    private int glucoseRecordsNumber;
    private long consultationId;
    private String consultationStatus;

}
