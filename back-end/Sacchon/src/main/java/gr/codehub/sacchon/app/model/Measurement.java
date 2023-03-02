package gr.codehub.sacchon.app.model;

import java.time.LocalDate;

/**
 Generic Interface for representing measurements with units, date and measurement value.
 @param <K>  is the type of the measurement value

 @author Christos Tzoulias
 @version 1.0
 @since 2023-02-28
 */

public interface Measurement<K> {

    String getUNITS();
    LocalDate getDate();
    K getMeasurement();

}
