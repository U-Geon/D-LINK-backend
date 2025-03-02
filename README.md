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

# 서비스 소개
- Onboarding
<img width="243" alt="image" src="https://github.com/user-attachments/assets/54dd3a11-1a6b-4498-8d27-4cf570b07336" />
<img width="244" alt="image" src="https://github.com/user-attachments/assets/39cba788-9f99-4472-9884-ea5c6489a978" />

- Landing
<img width="257" alt="image" src="https://github.com/user-attachments/assets/b11b2824-2214-4180-af37-c7b119900d37" />

- 추천 Prompt 입력
<img width="413" alt="image" src="https://github.com/user-attachments/assets/460702c2-1f6e-4bb1-bbdf-3272ed90e5e4" />

- 추천 모델 Output
<img width="227" alt="image" src="https://github.com/user-attachments/assets/67a6a402-a047-4e32-bfd6-0eeb38e3a0d3" />

- 추천 음료 중 택 1
<img width="437" alt="image" src="https://github.com/user-attachments/assets/fce73535-b645-4d53-828e-b31c2f897c91" />

- 추천 History
<img width="251" alt="image" src="https://github.com/user-attachments/assets/b1ed3cff-92a1-4dfe-b61d-d0fa0a96a6d6" />

- Community
<img width="490" alt="image" src="https://github.com/user-attachments/assets/e6a3123d-952f-437f-b3ee-3241a898e365" />
<img width="227" alt="image" src="https://github.com/user-attachments/assets/79aebfe4-482d-4863-b627-b90bceda2d6e" />

- MyPage
<img width="275" alt="image" src="https://github.com/user-attachments/assets/d4bc8001-398b-464f-a770-9fb824799247" />


## 모델 서버 API 연동 (음료 추천 기능) 

1. 모델 서버에 사용자가 입력한 prompt와 사용자 위치 기반 주변 카페명 리스트를 API Server에 전송해주면, 해당 데이터들에 대해 API Server에서 데이터를 처리하여 각 카페 별로 판매하는 음료에 대해 1:1 매핑되는 감성 문서들을 prompt와 함께 Model Server에 전송합니다.
2. Model Server는 해당 데이터를 토대로 유사도 분석을 진행하여 prompt에 유사한 음료에 대하여 해당 음료 식별자(id) 와 유사도를 API 서버에 전송합니다.
3. API 서버에서 받은 음료 ID를 토대로 음료 정보들을 추출하여 사용자가 입력한 prompt에 대한 유사도와 함께 클라이언트에게 전송합니다.
<img width="385" alt="스크린샷 2024-09-24 오후 12 43 00" src="https://github.com/user-attachments/assets/fe3e1f24-82f1-495a-80af-bdc51edf86c2">
