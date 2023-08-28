package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapInfo {
    private JSONArray data;
    private List<Float> currentLoc;

    public MapInfo(String resData) {
        JSONObject jsonObject = new JSONObject(resData);
        data = jsonObject.getJSONArray("documents");
    }

    public List<Float> getCurrentLoc() {
        currentLoc = new ArrayList<>();

        JSONObject dataObject = data.getJSONObject(0);

        float xCoordinate = Float.parseFloat(dataObject.getString("x"));
        float yCoordinate = Float.parseFloat(dataObject.getString("y"));

        currentLoc.add(xCoordinate);
        currentLoc.add(yCoordinate);

        return currentLoc;
    }

    public void display() {
        int maxDisplayCount = Math.min(data.length(), 10);

        try {
            System.out.println("**약국 검색 결과**");
            for (int i = 0; i < maxDisplayCount; i++) {
                JSONObject dataObject = data.getJSONObject(i);

                String placeUrl = dataObject.getString("place_url"); // 장소 url
                String placeName = dataObject.getString("place_name"); // 상호명
                String address = dataObject.getString("address_name"); // 주소
                String phone = dataObject.getString("phone"); // 전화번호
                int distanceMeters = Integer.parseInt(dataObject.getString("distance")); // 거리(M)
                float distanceKilometers = (float) distanceMeters / 1000; // 거리(KM)

                System.out.println("- 장소 URL(지도 위치): " + placeUrl);
                System.out.println("- 상호명: " + placeName);
                System.out.println("- 주소: " + address);
                System.out.println("- 전화번호: " + phone);
                System.out.println("- 거리(km): " + distanceKilometers);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
