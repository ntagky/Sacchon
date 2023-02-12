package gr.codehub.sacchon.app.model;

import java.time.LocalDate;

public interface Measurement<K> {

    String getUNITS();
    LocalDate getDate();
    K getMeasurement();

}
