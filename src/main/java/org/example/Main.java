package org.example;

import model.MapInfo;
import API.KakaoAPIConnector;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            // 기존 dotenv 사용 코드 제거
            // Dotenv dotenv = Dotenv.load();
            // String apiKey = dotenv.get("KAKAO_API_KEY");

            // 환경 변수에서 직접 읽기
            String apiKey = System.getenv("KAKAO_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("환경 변수 'KAKAO_API_KEY'가 설정되지 않았습니다.");
            }

            KakaoAPIConnector conn = new KakaoAPIConnector(apiKey);
            MapInfo mapInfo;

            // Set API configuration
            String apiURL = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=PM9&";
            String myLocURL = "https://dapi.kakao.com/v2/local/search/keyword.json?";

            System.out.print("위치 키워드를 입력하세요: ");
            String loc = br.readLine();
            System.out.print("검색 반경을 입력하세요(1000:1km): ");
            int rad = Integer.parseInt(br.readLine());
            System.out.println("입력한 위치 키워드: " + loc + "\n검색 반경: " + (float) rad / 1000 + "km\n");

            String encodedLoc = URLEncoder.encode(loc, "UTF-8");
            String fullUrl = myLocURL + "&query=" + encodedLoc;
            mapInfo = getMapInfo(conn, fullUrl);
            List<Float> currentLoc = mapInfo.getCurrentLoc();

            String encodedLocX = URLEncoder.encode(String.valueOf(currentLoc.get(0)), "UTF-8");
            String encodedLocY = URLEncoder.encode(String.valueOf(currentLoc.get(1)), "UTF-8");
            String encodedRad = URLEncoder.encode(String.valueOf(rad), "UTF-8");
            String fullApiUrl = apiURL + "&x=" + encodedLocX + "&y=" + encodedLocY + "&radius=" + encodedRad;
            mapInfo = getMapInfo(conn, fullApiUrl);

            mapInfo.display();

            System.out.println("---------------------------\n");

            while (true) {
                System.out.print("kakaomap URL(장소 URL): ");
                String url = br.readLine();
                if (url.equals("exit")) {
                    System.out.println("프로그램 종료\n");
                    break;
                }
                try {
                    openBrowser(url);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openBrowser(String url) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }

    public static MapInfo getMapInfo(KakaoAPIConnector conn, String url) throws IOException {
        conn.connect(url, "get");
        String myLocData = conn.getResponseData();
        return new MapInfo(myLocData);
    }
}
