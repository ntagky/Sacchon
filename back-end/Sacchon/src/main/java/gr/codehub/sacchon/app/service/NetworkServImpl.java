package gr.codehub.sacchon.app.service;

import lombok.AllArgsConstructor;
import org.pmml4s.model.Model;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
@AllArgsConstructor
public class NetworkServImpl implements NetworkService {
    private static final Model model = Model.fromFile("network/model.pmml");
    private static final Model scaler = Model.fromFile("network/scaler.pmml");

    public Object[] getPredictedProbability(Map<String, Double> values) {
        Object[] valuesMap = Arrays.stream(model.inputNames())
                .map(values::get)
                .toArray();

        Object[] normalized = scaler.predict(valuesMap);

        return model.predict(normalized);
    }

    public Object[] getNetworkOutput(Double pregnancies, Double glucose, Double bloodPressure, Double skinThickness, Double insulin, Double bmi, Double age, Double dpf) {
        Map<String, Double> values = Map.of(
                "Pregnancies", pregnancies,
                "Glucose", glucose,
                "BloodPressure", bloodPressure,
                "SkinThickness", skinThickness,
                "Insulin", insulin,
                "BMI", bmi,
                "DiabetesPedigreeFunction", age,
                "Age", dpf
        );

        return getPredictedProbability(values);
    }
}
