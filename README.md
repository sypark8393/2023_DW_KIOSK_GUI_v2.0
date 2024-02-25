# 2023_DW_KIOSK_GUI_v2.0
KIOSK_GUI project v2.0 in 2023

<br>

리팩토링으로 개선된 내용 (*사용자 모드)
- 디렉터리 구조 및 일부 파일명 수정
- 버전 ID 추가
- 패널의 setBounds 메소드는 생성자에서 호출
- 패널, 다이얼로그에 들어가는 모든 컴포넌트는 전역변수화
- 이벤트 리스너 작성 방법 변경 (무명 클래스  내부 클래스)
- 중괄호가 생략된 if [~ else] 구문 중괄호 추가
- 컴포넌트 변수명 수정
- 축약형 표현 삭제
- 컴포넌트 영문명(Button, Label, Panel)은 접미사로 작성
- 이미지 경로는 전역변수에서 초기화 (*파일명이 바뀌는 메뉴 이미지 제외)
- 메소드 -> 리스너 순서로 1차 정렬하고, 각 메소드와 리스너는 프로그램에서 호출되는 순서로 정렬
- 다이얼로그) 타이틀바 삭제
- 관리자 모드를 위한 내용 삭제
- 패널) 배경 이미지 변수 추가
