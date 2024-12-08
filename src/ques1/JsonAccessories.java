package ques1;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonAccessories {
        public static Map<String, Map<String, String>> readAccessoriesFromJson(String filePath) {
                Map<String, Map<String, String>> accessories = new HashMap<>();
                try {
                        // Create Path object for the JSON file
                        Path path = Paths.get(filePath);

                        // Read the entire content of the file into a string
                        String content = new String(Files.readAllBytes(path));

                        // Parse the string content into a JSON object
                        JSONObject jsonObject = new JSONObject(content);

                        //Extracting items then putting it in map
                        accessories.put("Exterior Accessories", parseAccessories(jsonObject.getJSONObject("Exterior Accessories")));
                        accessories.put("Interior Accessories", parseAccessories(jsonObject.getJSONObject("Interior Accessories")));
                        accessories.put("Infotainment and Electronics", parseAccessories(jsonObject.getJSONObject("Infotainment and Electronics")));
                        accessories.put("Safety and Security", parseAccessories(jsonObject.getJSONObject("Safety and Security")));
                        accessories.put("Car Care", parseAccessories(jsonObject.getJSONObject("Car Care")));
                } catch (IOException e) {
                        System.out.println(e.getMessage());
                }
                return accessories;
        }

        private static Map<String, String> parseAccessories(JSONObject jsonObject) {
                Map<String, String> accessoriesMap = new HashMap<>();
                for (String key : jsonObject.keySet()) {
                        accessoriesMap.put(key, jsonObject.getString(key));
                }
                return accessoriesMap;
        }
}
