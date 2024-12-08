package ques1;

import java.util.List;

public class Car {
String carName;
List<String> variants;
List<Double>prices;

    public Car(String carName, List<String> variants, List<Double> price) {
        this.carName = carName;
        this.variants = variants;
        this.prices = price;
    }
}
