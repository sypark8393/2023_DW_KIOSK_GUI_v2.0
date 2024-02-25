package system.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ErrorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312347065145447224L;		// 버전 ID
	
	private String backgroundImageSource = "src/img/background_main_fail.png";	// 배경 이미지 경로
	
	/* 생성자: ErrorPanel
	 * 파라미터 없음
	 * 기능 설명: 에러가 발생한 경우의 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public ErrorPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
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
}
