# backend
[2024-1] 알파프로젝트 D:LINK 백엔드

- 개요 : 대규모 언어 모델을 활용한 사용자 맞춤 음료 추천 플랫폼

# Tools
- **FrameWork** : Spring Boot
- **DB** : MySQL
- **API Specification** : Swagger-ui
- **Cloud**
  - AWS EC2
  - Docker
  - AWS S3
- **Cooperation Tool**
  - GitHub
  - Notion

# 주요 기능
## 1. CI/CD 기능 구현
github actions 워크 플로우를 작성하여 docker에 이미지를 업로드 하고 ec2에서 자동으로 기존의 실행중인 컨테이너를 중지하고, 중지된 컨테이너를 삭제 후 업로드 한 이미지를 다운로드 받아 실행하는 워크플로우까지 작성했습니다. 

하지만 EC2에서 이루어지는 CD 작업에 문제가 있어 개발 단계에서는 수동으로 EC2에서 docker 명령어를 통해 직접 이미지를 pull 받아왔습니다. 

## 2. kakao Social login
OAuth2 라이브러리 + JWT + Spring security를 사용하여 카카오 소셜 로그인을 구현했습니다. 

하지만 여기서 좀 아쉬웠던 점은 redis를 사용하여 refresh token을 저장하는 과정을 생략하고 매번 access token의 유효성을 체크한 뒤 만료되었을 경우 재발급하는 식으로 코드를 짰습니다.
이 부분은 개선하고 공부해야할 것 같습니다.

## 3. JPA Native Query 작성.
클라이언트에서 보내준 cafe list를 토대로 조건부 쿼리를 작성해야 했기 때문에, Spring Data JPA에서 동적 쿼리를 지원하지 않기에 직접 네이티브 쿼리를 작성하였습니다. 
