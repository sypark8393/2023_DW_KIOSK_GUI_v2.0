package system.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import system.custom.CustomButton;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5130816546151575907L; // 버전 ID
	
	private String backgroundImageSource = "src/img/background_main.png";	// 배경 이미지 경로
	
	private CustomButton startButton; // 시작 버튼
	
	/* 생성자: MainPanel
	 * 파라미터: 없음
	 * 기능 설명: 메인 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public MainPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 시작 버튼
		String startButtonText = "<html>주문하시려면 여기를 <style=\"font-size:40px\"><font color=#FFC843>클릭</font></style>해주세요.</html>";	// 시작 버튼에 들어갈 텍스트
		startButton = new CustomButton(startButtonText);	// 텍스트를 가지는 버튼 생성
		startButton.applyStartDesign(0, 720);				// 생성된 버튼에 시작 기능을 의미하는 디자인 적용
		startButton.addActionListener(new StartListener());	// 시작 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(startButton);								// 패널에 시작 버튼 추가
	}
	
	/* 메소드명: paintComponent
	 * 파라미터: Graphics g (컴포넌트의 그래픽 객체, 별도 선언은 필요하지 않음)
	 * 반환값: 없음
	 * 기능 설명: 컴포넌트가 그려져야 하는 시점(컴포넌트의 크기가 변경되거나 컴포넌트가 가려졌다가 다시 나타날 때)에 자동으로 컴포넌트를 다시 그린다.
	 * 		   (자동으로 호출되기 때문에 코드 상에서 직접 호출할 필요가 없다.)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);													// 부모 클래스가 그려야하는 사항을 그릴 수 있도록 부모 클래스의 paintComponent 메소드 호출
		Image backgroundImage = new ImageIcon(backgroundImageSource).getImage();	// 배경 이미지를 Image 객체로 받아옴
		g.drawImage(backgroundImage, 0, 0, this); 									// 패널의 (0, 0)을 원점으로 하여 배경 이미지를 그림
	}
	
	/* 클래스명: StartListener
	 * 설명: 시작 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class StartListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 시작 버튼 클릭 이벤트 발생 시 사용자 모드로 진입한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("=======================================");
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 메인 :: 사용자 모드로 진입합니다.");
			
			Main.currentMode = 'U';						// 실행 모드를 사용자 모드로 변경
			setVisible(false);							// 메인 패널 감추기
			KioskFrame.userModePanel.setVisible(true);	// 사용자 모드 패널 보여주기
		}
		
	}
	
}
