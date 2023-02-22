package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
public class GlucoseRecordUpdaterDto {

    private int hour;
    private int minute;
    private int second;
    private BigDecimal measurement;

}
