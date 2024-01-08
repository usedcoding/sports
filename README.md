## 서비스명
BUDDY
### 서비스 설명

<br />
- 헬스장 찾기, 일일권 검색 및 운동 파트너를 구할 수 있는 서비스
  <br>
- 체육관을 이용하는 도중 일일권을 일일이 알아보거나, 같이 운동하고 싶지만 사람을 구할 수 없을 때 느끼는 불편함을 해소 하고자 하는 아이디어에 의해 개발 착수
- <br>
- 현재 개발은 간단한 게시판과 데이터만 들어가 있지만 차후 다양한 기술을 도입하며 지속적으로 개발할 예정
<br/>


## 🛠 개발환경
- 
SpringBoot, Spring Security, JPA, MariaDB, Intellij, thymeleaf
<br/>
<br/>

## ☁️ ERD

![Buddy erd]![erd](https://github.com/usedcoding/sports/assets/136568257/7e0c4cb6-9498-483d-a9c8-7346bd58cc08)
<br>
<br>

## 👀 시연영상
[![이미지 텍스트](스크린샷 이미지)](유투브링크)

[![Video Label](http://img.youtube.com/vi/'유튜브주소의id'/0.jpg)](https://youtu.be/'유튜브주소의id')

## 🔥 트러블 슈팅

### 🚨 Issue 1
### 🚧 이슈 제목

A. validation 출력 오류
<br>
<br>
문제점 설명
체육관 데이터 중 image file 저장 과정에서 validation이 적용되지 않는 오류 발생
<br>
## 🛑 원인
- validation annotion 중 @NotEmpty는 String 자료형에서만 적용되는 annotion이다. 하지만 image는 MUltipartFile 자료형을 사용해서 문제가 발생 했다.
<br>
<br>

## 🚥 해결
- @NotEmpty annotation을 @NotNull annotation으로 교체하여 문제 해결. @NotNull은 Object 자료형에 사용하는 annotation.

### 🚨 Issue 2
### 🚧 이슈 제목

A. 파트너 게시글에서 신청 댓글  트러블
<br>
<br>
파트너 게시글에서 신청 댓글 호출 과정에서 해당 댓글만 호출되는 것이 아닌 전체 리스트가 호출 되는 트러블 발생
<br>
## 🛑 원인
- 신청 댓글에 해당하는 ID가 아닌 전체 리스트를 출력하여 댓글 데이터가 들어가는 모든 게시글에 같은 리스트가 호출되었다.
<br>
<br>

## 🚥 해결
- 신청 댓글 리스트 호출이 아닌 파트너 게시글에 저장된 신청 댓글 ID 데이터를 호출하여 해결. partner.offer

### 🚨 Issue 3
### 🚧 이슈 제목

A. 이슈 내역
<br>
<br>
문제점 설명
<br>
## 🛑 원인
- ...
<br>
<br>

## 🚥 해결
- ...
