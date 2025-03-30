### **📍 Project 소개**

[국민대학교 12학점 알파프로젝트]

**AI 기반의 사용자 맞춤 카페 음료 추천 플랫폼 - “DLNK”**

**배경**

- 카페 음료 소비자들의 메뉴 결정 장애 심리로 인해 카페 음료를 선택하기 어려워하는 경향이 있음.
- 푸드테크 산업의 발달로 인해 사람들의 카페 음료 소비량이 증가함
- 설문 조사 결과를 보면 카페 메뉴 선택에 어려움을 겪는 사용자들이 많다는 것을 시사함. 따라서 실제 사용자들의 음료 선택에 도움을 주기 위한 서비스의 필요성을 느끼게 됨.
<img width="939" alt="image" src="https://github.com/user-attachments/assets/031829be-1265-4b94-a75c-1cb24cbd5420" />



**목적**

- 사용자가 어떤 음료수를 마셔야 할 지 고민하고 결정하지 못할 때 인공지능 추천 시스템을 통해 사용자에게 현재 상황에 맞는 음료수를 추천해준다.
- 사용자들이 알지 못했던 음료를 새롭게 추천해 주거나 추천받은 새로운 음료를 시도해 보는 것을 즐기도록 도와준다.
- 다양한 맛있는 음료수와 함께 사용자의 음료 선택을 더욱 다채롭게 만들어 주는 역할을 한다.

**기능**

- 카페 음료 및 디저트 꿀조합 등 정보 교류의 장으로써의 커뮤니티 기능 제공
- 사용자가 입력한 프롬프트 내용을 바탕으로 카카오맵 API를 활용한 내 주변 카페 음료 추천
- 추천 받은 음료 및 선택한 추천 음료 히스토리 기록 제공

### 📍 Information

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
    - **DB** : MySQL 17, Redis
    - **CI/CD** : Docker, GitHub Actions
    - **인증, 인가** : JWT, OAuth2
    - **IDE** : Intellij IDEA
    - **Tools**
        - **Collaboration** : Notion
        - **Version Control** : Git
        - **API Docs.** : Swagger-UI
- **Architecture**
<img width="1184" alt="image" src="https://github.com/user-attachments/assets/331b7483-0617-470e-8ede-a375335d5409" />




## 모델 서버 API 연동 (음료 추천 기능) 

1. 모델 서버에 사용자가 입력한 prompt와 사용자 위치 기반 주변 카페명 리스트를 API Server에 전송해주면, 해당 데이터들에 대해 API Server에서 데이터를 처리하여 각 카페 별로 판매하는 음료에 대해 1:1 매핑되는 감성 문서들을 prompt와 함께 Model Server에 전송합니다.
2. Model Server는 해당 데이터를 토대로 유사도 분석을 진행하여 prompt에 유사한 음료에 대하여 해당 음료 식별자(id) 와 유사도를 API 서버에 전송합니다.
3. API 서버에서 받은 음료 ID를 토대로 음료 정보들을 추출하여 사용자가 입력한 prompt에 대한 유사도와 함께 클라이언트에게 전송합니다.
<img width="385" alt="스크린샷 2024-09-24 오후 12 43 00" src="https://github.com/user-attachments/assets/fe3e1f24-82f1-495a-80af-bdc51edf86c2">
