-- Cafe 데이터
INSERT INTO cafe (cafe_id, name, latitude, longitude) VALUES (1, '스타벅스 정릉점', '37.7749', '-122.4194');
INSERT INTO cafe (cafe_id, name, latitude, longitude) VALUES (2, '카페나무', '34.0522', '-118.2437');

-- 감성 문서 데이터
INSERT INTO document (document_id, content) VALUES (1, 'ㅁㄴㅇㄹ');
INSERT INTO document (document_id, content) VALUES (2, '말차라떼 아침 무작위 말차 상쾌하게 녹차라떼 시작 텍스트 그린티라떼 부드러운 추가 차가운 깨워 말차 우유 오후 말차 피곤 음료 라떼 점심 말차라떼 졸음 아트 활력 삽입 푸드 운동 신선한 트렌드 지친 카페인 수분 말차파우더 에너지 건강 음료 보충 일본 차 독서 부드러운 아이스 공부 말차라떼 집중력 힐링 필요 음료 차분한 유기농 스트레스 말차 긴장 녹차 하루 풍미 분말 해소 초록 음료 친구 식물성 만남 우유 카페 라떼 시간 초록색 대화 건강 카페 집 말차라떼 편안하게 맛집 쉬고 부드러운 휴식 말차 따뜻한 차 업무 애호가 중간 아시아 잠시 음료 리프레시 차이 바쁜 티 카페 저녁 라떼 식사 비건 디저트 음료 가벼운 말차 마무리 맛 명상 아이스드 요가 말차라떼 마음 차가운 다스린 라떼 안정 말차라떼 카페인 차 히스토리 일본 문화 차 아티스트 건강 트렌드 독특한');
INSERT INTO document (document_id, content) VALUES (3, 'ㅁㄴㅇㄹ');
INSERT INTO document (document_id, content) VALUES (4, 'ㅁㄴㅇㄹ');
INSERT INTO document (document_id, content) VALUES (5, '딸기스무디 아침 무작위 딸기 상쾌하게 음료 시작 텍스트 스무디 부드러운 추가 차가운 깨워 딸기 우유 오후 딸기 피곤 음료 블렌더 점심 딸기스무디 졸음 아트 활력 삽입 푸드 운동 신선한 트렌드 지친 비타민 수분 딸기 맛 에너지 건강 음료 보충 신선한 과일 독서 부드러운 아이스 공부 딸기스무디 집중력 힐링 필요 음료 차분한 유기농 스트레스 딸기 긴장 하루 풍미 해소 초록 음료 친구 식물성 만남 우유 카페 스무디 시간 핑크 대화 건강 카페 집 딸기스무디 편안하게 맛집 쉬고 부드러운 휴식 딸기 따뜻한 과일 업무 애호가 중간 잠시 음료 리프레시 바쁜 비타민 저녁 식사 디저트 비건 가벼운 딸기스무디 마무리 맛 명상 요가 딸기스무디 마음 차가운 다스린 스무디 안정 딸기스무디 비타민 신선한 상큼함 트렌드');

-- Beverage 데이터
INSERT INTO beverage (beverage_id, name, cafe_id, document_id, photo, type, price, caffein, fat, kcal, natrium, protein, sugar)
VALUES (1, '아메리카노', 1, 1, 'americano.jpg', 'COFFEE', 4500, 140, 0, 15, 10, 0, 0);
INSERT INTO beverage (beverage_id, name, cafe_id, document_id, photo, type, price, caffein, fat, kcal, natrium, protein, sugar)
VALUES (2, '말차라떼', 1, 2, 'latte.jpg', 'COFFEE', 4500, 120, 5, 100, 15, 4, 10);
INSERT INTO beverage (beverage_id, name, cafe_id, document_id, photo, type, price, caffein, fat, kcal, natrium, protein, sugar)
VALUES (3, '아이스티', 1, 3, 'lemonade.jpg', 'ADE', 4500, 0, 0, 50, 5, 0, 10);
INSERT INTO beverage (beverage_id, name, cafe_id, document_id, photo, type, price, caffein, fat, kcal, natrium, protein, sugar)
VALUES (4, '얼그레이티', 2, 4, 'greentea.jpg', 'TEA', 4500, 30, 0, 5, 0, 0, 0);
INSERT INTO beverage (beverage_id, name, cafe_id, document_id, photo, type, price, caffein, fat, kcal, natrium, protein, sugar)
VALUES (5, '딸기 스무디', 2, 5, 'smoothie.jpg', 'TEA', 4500, 0, 30, 150, 15, 8, 50);