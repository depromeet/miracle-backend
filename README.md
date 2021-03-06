# MIMO

* 사용자들의 미라클 모닝 실천을 도와주는 시스템을 개발한다

## 개발 스택
* Web Application Server 
    * JAVA 11 + Kotlin 1.3
    * Spring Boot 2.2.8(Spring FrameWork, Spring Web MVC)
    * ORM, Spring Data JPA + QueryDSL
    * Unit Testing / Integration Testing + Junit 5
* DataBase
    * MariaDB (Production)
    * H2 (Local)
* Infra
    * AWS EC2 + AWS RDS (MariaDB)
    * CI: GitHub Action + AWS S3 + AWS CodeDeploy
    * WebServer: Nginx (HTTPS, Reverse Proxy Configuration)

## 요구사항

### 스케쥴

* 사용자가 수행하게 되는 task의 최소 단위이다
* 스케쥴은 1개의 채널에 속할 수 있다 ? 속해야 한다?
* 스케쥴의 끝나는 시간이 지난 후에 성공 여부를 업데이트한다
* 스케쥴을 등록할 수 있다
* 스케쥴을 수정할 수 있다
* 스케쥴의 완료 여부를 알려줄 수 있다
* 특정 날짜의 전체 스케쥴을 조회할 수 있다
* 카테고리는 직접 입력할 수 있다
* 반복안함, 매일, 매주, 매달로 반복 설정을 할 수 있다

### 달성현황

* 스케쥴의 끝나는 시간 이후 달성 여부를 업데이트 할 수 있다
* 특정 month 의 스케쥴 달성 이력을 조회할 수 있다
* 이미지, 인증 투표 등의 개념이 들어갈 수도 있다

### 채널

### 인증

### 친구

### 수면패턴

## 모델링

### 스케쥴

* 이름, 날짜, 요일, 반복 설정, 시작 시간, 끝나는 시간, 카테고리, 설명 정보를 가진다
* 채널에 속한 스케쥴 간의 시간은 중복될 수 없다 ?
* 조회 시에 반복설정에 따른 스케쥴 조회에 필요한 데이터
  * 아님이라면 날짜 데이터가 필요하다
  * 매일이라면 무조건 조회한다
  * 매주라면 요일 데이터가 필요하다
  * 매달이라면 날짜 데이터가 필요하다
  * 만약 매년이라면 날짜+달 데이터가 필요하다
* 끝나는 시간은 시작하는 시간보다 커야 한다

### 달성현황

## 환경변수 설정
### Google OAuth
```
# miracle-api/src/main/resources/application-oauth.yml

google:
    oauth:
        client_id: ...
        client_secret: ...
        grant_type: authorization_code
        url: https://oauth2.googleapis.com/token
        redirect-uri: ...
    profile:
        url: https://www.googleapis.com/oauth2/v2/userinfo
```
