package system.main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import system.user.UserModePanel;

public class KioskFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567858222243318209L; // 버전 ID

	public static MainPanel mainPanel; 				// 메인 패널
	public static UserModePanel userModePanel;		// 사용자 모드 패널
	
	public static ErrorPanel errorPanel;			// 에러 패널
	
	private static KioskFrame kioskFrame = new KioskFrame(); 	// 싱글톤 객체 선언 및 생성
	
	/* 생성자: KioskFrame
	 * 파라미터: 없음
	 * 기능 설명: 프레임의 속성을 설정하고 필요한 패널을 생성 및 추가한다.
	 */
	private KioskFrame() {
		// Frame 속성
		this.setSize(720, 1000);			// 프레임 크기 (720px * 1000px)
		this.setResizable(false);			// 프레임 크기 변경 불가
		this.setUndecorated(true);			// 프레임 타이틀바 제거
		this.setAlwaysOnTop(true);			// 프레임을 항상 최상단으로 띄우기
		this.setLocationRelativeTo(null);	// 프레임을 화면 중앙에 띄우기
		
		this.setIconImage(new ImageIcon("src/img/logo.png").getImage());	// 프레임 아이콘 설정
	}
	
	/* 메소드명: getInstance
	 * 파라미터: 없음
	 * 반환값: KioskFrame kioskFrame (KioskFrame의 싱글톤 객체)
	 * 기능 설명: 멤버 필드로 선언 및 생성된 KioskFrame 객체를 반환한다.
	 */
	public static KioskFrame getInstance() {
		return kioskFrame;
	}
	
	/* 메소드명: initialize
	 * 파라미터: boolean allFilesCheck (true: 모든 주문 처리 파일이 존재하므로 정상 흐름으로 진행 가능, false: 모든 주문 처리 파일이 존재하지 않으므로 정상 흐름으로 진행 불가)
	 * 반환값: 없음
	 * 기능 설명: 모든 주문 처리 파일의 존재 여부에 따라 알맞은 화면을 보여준다.
	 */
	public void initialize(boolean allFilesCheck) {
		if(allFilesCheck) {
			// 메인 패널
			mainPanel = new MainPanel();	// 메인 패널 생성
			this.add(mainPanel);			// 프레임에 메인 패널 추가
			
			// 사용자 모드 패널
			userModePanel = UserModePanel.getInstance();	// 사용자 모드 패널 싱글톤 객체 받아오기
			userModePanel.setVisible(false);				// 사용자 모드 패널 감추기
			this.add(userModePanel);						// 프레임에 사용자 모드 패널 추가
			
		} else {
			// 에러 패널
			errorPanel = new ErrorPanel();	// 에러 패널 생성
			this.add(errorPanel);			// 프레임에 에러 패널 추가
		}
	}
}
