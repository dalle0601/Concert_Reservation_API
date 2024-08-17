# 콘서트 예약 서비스 구현

본 프로젝트는 콘서트 예약 서비스로, 사용자가 콘서트를 예약할 수 있는 기능을 제공하는 웹 애플리케이션입니다. 
이 시스템은 두 가지 주요 부분으로 구성됩니다
- [Concert_Reservation_UI, 프론트엔드](https://github.com/dalle0601/Concert_Reservation_UI)
- [Concert_Reservation_API. 백엔드](https://github.com/dalle0601/Concert_Reservation_API)

이 두 부분은 각각 독립적으로 개발되어 상호 작용합니다.

---

## 🚩 [마일스톤](https://github.com/dalle0601/Week3_Concert_Reservation/milestones)

## 📑 [요구사항분석](https://github.com/dalle0601/Week3_Concert_Reservation/issues/1)

## 🧭 [시퀀스 다이어그램](https://github.com/dalle0601/Week3_Concert_Reservation/issues/2)

## 🛎️ [API 명세](https://github.com/dalle0601/Week3_Concert_Reservation/issues/4)

## 📀 [ERD / mock API 목록](https://github.com/dalle0601/Week3_Concert_Reservation/issues/3)

---
## 📊 분산 시스템으로의 리팩토링 진행
  <details>
    <summary>콜드플레이 16년 11월 24일 내한 콘서트 동시접속자 수 90만명</summary>
    <br/>
    기존에 이 콘서트 예매 서비스를 구현해봄에 있어서 기능 구현에 초점을 맞췄습니다. <br/>
    하지만 어떤 기회를 통해 좀더 깊게 생각해보게 되었고..<br/>
    좋아하는 밴드의 콘서트가 예전에 90만 정도의 동시접속자수를 기록했다는 내용을 보고 <br/>
    90만 동시접속자의 트래픽을 목표로 (목표는 크게!) 리팩토링을 진행해보고자 합니다.<br/>
    - 고트래픽 처리: 목표는 90만 명의 동시 접속자를 처리할 수 있는 시스템 구축.<br/>
    - 분산 시스템 전환 이유: 단일 서버의 한계를 극복하고, 시스템 확장성과 가용성을 높이기 위함.<br/>
  </details>
  <details>
    <summary>병목 지점 파악</summary>
    - 로그인 : 트래픽이 집중될 때 로그인 요청이 몰리면, 인증 서버나 DB에 부하가 걸릴 수 있음<br/>
    - 대기열 : 동시 접속자가 많을 때 대기열 시스템이 과부하될 가능성이 큽니다. 특히, Redis와 같은 메모리 DB에 부하가 집중<br/>
    - 좌석 예약 :  예매가 열리면 좌석 예약 요청이 폭발적으로 증가할 수 있으며, DB의 트랜잭션 처리 능력이 한계에 도달할 수 있음<br/>
  </details>
  <details>
    <summary>예상 트래픽 산정</summary>
    - 트래픽 산정: 90만 명의 사용자가 동시에 접속할 가능성을 고려해, 각 기능별로 초당 처리해야 할 요청 수를 산정합니다.<br/>
    - 예시: 만약 90만 명 중 10%가 로그인 요청을 보낸다고 가정하면, 초당 약 9만 건의 요청이 발생할 수 있습니다. 이 요청을 처리할 수 있는 서버와 네트워크 용량이 필요합니다.    
  </details>
  <details>
    <summary>분산 아키텍처 설계 (예시)</summary>
    
    > AWS (예상 / 추후 도전)
    - 로드 밸런서: AWS의 ALB or NLB 사용. 이는 트래픽을 여러 서버에 분산시켜 과부하를 방지
    - 서버 확장: AWS EC2 인스턴스를 Auto-scaling 그룹으로 구성해 트래픽 증가 시 자동으로 서버를 추가
    - 캐싱: AWS ElastiCache(Redis)를 사용해 자주 요청되는 데이터를 캐싱하여 DB 부하를 줄임
    - 메세징 시스템 : Kafka를 사용해 결제 기능 등에서의 이벤트 처리

---

    > 실제 도전해볼 과제!
    - 로드밸런서: Nginx를 사용해 로컬 환경에서 트래픽을 분산.
    - 확장 서버: 여러 애플리케이션 서버를 Docker 컨테이너로 구현하여 스케일 아웃
    - 캐싱: Redis를 사용해 캐싱 시스템 구성
    - 메시징: Kafka를 통해 메시징 시스템 구축

---
    > docker compose 이용해 nginx, app1, app2, redis 등등 띄우기
    ```
      version: '3.8'
      
      services:
        # 로드 밸런서
        load_balancer:
          image: nginx:latest
          ports:
            - "80:80"
          volumes:
            - ./config/nginx.conf:/etc/nginx/nginx.conf:ro
          depends_on:
            - app1
            - app2
      
        # app 서버 1
        app1:
          build:
            context: .
            dockerfile: Dockerfile
          ports:
            - "8081:8080"
          environment:
            - SPRING_PROFILES_ACTIVE=dev
          depends_on:
            - redis
            - kafka
      
        # app 서버 2
        app2:
          build:
            context: .
            dockerfile: Dockerfile
          ports:
            - "8082:8080"
          environment:
            - SPRING_PROFILES_ACTIVE=dev
          depends_on:
            - redis
            - kafka
      
        redis:
          image: redis:latest
          ports:
            - "6379:6379"
      
        zookeeper:
          image: confluentinc/cp-zookeeper:latest
          container_name: zookeeper
          environment:
            ZOOKEEPER_SERVER_ID: 1
            ZOOKEEPER_CLIENT_PORT: 2181
            ZOOKEEPER_TICK_TIME: 2000
            ZOOKEEPER_INIT_LIMIT: 5
            ZOOKEEPER_SYNC_LIMIT: 2
          ports:
            - "2181:2181"
      
        kafka:
          image: confluentinc/cp-kafka:latest
          container_name: kafka
          depends_on:
            - zookeeper
          ports:
            - "9092:9092"
          environment:
            KAFKA_BROKER_ID: 1
            KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
            KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:29092,PLAINTEXT_HOST://localhost:9092
            KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
            KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
            KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
            KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      
        mysql:
          container_name: mysql_local
          image: mysql:8.0
          volumes:
            - ./db/conf.d:/etc/mysql/conf.d
            - ./db/initdb.d:/docker-entrypoint-initdb.d
          ports:
            - "3307:3306"
          environment:
            - MYSQL_DATABASE=concert_local
            - MYSQL_USER=
            - MYSQL_PASSWORD=
            - MYSQL_ROOT_PASSWORD=
            - TZ=Asia/Seoul
    ```

---

    > nginx.conf
    ```
      events {
        worker_connections 1024;
      }
      
      http {
        upstream backend {
          server app1:8080;
          server app2:8080;
        }
      
        server {
          listen 80;
      
          location / {
            proxy_pass http://backend;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
          }
        }
      }
    ```
---
  <details>
    <summary>트러블슈팅..</summary>
    <br/>
    위와같이 실행 후 각 메소드를 postman으로 호출 시도했으나 404 에러<br/>
    <br/>
      <details>
        <summary>해결</summary>
        <br/>아직못했지롱 !
      </details>
  </details>
    
  </details>

## 🧩 트랜잭션 범위 이해 및 서비스 확장에 따른 분리와 트랜잭션 처리의 한계 및 해결방안
  <details>
    <summary>기존 결제(Payment) 로직 및 서비스 확장 및 분리</summary>
    
      - 예약 정보 조회 (reservationService.findById)<br/>
      - 사용자 정보 조회 (userService.findUserInfo)<br/>
      - 사용자 포인트를 차감 (userService.paymentPoint)<br/>
      - 예약 상태 변경 (reserved) 및 만료 시간 제거 (reservationService.updateStateAndExpirationTime)<br/>
      - 포인트 히스토리 기록 삽입 (writePointHistoryUseCase.execute)<br/>

      예약, User 관리, Point 관리 측면의 분리

      문제점
      - 서비스를 분리함에따라 분산 트랜잭션으로 이루어지며 각각의 트랜잭션 관리가 복잡해짐

      해결방안
      - 상태 변화를 이벤트로 기록, 처리
      - SAGA 패턴? (더 자세히 알아보자..)
  </details>
  <details>
    <summary>실시간 정보를 데이터 플랫폼에 전달 요구사항에 대한 문제, 해결방안</summary>

    문제점
    - 실시간 데이터를 데이터 플랫폼에 전송하는 과정에서 실패할 경우 전체 트랜잭션이 롤백될 수 있습니다.
    - 실시간 데이터 전송 과정이 지연되면, 사용자 응답 시간이 길어집니다.

    해결방안
    - 비동기 이벤트 기반 처리
      - 결제 완료 후 실시간 데이터를 전송하는 작업을 비동기 이벤트로 처리하여 메인 트랜잭션에서 분리
      - ApplicationEventPublisher를 통해 이벤트를 발행하고, 별도의 이벤트 리스너에서 실시간 데이터를 전송
    - Kafka 도입
      - 결제 완료 이벤트를 Kafka 토픽으로 발행하고, 데이터 플랫폼이 해당 토픽을 구독하여 실시간 데이터를 처리
      
  </details>

---

## ☛ Query 분석 및 DB Index 설계
<details>
  <summary>Query 분석</summary>

  1. concert
     - findByAvailableStartDate
       - 현재시간을 기준으로 콘서트 시작 시간보다 이전이며, 잔여 콘서트좌석이 남아있는  
         콘서트의 리스트를 가져옵니다.
     - findByConcertId
       - concertId로 해당 콘서트의 정보를 가져옵니다.
  2. pointHistory
     - save
       - 포인트 사용, 추가 등의 동작 시 내역이 저장됩니다.
     - findByUserId
       - userId로 pointHistory의 내역을 가져옵니다.
  3. reservation
     - findByStatusInAndConcertId
       - 해당 콘서트의 reserved, temporary 상태의 좌석을 가져옵니다. 
     - findNonAvailableByConcertIdAndSeatId
       - concertId, seatId로 해당 콘서트의 좌석이 available 상태가 아닌지 확인합니다. 
     - updateStateAndExpirationTime
       - 해당 예약번호의 status, 만료시간, 예약시간을 수정합니다.
     - save
       - 예약정보를 저장합니다.
  4. user
     - findByUserId
       - userId로 해당 유저의 정보(id, point)를 가져옵니다.
     - updatePointByUserId
       - 포인트 충전, 사용시에 해당 user의 포인트를 수정합니다.
  
</details>
<details>
  <summary>Index 설계</summary>

1. concert
    - findByAvailableStartDate
      - concert 테이블에 reservation 테이블을 join해서 가져오는데
        reservation에서 해당 concertId의 status가 available이 아닌 컬럼의 갯수를 조회하기에 
        Reservation 테이블의 concertId를 Index로 사용해보기로했습니다.  
        ```
        SELECT c.*, (50 - COUNT(r.ID)) AS seat
        FROM Concert c
        LEFT JOIN Reservation r ON c.Id = r.concert_Id AND r.status <> 'available'
        WHERE c.concert_Date >= '2024-05-01 19:00:00'
        GROUP BY c.Id;
        ```
        <img width="1357" alt="스크린샷 2024-05-10 오전 12 36 55" src="https://github.com/dalle0601/Concert_Reservation_API/assets/33375877/a7cd39f0-615a-4da1-882a-628b35b74b25">
        <br/>reservation테이블에는 총 1229row가 존재하며,
        <br/>concertId를 인덱스로 추가하지 않은경우에 join의 스캔은 50430회, 대략 55ms,
        <br/>concertId를 인덱스로 추가한 경우에 join의 스캔은 1270회, 대략 28ms의 결과가 나왔습니다.
        <br/>로컬환경에서의 테스트결과값이지만 인덱스를 추가했을경우 인덱스를 추가하지 않은경우보다 실행속도 측면에서 약 49% 감소하는 결과를 얻었습니다.
        
     - findByConcertId
      - concertId는 기본키(PK)로 현재 사용중인 H2 데이터베이스 및 MySQL등에서<br />
        PK에 대해 자동으로 Index를 생성하므로 추가적인 Index 처리는 하지 않겠습니다.
2. pointHistory
    - save
      - insert 작업에 대해서는 인덱스 추가로 직접적인 성능 개선이 어렵고, 오히려 인덱스가 많을수록 쓰기 작업이 느려질것이라 판단했습니다.
    - findByUserId
      - userId로 포인트 이력을 조회하는 쿼리이므로 userId를 Index로 사용해보았습니다.
      <img width="461" alt="스크린샷 2024-05-07 오후 6 33 32" src="https://github.com/dalle0601/Concert_Reservation_API/assets/33375877/62e02ab6-671b-4220-82d5-de83c805bec3">
        <br/>point 테이블에는 총 45개의 row가 존재하며,
        <br/>userId로 인덱스를 추가하지 않은경우에 해당 쿼리의 스캔은 46회,
        <br/>userId로 인덱스를 추가한 경우에 해당 쿼리의 스캔은 10회로 줄일 수 있었습니다.
        <br/>* 하지만 포인트 이력을 조회하는 경우보다 데이터 삽입의 작업이 잦을것이라 생각이 들었고, 포인트 이력의 데이터 수도 많지 않을거라 판단되어 point 테이블에는 인덱스를 추가하지 않았습니다. 
3. reservation
    - findNonAvailableByConcertIdAndSeatId
      - concertId와 seatId를 포함하는 복합 Index로 구성하여 사용해보았습니다.
      <img width="822" alt="스크린샷 2024-05-07 오후 7 07 08" src="https://github.com/dalle0601/Concert_Reservation_API/assets/33375877/ba1caf8a-fe82-4d00-844a-0ad1032fd149">
      <br/> reservation 테이블에는 총 105 row가 존재하며,
      <br/>아무런 인덱스를 적용하지 않았을 경우에는 총 106회 스캔,
      <br/>concertId 로만 인덱스를 사용했을 경우에는 총 51회 스캔,
      <br/>concertId, seatId 복합 인덱스를 사용했을 경우에는 2회 스캔의 결과를 확인했습니다.
      <br/>concertId, seatId를 인덱스로 둘 경우 카디널리티가 너무 높아져 인덱스 유지관리비용이 커질것으로 판단됩니다.
    - findByStatusInAndConcertId
      - 이용가능한 콘서트 리스트를 가져오는 쿼리에서 join에 작성된 idx_reservation_concert_id 인덱스를 이용해 concertId를 기준으로 데이터를 필터링 하기에 조회 성능에 도움이 될것이라 판단됩니다.
        <img width="896" alt="스크린샷 2024-05-07 오후 7 18 46" src="https://github.com/dalle0601/Concert_Reservation_API/assets/33375877/144422c7-58cf-43db-83c8-288f3970ca65">
    - updateStateAndExpirationTime
      - Where 조건에 reservaion 테이블의 기본키(PK)로 사용중이라 추가적인 Index 처리는 하지 않았습니다.
      - 실제로 reservationId를 인덱스로 추가하고 테스트해봐도 스캔 결과는 동일했습니다.
4. user
    - findByUserId
    - updatePointByUserId
      - 2개 모두 userId 를 기준으로 데이터를 검색, 갱신 하고있습니다.
        <br /> userId를 index로 사용하여 두 쿼리를 실행시켜본 결과
        <img width="537" alt="스크린샷 2024-05-07 오후 7 39 24" src="https://github.com/dalle0601/Concert_Reservation_API/assets/33375877/2fe5a800-d992-4896-b231-18d81d76f465">
        위와같은 결과를 얻을 수 있었습니다.
   
</details>

## 🎬 대기열 구현 설계
<details>
  <summary>대기열 구현 설계</summary>
    
    API
    - 유저 토큰 발급 요청 (POST /user/token)
        - Request : userId
        - Response : Code, {message, waitCount, expireTime}
        - 유효토큰 발급요청을 합니다. 
        - 유효토큰은 3명만 받을 수 있으며 각 토큰의 만료시간은 발급시간 + 1분입니다.
        - 만약 유효토큰을 가진 사람이 3명이면 대기열에 포함됩니다.
    - 유저 대기열 상태 조회 (GET /user/{userID}/token)
        - Response : Code, {message, token, expiredTime, queuePosition(대기열 순번) }
        - 클라이언트에서 일정기간 (3초)마다 해당 API를 호출합니다. (UI에서 WebWorker로 구현)
        - 대기열에 포함되어있는 user가 내 앞에 몇명이 대기중인지 확인 요청합니다.
        - 만약 유효토큰을 가진 사람이 3명 미만이면 바로 유효토큰을 발급하고 대기열에서 삭제됩니다.
    - 클라이언트에서 일정기간 (3초)마다 해당 API를 호출을 스케줄링으로 임시 구현
        - WaitSchedulerUseCase
        - 유효토큰의 갯수가 3개 미만일경우 
        - 대기열(Redis)에서 다음유저를 가져온다음 해당 유저에게 유효토큰 발급 및 대기열에서 삭제 로직 진행
---
    대기열관련하여 Token(유효토큰)과 waitingQueue(대기열) 2가지로 나눴습니다.
    유효토큰
    - 유효토큰은 Redisson의 RBucket를 활용하여 구현했습니다.
    - key-value 쌍의 "token"+userId : UUID 로 관리되며 
      Bucket.set 메소드를 통해 내부 메커니즘으로 처리되어 자체적으로 설정한 시간의 TTL이 적용됩니다.
    - 설정한 시간이 지나면 레디스에서 해당 데이터를 삭제합니다.
    대기열
    - redis의 Sorted Set 구조를 활용했습니다.
    - 대기열에 사용자가 추가될 때 현재 시간의 타임스탬프를 점수로 사용하며,
      대기열 상태 조회시 유효토큰이 3명 미만이면 가장 오래된 사용자를 유효토큰에 올리고 대기열에서 삭제합니다.
    - 대기열에 존재하는 유저가 재진입 하려고하면 기존 대기열정보를 리턴합니다.
---
</details>

## 🔎 동시성 문제가 발생할 수 있는 유즈케이스 분석 
<details>
  <summary>분석</summary>
  
  ### 1. MakeReservationUseCase (좌석 예약, 임시점유)
  <details>
    <summary>문제점</summary>
    <div markdown="1">
      여러 사용자가 동시에 같은 좌석을 예약할 때 같은 좌석에 대한 예약이 중복되거나, 예약 가능 여부를 확인하는 동안 다른 사용자가 이미 해당 좌석을 예약한 경우.<br />      
    </div>
  </details>
   <details>
    <summary>해결 방안</summary>
    <div markdown="1">
      - Redisson의 분산 락을 이용<br /> 
      - 특정 콘서트의 특정 좌석을 여러인원이 한번에 신청하는 경우이므로 lockKey(concertId + seatId)로 해당 키에 대해 분산 락 시도<br /> 
      - RedissonClient를 사용하여 지정된 키로 RLock 객체를 가져옴, tryLock 메소드를 호출하여 락을 시도. <br /> 
      - 지정된 waitTime 동안 락을 획득할 수 없으면 false를 반환. <br /> 
      - leaseTime 동안 락을 유지한 후에는 자동으로 락이 해제<br /> 
    </div>
  </details>
  
  ### 2. ChargePointUseCase (포인트 충전)
  <details>
    <summary>문제점</summary>
    <div markdown="1">
      같은 사용자의 포인트 잔액을 동시에 충전하는 동작이 발생할 경우.<br />      
    </div>
  </details>
   <details>
    <summary>해결 방안</summary>
    <div markdown="1">
      - 한 유저의 포인트충전이 다양한 곳에서 시도된다는 전제의 동시성 이슈<br /> 
      - 아주 드물게 일어날것, 동시요청 중 한건만 성공해야하는 케이스라 생각이 되어 DataBase의 Optimistic Lock ( 낙관적 락 )을 이용<br /> 
    </div>
  </details>
    
  ### 3. EnterQueueUseCase (대기열 입장)
  <details>
    <summary>문제점</summary>
    <div markdown="1">
      - 현재는 대기열을 데이터베이스에 실질적 유효토큰을 가진 사람들 (Token Table), 대기중인 사람들의 대기열정보 확인 (Queue Table)로 나뉘어있음.<br /> 
      - 지속적인 DB lock과 많은 데이터가 쌓일경우 대기열 몇번째인지 등의 정보를 가져오는데 성능적 이슈가 생길것<br /> 
    </div>
  </details>
   <details>
    <summary>해결 방안</summary>
    <div markdown="1">
      - Redis의 Pub/Sub 모델 사용<br /> 
      - Spin Lock은 대기중인 쓰레드가 lock을 획득할때 까지 반복적으로 검사하기에 대기시간이 짧지않은 대기열의 경우 적절하지 않다고 생각<br /> 
    </div>
  </details>
</details>

--- 

## ⑆ Git Branch
<details>
<summary>Git Branch 전략 수립 </summary>
<div markdown="1">
  <br>
- Main(Master)<br>
  >> production 환경으로의 배포를 위한 브랜치 <br><br>
- Dev<br>
  >> 기능 개발 및 테스트를 위한 브랜치<br>
  >> 기능 추가, 버그 수정 이후 배포 가능한 안정적인 상태일 경우 develop 브랜치를 main(master)브랜치에 merge<br><br>
- Feature <br>
  >> Dev 브랜치에서 분기되어 기능 개발을 위한 브랜치<br>
  >> 개발 완료 이후 Dev브랜치에 merge<br><br>
- Release <br>
  >> 배포를 위한 전용 브랜치<br>
  >> 해당 브랜치에서부터 배포 사이클이 진행되며, 이후 배포와 관련된 수정 등의 작업이 수행<br>
  >> 배포 준비가 완료되면 main(maeter) 브랜치에 merge<br><br>
- Hotfix<br>
  >> 배포한 버전에 긴급 수정사항이 있을때 main(master)브랜치에서 분기되어 사용할 브랜치<br><br>
</div>
</details>

---

## 📂 Swagger
<details>
<summary> Swagger 자세히보기 </summary>
<div markdown="1">

<img width="1422" alt="스크린샷 2024-04-12 오전 10 42 25" src="https://github.com/dalle0601/Week3_Concert_Reservation/assets/33375877/c6b05cbb-87f2-429b-bb56-026be7151504">

</div>
</details>

--- 
