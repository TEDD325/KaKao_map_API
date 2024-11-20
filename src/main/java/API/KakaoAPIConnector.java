package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class KakaoAPIConnector {
    private String responseData;  // API 응답 데이터
    private String restApiKey;    // REST API 키

    // 기본 생성자
    public KakaoAPIConnector() {
    }

    // 환경 변수에서 API 키를 직접 받는 생성자
    public KakaoAPIConnector(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API 키가 설정되지 않았습니다.");
        }
        this.restApiKey = apiKey;
    }

    // Getter 및 Setter
    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getRestApiKey() {
        return restApiKey;
    }

    public void setRestApiKey(String restApiKey) {
        this.restApiKey = restApiKey;
    }

    // API 호출 메서드
    public String connect(String apiURL, String requestMethod) throws IOException {
        // URL 연결 설정
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // HTTP 요청 메서드 설정
        conn.setRequestMethod(requestMethod.toUpperCase());

        // Authorization 헤더 추가
        conn.setRequestProperty("Authorization", "KakaoAK " + restApiKey);

        // API 응답 데이터를 읽기 위한 BufferedReader 생성
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder res = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            res.append(line);
        }

        responseData = res.toString();   // 응답 데이터 저장

        conn.disconnect();   // 연결 해제

        return responseData;   // API 응답 데이터 반환
    }
}
