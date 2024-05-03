# 콘서트 예약 서비스 구현

## 🔎 동시성 문제가 발생할 수 있는 유즈케이스 분석 

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

## [마일스톤](https://github.com/dalle0601/Week3_Concert_Reservation/milestones)

## [요구사항분석](https://github.com/dalle0601/Week3_Concert_Reservation/issues/1)

## [시퀀스 다이어그램](https://github.com/dalle0601/Week3_Concert_Reservation/issues/2)

## [API 명세](https://github.com/dalle0601/Week3_Concert_Reservation/issues/4)

## [ERD / mock API 목록](https://github.com/dalle0601/Week3_Concert_Reservation/issues/3)

## 📂 Swagger
<details>
<summary> Swagger 자세히보기 </summary>
<div markdown="1">

<img width="1422" alt="스크린샷 2024-04-12 오전 10 42 25" src="https://github.com/dalle0601/Week3_Concert_Reservation/assets/33375877/c6b05cbb-87f2-429b-bb56-026be7151504">

</div>
</details>
