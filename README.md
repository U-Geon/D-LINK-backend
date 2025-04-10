## **📍 Project 소개**

[국민대학교 12학점 알파프로젝트] **AI 기반의 사용자 맞춤 카페 음료 추천 플랫폼 - “DLNK”** <br><br>

## **배경**

- 카페 음료 소비자들의 메뉴 결정 장애 심리로 인해 카페 음료를 선택하기 어려워하는 경향이 있음.
- 푸드테크 산업의 발달로 인해 사람들의 카페 음료 소비량이 증가함
- 설문 조사 결과를 보면 카페 메뉴 선택에 어려움을 겪는 사용자들이 많다는 것을 시사함. 따라서 실제 사용자들의 음료 선택에 도움을 주기 위한 서비스의 필요성을 느끼게 됨.
<img width="939" alt="image" src="https://github.com/user-attachments/assets/031829be-1265-4b94-a75c-1cb24cbd5420" />



## **목적**

- 사용자가 어떤 음료수를 마셔야 할 지 고민하고 결정하지 못할 때 인공지능 추천 시스템을 통해 사용자에게 현재 상황에 맞는 음료수를 추천해준다.
- 사용자들이 알지 못했던 음료를 새롭게 추천해 주거나 추천받은 새로운 음료를 시도해 보는 것을 즐기도록 도와준다.
- 다양한 맛있는 음료수와 함께 사용자의 음료 선택을 더욱 다채롭게 만들어 주는 역할을 한다.

## **기능**

- 카페 음료 및 디저트 꿀조합 등 정보 교류의 장으로써의 커뮤니티 기능 제공
- 사용자가 입력한 프롬프트 내용을 바탕으로 카카오맵 API를 활용한 내 주변 카페 음료 추천
- 추천 받은 음료 및 선택한 추천 음료 히스토리 기록 제공

## 📍 Information

- **개발 기간** : 2024.01 ~ 2024.05 (5개월)
- **플랫폼** : Web
- **개발 인원** : 6명
- **담당 역할**
    - 카카오 소셜 로그인 및 회원 관리 API (기여도 100%)
    - Spring WebFlux를 활용한 모델 서버 API 호출 및 데이터 후처리 로직 구현 (기여도 100%)
    - 도메인 별 CRUD API 구현 (기여도 100%)
    - Web Server Infra 구축 (기여도 100%)
    - Nginx를 활용한 HTTPS 적용 (기여도 100%)
- **개발 환경**
    - **Language** : Java 17 (OpenJDK 17)
    - **Web Server** : Apache Tomcat, AWS EC2, ubuntu
    - **Framework** : Spring Boot 3.x, Spring Security 6.x, Spring WebFlux
    - **DB** : MySQL 17
    - **CI/CD** : Docker, GitHub Actions
    - **인증, 인가** : JWT, OAuth2
    - **IDE** : Intellij IDEA
    - **Tools**
        - **Collaboration** : Notion
        - **Version Control** : Git
        - **API Docs.** : Swagger-UI
- **Architecture**
<img width="1184" alt="image" src="https://github.com/user-attachments/assets/331b7483-0617-470e-8ede-a375335d5409" />

## 📍 프로젝트 설치 및 실행 방법

1. 리포지토리를 클론합니다.

``` 
$ git clone https://github.com/U-Geon/clerker-backend.git
```

2. 각 `application-*.yml` 파일들을 생성해줍니다.

- `main/resource/application.yml`
```yaml
app:
  version: 1.0.1

spring:
  servlet:
    multipart:
      maxFileSize: 30MB # 파일 하나의 최대 크기
      maxRequestSize: 100MB  # 한 번에 최대 업로드 가능 용량
  config:
    import: classpath:application-private.yml
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ${kakao.id} # 앱키 -> REST API 키
            client-secret: ${kakao.secret} # 카카오 로그인 -> 보안 -> Client Secret 코드
            authorization-grant-type: authorization_code
            redirect-uri: ${kakao.redirect}
            client-authentication-method: client_secret_post # POST 사용 불가 (setRequestEntityConverter를 사용하여 POST를 지원하는 인스턴스를 제공해야 함.)
            scope:
              - account_email
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize # "인가 코드 받기" 항목
            token-uri: https://kauth.kakao.com/oauth/token # "토큰 받기" 항목
            user-info-uri: https://kapi.kakao.com/v2/user/me # "사용자 정보 가져오기" 항목
            user-name-attribute: id # 식별자 . 카카오의 경우 "id" 사용

  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: ${mysql.url}
    username: ${mysql.username}
    password: ${mysql.password}
  jpa:
    database: mysql
    hibernate:
      ddl-auto: create # create none update
    properties:
      hibernate:
        format_sql: true

  sql:
    init:
      mode: always

logging:
  level:
    org.hibernate.SQL: debug


springdoc:
  swagger-ui:
    path: /swagger-ui.html # 스웨거 접근 경로
    groups-order: DESC # API 그룹 표시 순서

    # alpha: 알파벳 순 정렬
    # method: OpenAPI specification file에 원하는 태그 정렬 방식 직접 기재
    tags-sorter: alpha # 태그 정렬 순서.

    operationsSorter: method # 컨트롤러 정렬 순서
    disable-swagger-default-url: true # swagger-ui default url인 petstore html의 비활성화 설정
    display-request-duration: true # swagger-ui default url인 petstore html의 비활성화 설정
  api-docs:
    path: /api-docs # openAPI 접근 경로. default 값은 /v3/api-docs 이다.
  show-actuator: true # Spring Actuator의 endpoint까지 보여줄 것인지?
  default-consumes-media-type: application/json # request media type 의 기본 값
  default-produces-media-type: application/json # response media type 의 기본 값
  paths-to-match: /api/** # 해당 패턴에 매칭되는 controller만 swagger-ui에 노출한다.

cloud:
  aws:
    s3:
      bucket: ${s3.bucket}
    stack:
      auto: false
    region.static: ${s3.region}
    credentials:
      accessKey: ${s3.accessKey}
      secretKey: ${s3.secretKey}
```

- `main/resource/application-private.yml`
```yaml
kakao:
  id: 
  secret: 
  redirect: "http://localhost:8080/login/oauth2/code/kakao"

mysql:
  url: jdbc:mysql://127.0.0.1:3306/alpha?serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useUnicode=true
  username: 
  password: 


jwt:
  secret-key: 
  access-token:
    expiration: 168 # 1주일
  refresh-token:
    expiration: 336 # 2주일

s3:
  bucket: 
  accessKey: 
  secretKey: 
  region: ap-northeast-2

baseUrl:
  model: 
  client-redirect-url: 
  server: 
```

3. jar file 빌드 후 프로젝트를 실행합니다.
```Bash
chmod +x ./gradlew
./gradlew clean build
```
