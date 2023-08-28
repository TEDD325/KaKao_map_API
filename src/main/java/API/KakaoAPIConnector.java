package API;

import lombok.*;
import model.KeyCaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAPIConnector {
    private String keyFilePath;        // API 키 파일 경로
    private String keyFileMeta;        // API 키 파일 메타 정보
    private String responseData;       // API 응답 데이터

    @Getter(value = AccessLevel.NONE)
    private String restApiKey;         // REST API 키

    public KakaoAPIConnector(String keyFilePath, String keyFileMeta) {
        this.keyFilePath = keyFilePath;
        this.keyFileMeta = keyFileMeta;
    }

    private String callAPIKey(String keyFilePath, String keyFileMeta) throws IOException {
        return KeyCaller.call(keyFilePath, keyFileMeta);   // API 키를 가져오는 메서드 호출
    }

    public String connect(String apiURL, String requestMethod) throws IOException {
        // REST API 키를 가져옴
        String restApiKey = this.callAPIKey(keyFilePath, keyFileMeta);

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
