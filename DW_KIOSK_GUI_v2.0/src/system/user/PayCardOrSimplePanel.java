package system.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.custom.CustomButton;
import system.custom.CustomColor;
import system.custom.CustomLabel;
import system.user.dialog.GoPayDialog;

public class PayCardOrSimplePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7213632467806506989L;	// 버전 ID
	
	private String backgroundImageSource = "src/img/background.png"; 	// 배경 이미지 경로
	private String cardImageSource = "src/img/img_card.png";			// 카드 결제 이미지 경로
	private String simpleImageSource = "src/img/img_simple.png";		// 간편 결제 이미지 경로
	
	private char type;	// 진행 유형 (C: 카드 결제, S: 간편 결제)
	
	private CustomLabel guideMessageLabel;		// 안내 메시지 레이블
	private CustomLabel payMethodLabel;			// 결제 방법 레이블
	private CustomLabel requestMessageLabel;	// 요청 메시지 레이블
	private CustomLabel totalAmtLabel;			// 결제 금액 레이블
	private CustomLabel guideImageLabel;		// 안내 이미지 레이블
	private CustomButton cancelButton;			// 취소 버튼
	private CustomButton requestPayButton;		// 승인 요청 버튼
	
	/* 생성자: PayCardOrSimplePanel
	 * 파라미터: 없음
	 * 기능 설명: 결제를 진행하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public PayCardOrSimplePanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("", JLabel.CENTER, 25);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);			// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);								// 패널에 안내 메시지 레이블 추가
		
		// 결제 방법 레이블
		payMethodLabel = new CustomLabel("", JLabel.CENTER, 18);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		payMethodLabel.setForeground(CustomColor.KIOSK_GRAY_120);	// 결제 방법 레이블의 전경색을 KIOSK_GRAY_120로 지정
		payMethodLabel.setBounds(300, 300, 120, 30);				// 결제 방법 레이블을 (300, 300)에 위치시키고 크기는 120px * 120px로 설정
		this.add(payMethodLabel);									// 패널에 결제 방법 레이블 추가
		
		// 요청 메시지 레이블
		requestMessageLabel = new CustomLabel("", JLabel.CENTER, 20);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		requestMessageLabel.setBounds(110, 330, 500, 40);				// 요청 메시지 레이블을 (110, 330)에 위치시키고 크기는 500px * 40px로 설정
		this.add(requestMessageLabel);									// 패널에 요청 메시지 레이블 추가
		
		// 결제 금액 레이블
		totalAmtLabel = new CustomLabel("", JLabel.CENTER);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		totalAmtLabel.changeFontToHighLight(30);				// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 30으로 지정
		totalAmtLabel.setBounds(120, 570, 480, 50);				// 결제 금액 레이블을 (120, 570)에 위치시키고 크기는 480px * 50px로 설정
		this.add(totalAmtLabel);								// 패널에 결제 금액 레이블 추가
		
		// 안내 이미지 레이블
		guideImageLabel = new CustomLabel();			// 내용을 가지지 않는 빈 레이블 생성
		guideImageLabel.setBounds(80, 280, 560, 360);	// 안내 이미지 레이블을 (80, 280)에 위치시키고 크기는 560px * 360px로 설정
		this.add(guideImageLabel);						// 안내 이미지 레이블 추가
		
		// 취소 버튼
		cancelButton = new CustomButton("취소");					// 텍스트를 가지는 버튼 생성
		cancelButton.applyCancelOnCenterDesign(120, 680);		// 화면 중앙에 위치한 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());	// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(cancelButton);									// 취소 버튼 추가
		
		// 승인 요청 버튼
		requestPayButton = new CustomButton("승인 요청");					// 텍스트를 가지는 버튼 생성
		requestPayButton.applyRequestPayDesign(360, 680);				// 승인 요청 버튼을 의미하는 디자인 적용
		requestPayButton.addActionListener(new RequestPayListener());	// 승인 요청 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(requestPayButton);										// 승인 요청 버튼 추가
		
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
	
	/* 메소드명: initialize
	 * 파라미터: char type (진행 유형, C(카드 결제)/S(간편 결제))
	 * 반환값: 없음
	 * 기능 설명: 파라미터로 받은 진행 유형에 따라 결제 요청 패널에서 사용하는 변수와 구성 요소의 내용 및 속성을 초기화한다.
	 */
	public void initialize(char type) {
		this.type = type;	// 진행 유형
		
		totalAmtLabel.setText(String.format("결제 금액: %,d원", UserModePanel.salesTotal.getTotalAmt()));	// 결제 금액 레이블
		
		if(type == 'C') {																// 카드 결제로 진행하는 경우
			guideMessageLabel.setText("다음 그림과 같이 카드 투입구에 카드를 넣어주세요.");			// 안내 메세지 레이블 텍스트 변경
			payMethodLabel.setText("카드 결제");											// 결제 방법 레이블 텍스트 변경
			requestMessageLabel.setText("카드를 넣은 후 화면의 승인 요청 버튼을 클릭해주세요.");		// 요청 메시지 레이블 텍스트 변경
			guideImageLabel.setIcon(new ImageIcon(cardImageSource));					// 안내 이미지 레이블 아이콘 변경
			
		} else {																			// 간편 결제로 진행하는 경우
			guideMessageLabel.setText("다음 그림과 같이 NFC 리더기에 휴대전화를 태그해주세요.");			// 안내 메세지 레이블 텍스트 변경
			payMethodLabel.setText("간편 결제");												// 결제 방법 레이블 텍스트 변경
			requestMessageLabel.setText("휴대전화를 태그한 후 화면의 승인 요청 버튼을 클릭해주세요.");		// 요청 메시지 레이블 텍스트 변경
			guideImageLabel.setIcon(new ImageIcon(simpleImageSource));						// 안내 이미지 레이블 아이콘 변경
			
		}
		
	}
	
	/* 클래스명: RequestPayListener
	 * 설명: 승인 요청 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class RequestPayListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 승인 요청 버튼 클릭 이벤트 발생 시 승인 진행중 메시지를 출력하는 다이얼로그를 호출한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// 거래 일시
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");	// 날짜 및 시간을 특정 문자열 포맷으로 변경하기 위한 객체
			String transDt = dateFormat.format(new Date());					// 현재 날짜 및 시간을 특정 문자열 포맷으로 변경하여 저장
			UserModePanel.salesTotal.setTransDt(transDt);					// 거래 일시 저장
			
			new GoPayDialog(type);			// 승인 진행중 다이얼로그 호출
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
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(결제 요청 패널) :: 메뉴 패널로 돌아갑니다.");
			
			setVisible(false);							// 결제 수단 선택 패널 감추기
			UserModePanel.menuPanel.setVisible(true);	// 메뉴 패널 보여주기
		}
		
	}
}
