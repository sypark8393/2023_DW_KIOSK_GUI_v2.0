package system.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Scanner;

import system.admin.AdminMode;

public class Main {
	public static char currentMode = 'M';	// 실행 모드: M(main, 메인), U(user, 사용자)
	
	public static Scanner sc = new Scanner(System.in);
	
	public static AdminMode admin = AdminMode.getInstance();	// 관리자 모드 객체
	
	public static KioskFrame kioskFrame = KioskFrame.getInstance(); // 시스템에서 사용할 KioskFrame 객체
	
	// 메인 메소드
	public static void main(String[] args) {
		kioskFrame.getContentPane().addMouseListener(new EnterAdminModeListener());
		
		kioskFrame.initialize(FileCheck.allFilesCheck());	// KioskFrame의 초기화 메소드를 호출하고, 모든 파일이 존재하는지의 여부를 파라미터로 전달
		kioskFrame.setVisible(true);						// 화면에 KioskFrame 객체를 보여줌
		
	}
	
	public static class EnterAdminModeListener extends MouseAdapter {
		private int clickCount = 0;		// 클릭 횟수
		
		/* 메소드명: mouseClicked
		 * 파라미터: MouseEvent e (마우스 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 관리자 전용 커맨드가 수행되면 창을 숨기고 관리자 모드로 진입한다.
		 * 		   (관리자 전용 커맨드: 좌측 상단의 특정 영역(60px * 60px)을 연속으로 3번 클릭)
		 */
		@Override
		 public void mouseClicked(MouseEvent e) {
			int x = e.getX();		// 마우스가 클릭된 위치의 x 좌표값
			int y = e.getY();		// 마우스가 클릭된 위치의 y 좌표값
			
			if(x < 60 && y < 60 && currentMode == 'M') {	// 지정된 영역이 클릭된 경우
				clickCount++;								// 클릭 휫수 1 증가
				
			} else {				// 지정되지 않은 영역이 클릭된 경우
				clickCount = 0;		// 클릭 횟수를 0으로 초기화
				
			}
			
			if(currentMode == 'M' && clickCount == 3) {		// 관리자 모드인 상태에서 3번 클릭된 경우
				kioskFrame.setVisible(false);				// KioskFrame 객체 숨기기
				
				System.out.println("=======================================");
				System.out.println(Calendar.getInstance().getTime().toString() + " :: 메인 :: 관리자 모드로 진입합니다.");
				System.out.println("=======================================");
				
				// 관리자 비밀번호를 입력받아 일치하는지 확인
				System.out.println("관리자 비밀번호를 입력해주세요.");
				System.out.print(" > ");
				String password = sc.nextLine();
				
				if(admin.adminCheck(password)) {	// 비밀번호 일치
					currentMode = 'A';				// 현재 실행 모드: A(관리자)
					admin.startAdminMode(sc);		// 관리자 모드 실행
					
					if(admin.isExit()) {
						System.out.println("=======================================");
						System.out.println(Calendar.getInstance().getTime().toString() + " :: 메인 :: 관리자 요청으로 시스템을 종료합니다.");
						System.out.println("=======================================");
						
						sc.close();
						System.exit(0);
					}
					
				} else {	// 관리자 비밀번호 불일치
					System.out.println("관리자 비밀번호가 일치하지 않습니다.");
				}
				
				// 관리자 모드에서 '관리자 모드 종료'를 선택했거나 관리자 비밀번호가 불일치한 경우 아래 문장 수행
				System.out.println("잠시 후 메인 화면으로 돌아갑니다.");
				
				// 잠시 대기
				try { Thread.sleep(1000); }
				catch (InterruptedException IE) { IE.printStackTrace(); }
				
				currentMode = 'M';	// 현재 실행 모드: M(메인 화면)
				
				// 관리자 전용 커맨드 수행 정보 초기화
				clickCount = 0;
				
				kioskFrame.setVisible(true);		// 키오스크 프레임 창을 띄움
				
			}
			
		}
		
	}
	
}
