# backend
2024년도 1학기 알파프로젝트 D:LINK 백엔드

- 개요 : 대규모 언어 모델을 활용한 사용자 맞춤 음료 추천 플랫폼

# Tools
- **FrameWork** : Spring Boot
- **Cloud**
    - AWS EC2
    - AWS S3
    - Docker
- **CI/CD**
    - GitHub Actions
- **DB**
    - MySQL
- **버전 관리 / 이슈 관리**
    - GitHub / github projects
- **협업 툴**
    - Notion

# 상세 기술
## 1. CI/CD 기능 구현
github actions 워크 플로우를 작성하여 docker에 이미지를 업로드 하고 ec2에서 자동으로 기존의 실행중인 컨테이너를 중지하고, 중지된 컨테이너를 삭제 후 업로드 한 이미지를 다운로드 받아 실행하는 워크플로우까지 작성했습니다. 

하지만 EC2에서 이루어지는 CD 작업에 문제가 있어 개발 단계에서는 수동으로 EC2에서 docker 명령어를 통해 직접 이미지를 pull 받아왔습니다. 

## 2. kakao Social login
OAuth2 라이브러리 + JWT + Spring security를 사용하여 카카오 소셜 로그인을 구현했습니다. 

하지만 여기서 좀 아쉬웠던 점은 redis를 사용하여 refresh token을 저장하는 과정을 생략하고 매번 access token의 유효성을 체크한 뒤 만료되었을 경우 재발급하는 식으로 코드를 짰습니다.
이 부분은 개선하고 공부해야할 것 같습니다.

## 3. 커뮤니티 기능
게시판 CRUD API와 게시글 좋아요 기능을 구현하였습니다. 또한 게시글에 사용될 사진 업로드를 위해 AWS S3를 활용하여 게시글 사진 업로드 기능을 구현했습니다.

## 4. 모델 서버 API 연동 (음료 추천 기능) 

1. 모델 서버에 사용자가 입력한 prompt와 사용자 위치 기반 주변 카페명 리스트를 API Server에 전송해주면, 해당 데이터들에 대해 API Server에서 데이터를 처리하여 각 카페 별로 판매하는 음료에 대해 1:1 매핑되는 감성 문서들을 prompt와 함께 Model Server에 전송합니다.
2. Model Server는 해당 데이터를 토대로 유사도 분석을 진행하여 prompt에 유사한 음료에 대하여 해당 음료 식별자(id) 와 유사도를 API 서버에 전송해줍니다.
3. API 서버에서 받은 음료 ID를 토대로 음료 정보들을 추출하여 사용자가 입력한 prompt에 대한 유사도와 함께 클라이언트에게 전송해준다.
<img width="385" alt="스크린샷 2024-09-24 오후 12 43 00" src="https://github.com/user-attachments/assets/fe3e1f24-82f1-495a-80af-bdc51edf86c2">


## 3. JPA Native Query 작성.
클라이언트에서 보내준 cafe list를 토대로 음료 데이터를 SELECT해야 했습니다. 따라서 조건부 쿼리를 작성해야 했기 때문에, Spring Data JPA에서 동적 쿼리를 지원하지 않기에 직접 네이티브 쿼리를 작성하였습니다.
