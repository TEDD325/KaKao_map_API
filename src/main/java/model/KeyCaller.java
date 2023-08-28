package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Data
@NoArgsConstructor
public class KeyCaller {
    public static String call(String filePath, String key) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonStr = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }

            JSONObject jsonObj = new JSONObject(jsonStr.toString());
            return jsonObj.optString(key, "Key not found");
        }
    }
}
