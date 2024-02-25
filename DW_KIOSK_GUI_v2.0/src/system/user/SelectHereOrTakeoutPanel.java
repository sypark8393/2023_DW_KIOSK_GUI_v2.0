package system.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import system.custom.CustomButton;
import system.custom.CustomLabel;

public class SelectHereOrTakeoutPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4753187998356491416L; // 버전 ID

	private String backgroundImageSource = "src/img/background.png"; 	// 배경 이미지 경로
	private String hereIconSource = "src/img/icon_here.png";			// 매장 아이콘 경로
	private String takeoutIconSource = "src/img/icon_takeout.png";		// 포장 아이콘 경로
	
	private CustomLabel guideMessageLabel;			// 안내 메시지 레이블
	private CustomButton hereButton, takeoutButton;	// 매장, 포장 버튼
	
	/* 생성자: SelectHereOrTakeoutPanel
	 * 파라미터: 없음
	 * 기능 설명: 매장 이용 방법(매장/포장)을 선택하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public SelectHereOrTakeoutPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("매장 이용 방법을 선택해주세요.");	// 텍스트를 가지는 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);				// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);									// 패널에 안내 메시지 레이블 추가
		
		// 매장 버튼
		String hereButtonText = "<html><div style=\"text-align:center\">매장<br><font size=6>For Here</font></div></html>";	// 매장 버튼에 들어갈 텍스트
		hereButton = new CustomButton(hereButtonText, new ImageIcon(hereIconSource));	// 텍스트와 이미지 아이콘을 가지는 버튼 생성
		hereButton.applySelectOneDesign(120, 360);										// 생성된 버튼에 2개 이상의 선택지 중 하나를 선택해야 하는 버튼을 의미하는 디자인 적용
		hereButton.addActionListener(new HereOrTakeoutListener());						// 매장 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(hereButton);															// 패널에 매장 버튼 추가
		
		// 포장 버튼
		String takeoutButtonText = "<html><div style=\"text-align:center\">포장<br><font size=6>For Take-out</font></div></html>";	// 포장 버튼에 들어갈 텍스트
		takeoutButton = new CustomButton(takeoutButtonText, new ImageIcon(takeoutIconSource));	// 텍스트와 이미지 아이콘을 가지는 버튼 생성
		takeoutButton.applySelectOneDesign(380, 360);											// 생성된 버튼에 2개 이상의 선택지 중 하나를 선택해야 하는 버튼을 의미하는 디자인 적용
		takeoutButton.addActionListener(new HereOrTakeoutListener());							// 포장 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(takeoutButton);																// 패널에 포장 버튼 추가
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
	
	/* 클래스명: HereOrTakeoutListener
	 * 설명: 매장/포장 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class HereOrTakeoutListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 매장/포장 버튼 클릭 이벤트 발생 시 매장 이용 방법 정보를 저장한 후, 메뉴 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			// 매장 버튼이 클릭된 경우
			if(button == hereButton) {
				UserModePanel.salesTotal.setOrderType("매장");		// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 주문 유형 정보로 "매장" 저장
				
			} else { // 포장 버튼이 클릭된 경우
				UserModePanel.salesTotal.setOrderType("포장");		// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 주문 유형 정보로 "포장" 저장
			}
			
			System.out.println("=======================================");
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(매장 이용 방법 선택 패널) :: 메뉴 패널로 넘어갑니다.");
			
			setVisible(false);							// 매장 이용 방법 선택 패널 감추기
			UserModePanel.menuPanel.setVisible(true);	// 메뉴 패널 보여주기
			UserModePanel.menuPanel.initialize();		// 메뉴 패널 초기화 메소드 호출
		}
		
	}
}
