# 위치기반 장소 검색 Java 애플리케이션 개발
> ```(Location Based Place Search Java Application Development)```

이 Java 어플리케이션은 유저의 콘솔 입력에 기반하여 특정 위치 반경의 약국을 검색합니다.

Kakao 로컬 REST API를 이용하였습니다.

## 목차
- [문제 설명](#문제-설명)
- [예시 입력 및 출력](#예시-입력-및-출력)
- [의존성](#의존성)

## 문제 설명

### 단계 1: 카카오 API 키 획득
### 단계 2: 로컬 REST API 사용
### 단계 3: Java 어플리케이션 구현
1. 키보드로 특정 위치 키워드와 검색 반경을 입력
2. 입력한 키워드를 기반으로 위도(latitude)와 경도(longitude)를 추출
3. 추출한 위치를 사용하여 입력한 반경(radius) 내에서 약국을 검색
4. 검색 결과(JSON)에서 원하는 정보를 추출하여 표시(상위 ```10개 결과에 대한 추출된 데이터를 표시```)
5. 검색된 결과에서 장소 URL을 입력하면 브라우저에 해당 kakaomap이 출력되도록 함
6. exit를 입력하면 종료


## 예시 입력 및 출력

### 입력 화면

```cmd
위치 키워드를 입력하세요:  
검색 반경을 입력하세요(1000:1km):  
```

### 출력 화면

```cmd
입력한 위치 키워드:  
검색 반경:  

**약국 검색 결과**
- 장소 URL(지도 위치):
- 상호명:
- 주소:
- 전화번호:
- 거리(km):

kakaomap URL(장소 URL):http://place.map.kakao.com/26338954 -> 검색된 결과에서 장소 URL을 복사하여 붙여넣기 한 후 엔터 -> 브라우져가 실행
kakaomap URL(장소 URL):exit -> exit 입력하면 프로그램이 종료된다.

프로그램 종료
```

## 의존성
- httpclient (4.5.13)
- okhttp ( 4.10.0)
- json (20160810)
- lombok (1.18.24)

## Troubleshooting
- 로컬 실행
  - `mvn compile exec:java -Dexec.mainClass="org.example.Main"`
- pom.xml 파일 변경 또는 프로젝트 이름 변경 시
  - `mvn clean package`
  - `jar tf target/Find_Pharn_Near_Me-1.0-SNAPSHOT.jar`
  - `jar xf target/Find_Pharn_Near_Me-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF\ncat META-INF/MANIFEST.MF`
  - `export KAKAO_API_KEY=YOUR_KEY`
  - `java -jar target/Find_Pharn_Near_Me-1.0-SNAPSHOT.jar`
- 도커 이미지 생성 및 실행 시
  - .env 필요
  - `docker build -t find_pharm_near_me_app_image .`
  - `docker run -it --env-file .env find_pharm_near_me_app_image`