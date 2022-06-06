# 2차 PR todo list

- [ ] Native 쿼리에 대한 사용 의문?
- [x] select 절 * 를 직접 명시로 변경
- [x] ItemResponse에 setter 고민
- [x] SearchConditionRequest에 date 변수명 변경하기
- [x] SearchController 메서드 이름 HouseController로 변경하기
- [x] House에서 wishList 역참조 뺴기
- [x] Location 지우기
- [x] migration 쿼리에 대해 알아보기
- [x] User 의 hostedHouse를 houses 로 변경하기
- [ ] JPA에서 추가 모듈로 공간데이터를 사용하는 기능은 제공 사용
- [X] Service에 transaction 달기
- [X] HouseController 에서 사용하는 Service계층에서 dto반환하기
- [X] GeometryUtils Point 변환 함수의 예외처리
- [X] application.yml(test용) 주석 지우기

---

# 3차 PR todo list
- [X] Spring Actuator 학습
- [X] HouseCountResponse 이름 변경
- [X] DTO -> equals & hashcode override
- [X] 테스트에서 하우스 생성 부분을 팩토리 메서드로 구현
- [X] test에서 파라미터를 변수명으로 추출(10, 100 등)

--- 

# 4차 PR todo list
- [ ] call back url 수정
- [ ] Token parsing 부분 Optional 제거
- [ ] OAuthLoginController log 구체화
- [X] calculateFee 메서드 위치 이동
- [ ] OAuth property yml에 정의하기
- [ ] RESTTemplate, ObjectMapper를 DI로 변경
- [ ] setProperty(snakecase) 수정
- [ ] concurrentHashMap으로 변경
- [ ] OAuthServer Component로 변경
