package system.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.custom.CustomButton;
import system.custom.CustomLabel;

public class SelectPayMethodPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5582815836368990750L;	// 버전 ID
	
	private String backgroundImageSource = "src/img/background.png"; 	// 배경 이미지 경로
	private String cardIconSource = "src/img/icon_card.png";			// 신용카드 아이콘 경로
	
	private LinkedHashMap<String, String> simplePayMethods = new LinkedHashMap<String, String>() {	// {간편결제명 : 아이콘 경로}로 매핑되어 있는 간편 결제 목록
		/**
		 * 
		 */
		private static final long serialVersionUID = 6941767804455098889L;	// 버전 ID

		{
			put("카카오페이", "src/img/icon_kakaoPay.png");
			put("페이코", "src/img/icon_payco.png");
			put("네이버페이", "src/img/icon_naverPay.png");
			put("제로페이", "src/img/icon_zeroPay.png");
			put("삼성페이", "src/img/icon_samsungPay.png");
			put("페이북", "src/img/icon_paybooc.png");
		}
	};
	
	private CustomLabel guideMessageLabel;													// 안내 메시지 레이블
	private CustomLabel cardLabel;															// 카드 결제 레이블
	private CustomButton cardButton;														// 신용카드 버튼
	private CustomLabel simplePayLabel;														// 간편 결제 레이블
	private CustomButton[] simplePayButtons = new CustomButton[simplePayMethods.size()];	// 간편결제 버튼 배열
	private CustomButton cancelButton;														// 취소 버튼

	/* 생성자: SelectPayMethodPanel
	 * 파라미터: 없음
	 * 기능 설명: 결제 수단을 선택하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public SelectPayMethodPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("결제 수단을 선택해주세요.");		// 텍스트를 가지는 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);				// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);									// 패널에 안내 메시지 레이블 추가
		
		// 카드 결제 레이블
		cardLabel = new CustomLabel("카드 결제", JLabel.LEFT, 22);		// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		cardLabel.setBounds(80, 220, 100, 40);						// 카드 결제 레이블을 (80, 220)에 위치시키고 크기는 100px * 40px로 설정
		this.add(cardLabel);										// 패널에 카드 결제 레이블 추가
		
		// 신용카드 버튼
		cardButton = new CustomButton("신용카드", new ImageIcon(cardIconSource));	// 텍스트와 이미지 아이콘을 가지는 버튼 생성
		cardButton.applySelectPayMethodDesign(80, 260);							// 결제 수단 버튼을 의미하는 디자인 적용
		cardButton.addActionListener(new CardPayMethodListener());				// 카드 결제 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(cardButton);													// 패널에 신용카드 버튼 추가
		
		// 간편 결제 레이블
		simplePayLabel = new CustomLabel("간편 결제", JLabel.LEFT, 22);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		simplePayLabel.setBounds(80, 420, 100, 40);						// 카드 결제 레이블을 (80, 420)에 위치시키고 크기는 100px * 40px로 설정
		this.add(simplePayLabel);										// 패널에 카드 결제 레이블 추가

		// 간편 결제 버튼
		int i = 0;	// 인덱스 변수
		for(String key : simplePayMethods.keySet()) {	// 간편 결제 목록의 key에 순차적으로 접근하면서
			int x = 80 + 200 * (i % 3);					// 80: 첫번째 버튼의 원점 x좌표, 200: 버튼 폭(160) + 여백(40), i % (한 행에 들어가는 결제 수단 버튼의 버튼 개수): 행 기준 n번째
			int y = 460 + 140 * (i / 3);				// 460: 첫번째 버튼의 원점 y좌표, 140: 버튼 높이(120) + 여백(20), i / (한 행에 들어가는 결제 수단 버튼의 버튼 개수): 열 기준 n번째
			
			simplePayButtons[i] = new CustomButton(key, new ImageIcon(simplePayMethods.get(key)));	// 텍스트와 이미지를 가지는 버튼 생성
			simplePayButtons[i].applySelectPayMethodDesign(x, y);									// 결제 수단 버튼을 의미하는 디자인 적용
			simplePayButtons[i].addActionListener(new SimplePayMethodListener());					// 간편 결제 버튼 클릭 이벤트 처리를 위한 리스너 등록
			this.add(simplePayButtons[i]);															// 패널에 제공하고 있는 간편 결제 버튼 추가
		
			i++;	// 인덱스 1 증가
		}
		
		// 취소 버튼
		cancelButton = new CustomButton("취소");					// 텍스트를 가지는 버튼 생성
		cancelButton.applyCancelDesign(40, 900);				// 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());	// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(cancelButton);									// 패널에 취소 버튼 추가
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
	
	/* 클래스명: CardPayMethodListener
	 * 설명: 카드 결제 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class CardPayMethodListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 카드 결제 버튼 클릭 이벤트 발생 시 결제 요청 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(결제 수단 선택 패널) :: 결제 요청 패널로 넘어갑니다.");

			UserModePanel.salesTotal.setPayMethod("신용카드");			// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 결제 수단 정보로 "신용카드" 저장
			
			setVisible(false);										// 결제 수단 선택 패널 감추기
			UserModePanel.payCardOrSimplePanel.setVisible(true);	// 결제 요청 패널 보여주기
			UserModePanel.payCardOrSimplePanel.initialize('C');		// 결제 요청 패널의 초기화 메소드 호출
		}
		
	}
	
	/* 클래스명: SimplePayMethodListener
	 * 설명: 간편 결제 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class SimplePayMethodListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 간편 결제 버튼 클릭 이벤트 발생 시 결제 요청 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(결제 수단 선택 패널) :: 결제 요청 패널로 넘어갑니다.");


			UserModePanel.salesTotal.setPayMethod("간편결제");			// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 결제 수단 정보로 "간편결제" 저장
			
			setVisible(false);										// 결제 수단 선택 패널 감추기
			UserModePanel.payCardOrSimplePanel.setVisible(true);	// 결제 요청 패널 보여주기
			UserModePanel.payCardOrSimplePanel.initialize('S');		// 결제 요청 패널의 초기화 메소드 호출
		}
		
	}
	
	/* 클래스명: CancelListener
	 * 설명: 취소 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class CancelListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 취소 버튼 클릭 이벤트 발생 시 메뉴 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(결제 수단 선택 패널) :: 메뉴 패널로 돌아갑니다.");
			
			setVisible(false);							// 결제 수단 선택 패널 감추기
			UserModePanel.menuPanel.setVisible(true);	// 메뉴 패널 보여주기
		}
		
	}
}
