-- Cafe 데이터
INSERT INTO cafe (cafe_id, name, latitude, longitude) VALUES (1, '언더그라운드커피웍스', '37.6100862904966', '126.995125457406');
INSERT INTO cafe (cafe_id, name, latitude, longitude) VALUES (2, '카페그리다', '37.60932585128242', '127.00497528986767');
INSERT INTO cafe (cafe_id, name, latitude, longitude) VALUES (3, '플레이스엔', '37.61049717085312', '126.99589444374668');
INSERT INTO cafe (cafe_id, name, latitude, longitude) VALUES (4, '카페테인', '37.6081906479131', '127.003809838548');

-- 감성 문서 데이터
INSERT INTO document (document_id, content) VALUES (1, 'ㅁㄴㅇㄹ');
INSERT INTO document (document_id, content) VALUES (2, '말차라떼 아침 무작위 말차 상쾌하게 녹차라떼 시작 텍스트 그린티라떼 부드러운 추가 차가운 깨워 말차 우유 오후 말차 피곤 음료 라떼 점심 말차라떼 졸음 아트 활력 삽입 푸드 운동 신선한 트렌드 지친 카페인 수분 말차파우더 에너지 건강 음료 보충 일본 차 독서 부드러운 아이스 공부 말차라떼 집중력 힐링 필요 음료 차분한 유기농 스트레스 말차 긴장 녹차 하루 풍미 분말 해소 초록 음료 친구 식물성 만남 우유 카페 라떼 시간 초록색 대화 건강 카페 집 말차라떼 편안하게 맛집 쉬고 부드러운 휴식 말차 따뜻한 차 업무 애호가 중간 아시아 잠시 음료 리프레시 차이 바쁜 티 카페 저녁 라떼 식사 비건 디저트 음료 가벼운 말차 마무리 맛 명상 아이스드 요가 말차라떼 마음 차가운 다스린 라떼 안정 말차라떼 카페인 차 히스토리 일본 문화 차 아티스트 건강 트렌드 독특한');
INSERT INTO document (document_id, content) VALUES (3, 'ㅁㄴㅇㄹ');
INSERT INTO document (document_id, content) VALUES (4, 'ㅁㄴㅇㄹ');
INSERT INTO document (document_id, content) VALUES (5, '딸기스무디 아침 무작위 딸기 상쾌하게 음료 시작 텍스트 스무디 부드러운 추가 차가운 깨워 딸기 우유 오후 딸기 피곤 음료 블렌더 점심 딸기스무디 졸음 아트 활력 삽입 푸드 운동 신선한 트렌드 지친 비타민 수분 딸기 맛 에너지 건강 음료 보충 신선한 과일 독서 부드러운 아이스 공부 딸기스무디 집중력 힐링 필요 음료 차분한 유기농 스트레스 딸기 긴장 하루 풍미 해소 초록 음료 친구 식물성 만남 우유 카페 스무디 시간 핑크 대화 건강 카페 집 딸기스무디 편안하게 맛집 쉬고 부드러운 휴식 딸기 따뜻한 과일 업무 애호가 중간 잠시 음료 리프레시 바쁜 비타민 저녁 식사 디저트 비건 가벼운 딸기스무디 마무리 맛 명상 요가 딸기스무디 마음 차가운 다스린 스무디 안정 딸기스무디 비타민 신선한 상큼함 트렌드');


-- Beverage 데이터
INSERT INTO beverage (beverage_id, name, cafe_id, document_id, type, price, caffein, fat, kcal, natrium, protein, sugar) VALUES
 (1, 'ICE 아메리카노', 1, 1, 'COFFEE', 3900, 180, 0, 0, 10, 1, 0),
 (2, 'HOT 아메리카노', 1, 2, 'COFFEE', 3700, 180, 0, 0, 10, 1, 0),
 (3, 'ICE 카페라떼', 1, 3, 'COFFEE', 4500, 225, 8, 250, 180, 12, 18),
 (4, 'HOT 카페라떼', 1, 4, 'COFFEE', 4000, 225, 8, 250, 180, 12, 18),
 (5, 'ICE 카푸치노', 1, 5, 'COFFEE', 4500, 140, 0, 149, 10, 0, 0);
--  (6, 'HOT 카푸치노', 1, 6, 'COFFEE', 4000, 140, 0, 149, 10, 0, 0),
--  (7, 'ICE 바닐라라떼', 1, 7, 'COFFEE', 4800, 140, 0, 15, 10, 0, 0),
--  (8, 'HOT 바닐라라떼', 1, 8, 'COFFEE', 4300, 140, 0, 15, 10, 0, 0),
--  (9, 'ICE 카라멜마키야또', 1, 9, 'COFFEE', 5000, 140, 0, 15, 10, 0, 0),
--  (10, 'HOT 카라멜마키야또', 1, 10, 'COFFEE', 4500, 140, 0, 15, 10, 0, 0),
--  (11, 'ICE 카페크림모카', 1, 11, 'COFFEE', 5300, 140, 0, 15, 10, 0, 0),
--  (12, 'HOT 카페크림모카', 1, 12, 'COFFEE', 4800, 140, 0, 15, 10, 0, 0),
--  (13, 'ICE 레몬차', 1, 13, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (14, 'HOT 레몬차', 1, 14, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (15, 'ICE 자몽차', 1, 15, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (16, 'HOT 자몽차', 1, 16, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (17, '레몬에이드', 1, 17, 'ADE', 4500, 140, 0, 15, 10, 0, 0),
--  (18, '자몽에이드', 1, 18, 'ADE', 4500, 140, 0, 15, 10, 0, 0),
--  (19, '라임에이드', 1, 19, 'ADE', 4500, 140, 0, 15, 10, 0, 0),
--  (20, 'ICE 캐모마일티', 1, 20, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (21, 'HOT 캐모마일티', 1, 21, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (22, 'ICE 루이보스티', 1, 22, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (23, 'HOT 루이보스티', 1, 23, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (24, 'ICE 페퍼민트', 1, 24, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (25, 'HOT 페퍼민트', 1, 25, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (26, 'ICE 레몬진저', 1, 26, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (27, 'HOT 레몬진저', 1, 27, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (28, 'ICE 허브티', 1, 28, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (29, 'HOT 허브티', 1, 29, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (30, 'ICE 밀크티', 1, 30, 'TEA', 4500, 140, 0, 15, 10, 0, 0),
--  (31, 'HOT 밀크티', 1, 31, 'TEA', 4000, 140, 0, 15, 10, 0, 0),
--  (32, '핫초코', 1, 32, 'BEVERAGE', 4000, 140, 0, 15, 10, 0, 0),
--  (33, '아이스초코', 1, 33, 'BEVERAGE', 4500, 140, 0, 15, 10, 0, 0),
--  (34, 'ICE 녹차라떼', 1, 34, 'BEVERAGE', 4500, 140, 0, 15, 10, 0, 0),
--  (35, 'HOT 녹차라떼', 1, 35, 'BEVERAGE', 4000, 140, 0, 15, 10, 0, 0),
--  (36, '딸기우유', 1, 36, 'BEVERAGE', 4500, 140, 0, 15, 10, 0, 0);