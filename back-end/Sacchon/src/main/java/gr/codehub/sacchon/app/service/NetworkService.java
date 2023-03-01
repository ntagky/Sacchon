package gr.codehub.sacchon.app.service;

public interface NetworkService {
    Object[] getNetworkOutput(Double pregnancies, Double glucose, Double bloodPressure, Double skinThickness, Double insulin, Double bmi, Double age, Double dpf);
}
