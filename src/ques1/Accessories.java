package ques1;

import java.util.List;

public class Accessories {
    public List<String> exteriorAccessories;
    public List<String> interiorAccessories;
    public List<String> infotainmentAndElectronics;
    public List<String> safetyAndSecurity;
    public List<String> carCare;

    // Constructor
    public Accessories(List<String> exteriorAccessories, List<String> interiorAccessories,
                       List<String> infotainmentAndElectronics, List<String> safetyAndSecurity,
                       List<String> carCare) {
        this.exteriorAccessories = exteriorAccessories;
        this.interiorAccessories = interiorAccessories;
        this.infotainmentAndElectronics = infotainmentAndElectronics;
        this.safetyAndSecurity = safetyAndSecurity;
        this.carCare = carCare;
    }

}
