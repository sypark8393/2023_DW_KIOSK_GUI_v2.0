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

public class SelectUsageOfPointOrCouponPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2223110733953704922L;		// 버전 ID

	private String backgroundImageSource = "src/img/background.png";		// 배경 이미지 경로
	private String pointIconSource = "src/img/icon_point.png";				// 포인트 아이콘 경로
	private String couponIconSource = "src/img/icon_coupon.png";			// 쿠폰 아이콘 경로
	
	private CustomLabel guideMessageLabel;				// 안내 메시지 레이블
	private CustomButton pointButton, couponButton;		// 포인트, 쿠폰 버튼
	private CustomButton cancelButton;					// 취소 버튼
	private CustomButton notUsePointAndCouponButton;	// 포인트/쿠폰 미사용 버튼
	
	/* 생성자: SelectUsageOfPointOrCouponPanel
	 * 파라미터: 없음
	 * 기능 설명: 포인트/쿠폰 사용 여부를 선택하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public SelectUsageOfPointOrCouponPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("포인트/쿠폰 사용 여부를 선택해주세요.");		// 텍스트를 가지는 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);						// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);											// 패널에 안내 메시지 레이블 추가
		
		// 포인트 버튼
		String pointButtonText = "<html><div style=\"text-align:center\">포인트<br><font size=6>Point</font></div></html>";	// 포인트 버튼에 들어갈 텍스트
		pointButton = new CustomButton(pointButtonText, new ImageIcon(pointIconSource));	// 텍스트와 이미지 아이콘을 가지는 버튼 생성
		pointButton.addActionListener(new PointOrCouponListener());							// 포인트 버튼 클릭 이벤트 처리를 위한 리스너 등록
		pointButton.applySelectOneDesign(120, 360);											// 생성된 버튼에 2개 이상의 선택지 중 하나를 선택해야 하는 버튼을 의미하는 디자인 적용
		this.add(pointButton);																// 패널에 포인트 버튼 추가
		
		// 쿠폰 버튼
		String couponButtonText = "<html><div style=\"text-align:center\">쿠폰<br><font size=6>Coupon</font></div></html>";	// 쿠폰 버튼에 들어갈 텍스트
		couponButton = new CustomButton(couponButtonText, new ImageIcon(couponIconSource));	// 텍스트와 이미지 아이콘을 가지는 버튼 생성
		couponButton.applySelectOneDesign(380, 360);										// 생성된 버튼에 2개 이상의 선택지 중 하나를 선택해야 하는 버튼을 의미하는 디자인 적용
		couponButton.addActionListener(new PointOrCouponListener());						// 포인트 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(couponButton);																// 패널에 쿠폰 버튼 추가
		
		// 취소 버튼
		cancelButton = new CustomButton("취소");					// 텍스트를 가지는 버튼 생성
		cancelButton.applyCancelDesign(40, 900);				// 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());	// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(cancelButton);									// 패널에 취소 버튼 추가
		
		// 포인트/쿠폰 미사용 버튼
		notUsePointAndCouponButton = new CustomButton("미사용");								// 텍스트를 가지는 버튼 생성
		notUsePointAndCouponButton.applyNotUsePointAndCouponDesign(520, 900);				// 포인트/쿠폰 미사용 버튼을 의미하는 디자인 적용
		notUsePointAndCouponButton.addActionListener(new NotUsePointAndCouponListener());	// 포인트/쿠폰 미사용 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(notUsePointAndCouponButton);												// 패널에 미사용 버튼 추가
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
	
	
	/* 클래스명: PointOrCouponListener
	 * 설명: 포인트/쿠폰 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class PointOrCouponListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 포인트/쿠폰 버튼 클릭 이벤트 발생 시 포인트/쿠폰 사용을 위한 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트/쿠폰 사용 여부 선택 패널) :: 포인트/쿠폰 사용 패널로 넘어갑니다.");
			
			setVisible(false);										// 포인트/쿠폰 사용 여부 선택 패널 감추기
			UserModePanel.usePointOrCouponPanel.setVisible(true);	// 포인트/쿠폰 사용 패널 보여주기
			
			if(button == pointButton) {									// 포인트 버튼이 클릭된 경우
				UserModePanel.salesTotal.setPayMethod("포인트");			// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 결제 수단 정보로 "포인트" 저장
				UserModePanel.usePointOrCouponPanel.initialize('P');	// 포인트/쿠폰 사용 패널 초기화 메소드 호출, 진행 유형은 P(포인트)로 전달
				
			} else {													// 쿠폰 버튼이 클릭된 경우
				UserModePanel.salesTotal.setPayMethod("쿠폰");			// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 결제 수단 정보로 "포인트" 저장
				UserModePanel.usePointOrCouponPanel.initialize('C');	// 포인트/쿠폰 사용 패널 초기화 메소드 호출, 진행 유형은 C(쿠폰)로 전달
			}
			
		}
		
	}

	/* 클래스명: NotUsePointAndCouponListener
	 * 설명: 포인트/쿠폰 미사용 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NotUsePointAndCouponListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 포인트/쿠폰 미사용 버튼 클릭 이벤트 발생 시 주문 세부 내역 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트/쿠폰 사용 여부 선택 패널) :: 주문 세부 내역 패널로 넘어갑니다.");
			
			UserModePanel.salesTotal.setDiscountId(" ");		// 할인 ID 정보 초기화 (쿠폰/포인트 사용을 취소한 경우 남아 있는 정보를 지우기 위한 목적)
			
			setVisible(false);									// 포인트/쿠폰 사용 여부 선택 패널 감추기
			UserModePanel.orderDetailsPanel.setVisible(true);	// 주문 세부 내역 패널 보여주기
			UserModePanel.orderDetailsPanel.initialize(0);		// 주문 세부 내역 패널 초기화 메소드 호출
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
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트/쿠폰 사용 여부 선택 패널) :: 메뉴 패널로 돌아갑니다.");
			
			setVisible(false);							// 포인트/쿠폰 사용 여부 선택 패널 감추기
			UserModePanel.menuPanel.setVisible(true);	// 메뉴 패널 보여주기
		}
		
	}
	
}
                                                                                              