package ques1;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

public class JsonCar {

    public static Map<String, Map<String, Double>> readCarsFromJson(String filePath) {
        Map<String, Map<String, Double>> carMap = new LinkedHashMap<>();
        try {
            // Create Path object for the JSON file
            Path path = Paths.get(filePath);

            // Read the entire content of the file into a string
            String content = new String(Files.readAllBytes(path));

            // Parse the string content into a JSON array
            JSONArray jsonArray = new JSONArray(content);

            // Iterate over the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject carInfo = jsonArray.getJSONObject(i);
                String name = carInfo.getString("name");
                JSONArray variantsArray = carInfo.getJSONArray("variants");
                JSONArray priceRangeArray = carInfo.getJSONArray("Price_range");

                Map<String, Double> variantPrices = new LinkedHashMap<>();

                for (int j = 0; j < variantsArray.length(); j++) {
                    variantPrices.put(variantsArray.getString(j), priceRangeArray.getDouble(j));
                }

                carMap.put(name, variantPrices);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return carMap;
    }
}
