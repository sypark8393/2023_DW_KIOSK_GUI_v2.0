package system.user;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.custom.CustomButton;
import system.custom.CustomColor;
import system.custom.CustomLabel;
import system.data.model.CartedMenu;
import system.user.process.UpdateDataProcess;

public class OrderDetailsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5548526328960160575L;		// 버전 ID

	private String backgroundImageSource = "src/img/background.png"; 		// 배경 이미지 경로
	
	private int discountAmt;					// 할인 금액
	
	private int cartViewPointer;				// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스
	
	private CustomLabel guideMessageLabel;		// 안내 메시지 레이블
	private JPanel headerPanel;					// 헤더 패널
	private CustomLabel headerMenuNameLabel;	// 헤더의 메뉴명 레이블
	private CustomLabel headerQuantityLabel;	// 헤더의 수량 레이블
	private CustomLabel headerTotalPriceLabel;	// 헤더의 금액 레이블
	
	private JPanel cartViewPanel;									// 주문 목록 view 패널
	private CustomLabel[] menuNameLabels = new CustomLabel[10];		// 메뉴명 레이블
	private CustomLabel[] quantityLabels = new CustomLabel[10];		// 수량 레이블
	private CustomLabel[] totalPriceLabels = new CustomLabel[10];	// 금액 레이블
	private CustomButton scrollUpButton, scrollDownButton;			// 주문 목록 view 스크롤 이동 버튼
	
	private JPanel resultPanel;						// 결과 패널
	private CustomLabel resultTotalLabel;			// 합계 레이블
	private CustomLabel resultTotalCountLabel;		// 합계 수량 레이블
	private CustomLabel resultTotalAmtLabel;		// 합계 금액 레이블
	private CustomLabel resultDiscountLabel;		// 할인 레이블
	private CustomLabel resultDiscountAmtLabel;		// 할인 금액 레이블 
	private CustomLabel resultRealTotalLabel;		// 최종 합계 레이블
	private CustomLabel resultRealTotalAmtLabel;	// 최종 합계 금액 레이블
	private CustomButton cancelButton;				// 취소 버튼
	private CustomButton payButton;					// 결제 버튼
	
	/* 생성자: OrderDetailsPanel
	 * 파라미터: 없음
	 * 기능 설명: 주문 세부 내역을 확인하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public OrderDetailsPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("주문 세부 내역을 확인해주세요");	// 텍스트를 가지는 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);				// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);									// 패널에 안내 메시지 레이블 추가
		
		// 헤더 패널
		headerPanel = new JPanel();								// 헤더 패널 생성
		headerPanel.setBounds(60, 200, 600, 40);				// 헤더 패널을 (60, 200)에 위치시키고 크기는 600px * 40px로 설정
		headerPanel.setBackground(CustomColor.KIOSK_YELLOW);	// 헤더 패널의 배경색을 노란색으로 지정
		headerPanel.setLayout(null);							// 헤더 패널의 레이아웃 매니저 제거
		this.add(headerPanel);									// 패널에 헤더 패널 추가
		
		// 헤더 - 메뉴명
		headerMenuNameLabel = new CustomLabel("메뉴", JLabel.CENTER);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		headerMenuNameLabel.changeFontToHighLight(22);					// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		headerMenuNameLabel.setBounds(10, 0, 360, 40);					// 헤더의 메뉴명 레이블을 (10, 0)에 위치시키고 크기를 360px * 40px로 설정
		headerPanel.add(headerMenuNameLabel);							// 헤더 패널에 헤더의 메뉴명 레이블 추가
		
		// 헤더 - 수량
		headerQuantityLabel = new CustomLabel("수량", JLabel.CENTER);									// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		headerQuantityLabel.changeFontToHighLight(22);												// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		headerQuantityLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE));	// 헤더의 수량 레이블의 좌, 우 테두리를 흰색의 실선으로 설정
		headerQuantityLabel.setBounds(370, 0, 90, 40);												// 헤더의 수량 레이블을 (370, 0)에 위치시키고 크기를 90px * 40px로 설정
		headerPanel.add(headerQuantityLabel);														// 헤더 패널에 헤더의 수량 레이블 추가
		
		// 헤더 - 금액
		headerTotalPriceLabel = new CustomLabel("금액", JLabel.CENTER);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		headerTotalPriceLabel.changeFontToHighLight(22);					// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		headerTotalPriceLabel.setBounds(460, 0, 130, 40);					// 헤더의 메뉴명 레이블을 (460, 0)에 위치시키고 크기를 130px * 40px로 설정
		headerPanel.add(headerTotalPriceLabel);								// 헤더 패널에 헤더의 금액 레이블 추가
		
		// 주문 목록 view 패널
		cartViewPanel = new JPanel();				// 주문 목록 view 패널 생성
		cartViewPanel.setBounds(60, 240, 600, 400);	// 주문 목록 view 패널을 (60, 240)에 위치시키고 크기는 600px * 400px로 설정
		cartViewPanel.setBackground(Color.white);	// 주문 목록 view 패널의 배경색을 흰색으로 지정
		cartViewPanel.setLayout(null);				// 주문 목록 view 패널의 레이아웃 매니저 제거
		this.add(cartViewPanel);					// 패널에 주문 목록 view 패널 추가
				
		// 주문 목록 view에 들어갈 아이템
		for(int i=0; i<10; i++) {
			// 메뉴명 레이블
			menuNameLabels[i] = new CustomLabel("");							// 텍스트를 가지는 레이블 생성
			menuNameLabels[i].applyMenuNameInOrderDetailsDesign(10, 40 * i);	// OrderDetailsPanel에 있는 메뉴명 레이블을 의미하는 디자인 적용
			cartViewPanel.add(menuNameLabels[i]);								// 주문 목록 view 패널에 메뉴명 레이블 추가
			
			// 수량 레이블
			quantityLabels[i] = new CustomLabel("");							// 텍스트를 가지는 레이블 생성
			quantityLabels[i].applyQuantityInOrderDetailsDesign(370, 40 * i);	// OrderDetailsPanel에 있는 수량 레이블을 의미하는 디자인 적용
			cartViewPanel.add(quantityLabels[i]);								// 주문 목록 view 패널에 수량 레이블 추가
			
			// 금액 레이블
			totalPriceLabels[i] = new CustomLabel("", JLabel.RIGHT, 17);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
			totalPriceLabels[i].applyTotalInOrderDetailsDesign(460, 40 * i);	// OrderDetailsPanel에 있는 금액 레이블을 의미하는 디자인 적용
			cartViewPanel.add(totalPriceLabels[i]);								// 주문 목록 view 패널에 금액 레이블 추가
		}
		
		// 주문 목록 view 스크롤 이동 버튼
		// - 위로 이동
		scrollUpButton = new CustomButton("▲");									// 텍스트를 가지는 버튼 생성
		scrollUpButton.applyScrollInOrderDetailsDesign(280, 650);				// OrderDetails 패널에 있는 주문 목록 view 스크롤 이동 버튼을 의미하는 디자인 적용
		scrollUpButton.addActionListener(new MoveCartViewScrollListener());		// 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(scrollUpButton);												// 주문 목록 view를 위로 이동하는 스크롤 버튼 추가
		
		// - 아래 이동
		scrollDownButton = new CustomButton("▼");								// 텍스트를 가지는 버튼 생성
		scrollDownButton.applyScrollInOrderDetailsDesign(400, 650);				// OrderDetails 패널에 있는 주문 목록 view 스크롤 이동 버튼을 의미하는 디자인 적용
		scrollDownButton.addActionListener(new MoveCartViewScrollListener());	// 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(scrollDownButton);												// 주문 목록 view를 아래로 이동하는 스크롤 버튼 추가
		
		// 결과 패널
		resultPanel = new JPanel();					// 결과 패널 생성
		resultPanel.setBounds(60, 700, 600, 120);	// 결과 패널을 (60, 700)에 위치시키고 크기는 600px * 120px로 설정
		resultPanel.setBackground(Color.white);		// 결과 패널의 배경색을 흰색으로 지정
		resultPanel.setLayout(null);				// 결과 패널의 레이아웃 메니저 제거
		this.add(resultPanel);						// 패널에 결과 패널 추가
				
		// 합계 레이블
		resultTotalLabel = new CustomLabel("합계");		// 텍스트를 가지는 레이블 생성
		resultTotalLabel.changeFontToHighLight(22);		// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		resultTotalLabel.setBounds(10, 0, 360, 40);		// 합계 레이블을 (10, 0)에 위치시키고 크기는 360px * 40px로 설정
		resultPanel.add(resultTotalLabel);				// 결과 패널에 합계 레이블 추가
		
		// 합계 수량 레이블
		resultTotalCountLabel = new CustomLabel("", JLabel.CENTER);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		resultTotalCountLabel.changeFontToHighLight(22);				// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		resultTotalCountLabel.setBounds(370, 0, 90, 40);				// 합계 수량 레이블을 (370, 0)에 위치시키고 크기는 90px * 40px로 설정
		resultPanel.add(resultTotalCountLabel);							// 결과 패널에 합계 수량 레이블 추가
		
		// 합계 금액 레이블
		resultTotalAmtLabel = new CustomLabel("", JLabel.RIGHT);	// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		resultTotalAmtLabel.changeFontToHighLight(22);				// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		resultTotalAmtLabel.setBounds(460, 0, 130, 40);				// 합계 금액 레이블을 (460, 0)에 위치시키고 크기는 130px * 40px로 설정
		resultPanel.add(resultTotalAmtLabel);						// 결과 패널에 합계 금액 레이블 추가
		
		// 할인 레이블
		resultDiscountLabel = new CustomLabel("할인");		// 텍스트를 가지는 레이블 생성
		resultDiscountLabel.changeFontToHighLight(22);		// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 22로 지정
		resultDiscountLabel.setBounds(10, 40, 450, 40);		// 할인 레이블을 (10, 460)에 위치시키고 크기는 450px * 40px로 설정
		resultPanel.add(resultDiscountLabel);				// 결과 패널에 할인 레이블 추가
		
		// 할인 금액 레이블
		resultDiscountAmtLabel = new CustomLabel("", JLabel.RIGHT);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		resultDiscountAmtLabel.changeFontToHighLight(22);				// 레이블의 폰트를 강조용 폰트로 변경하고폰트 크기를 22로 지정
		resultDiscountAmtLabel.setBounds(460, 40, 130, 40);				// 할인 금액 레이블을 (460, 40)에 위치시키고 크기는 130px * 40px로 설정
		resultPanel.add(resultDiscountAmtLabel);						// 결과 패널에 할인 금액 레이블 추가
		
		// 최종 합계 레이블
		resultRealTotalLabel = new CustomLabel("총 결제 금액");														// 텍스트를 가지는 레이블 생성
		resultRealTotalLabel.changeFontToHighLight(22);																// 레이블의 폰트를 강조용 폰트로 변경하고폰트 크기를 22로 지정
		resultRealTotalLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, CustomColor.KIOSK_GRAY_120));	// 레이블의 상단 테두리를 KIOSK_GRAY_120의 실선으로 설정
		resultRealTotalLabel.setBounds(10, 80, 450, 40);															// 최종 합계 레이블을 (10, 80)에 위치시키고 450px * 40px로 설정
		resultPanel.add(resultRealTotalLabel);																		// 결과 패널에 최종 합계 레이블 추가
		
		// 최종 합계 금액 레이블
		resultRealTotalAmtLabel = new CustomLabel("", JLabel.RIGHT);													// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		resultRealTotalAmtLabel.changeFontToHighLight(25);																// 레이블의 폰트를 강조용 폰트로 변경하고폰트 크기를 25로 지정
		resultRealTotalAmtLabel.setForeground(CustomColor.KIOSK_RED);													// 전경색을 KIOSK_RED로 지정
		resultRealTotalAmtLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, CustomColor.KIOSK_GRAY_120));		// 레이블의 상단 테두리를 KIOSK_GRAY_120의 실선으로 설정
		resultRealTotalAmtLabel.setBounds(460, 80, 130, 40);															// 최종 합계 레이블을 (460, 80)에 위치시키고 130px * 40px로 설정
		resultPanel.add(resultRealTotalAmtLabel);																		// 결과 패널에 최종 합계 금액 레이블 추가
		
		// 취소 버튼
		cancelButton = new CustomButton("취소");					// 텍스트를 가지는 버튼 생성
		cancelButton.applyCancelDesign(40, 900);				// 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());	// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(cancelButton);									// 패널에 취소 버튼 추가
		
		// 결제 버튼
		payButton = new CustomButton("결제");					// 텍스트를 가지는 버튼 생성
		payButton.applyPayDesign(520, 900);					// 결제 버튼을 의미하는 디자인 적용
		payButton.addActionListener(new PayListener());		// 결제 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(payButton);								// 패널에 결제 버튼 추가
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
	 * 파라미터: int discountAmt (할인 금액: 사용할 포인트 혹은 쿠폰의 금액)
	 * 반환값: 없음
	 * 기능 설명: 주문 세부 내역 패널에서 사용하는 변수와 구성 요소의 내용 및 속성을 초기화한다.
	 */
	public void initialize(int discountAmt) {
		this.discountAmt = discountAmt;		// 할인 금액 저장
		cartViewPointer = 0;				// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스를 0으로 초기화
		
		updateCartView();		// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		
		resultTotalCountLabel.setText(CartedMenu.getTOTAL_QUANTITY() + "");				// 합계 수량 레이블 텍스트 변경
		resultTotalAmtLabel.setText(String.format("%,d", CartedMenu.getTOTAL_AMT()));	// 합계 금액 레이블 텍스트 변경
		
		if(discountAmt != 0) {														// 할인 금액이 0원이 아닌 경우
			resultDiscountAmtLabel.setText(String.format("-%,d", discountAmt));		// 할인 금액 레이블 텍스트 변경
			
		} else {									// 할인 금액이 0원이 아닌 경우
			resultDiscountAmtLabel.setText("");		// 할인 금액 레이블 텍스트를 빈 문자열로 변경
		}
		
		resultRealTotalAmtLabel.setText(String.format("%,d원", CartedMenu.getTOTAL_AMT() - discountAmt));	// 최종 합계 금액 레이블 텍스트 변경
	}
	
	/* 메소드명: updateCartView
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 주문 목록 view와 관련된 컴포넌트의 속성(내용, 표시 여부, 디자인 등)을 적절하게 변경한다.
	 */
	private void updateCartView() {
		for(int i=0; i<10; i++) {
			if(cartViewPointer + i < UserModePanel.cart.size()) {	// 주문 목록 view에서 보여줄 아이템이 있는 경우
				// 메뉴명 레이블
				menuNameLabels[i].setText(UserModePanel.cart.get(cartViewPointer + i).getMenuName());	// 메뉴명 레이블 텍스트 변경
				menuNameLabels[i].setVisible(true);														// 메뉴명 레이블 보여주기
				
				// 수량 레이블
				quantityLabels[i].setText(UserModePanel.cart.get(cartViewPointer + i).getQuantity() + "");	// 수량 레이블 텍스트 변경
				quantityLabels[i].setVisible(true);															// 수량 레이블 보여주기
				
				// 금액 레이블
				totalPriceLabels[i].setText(String.format("%,d원", UserModePanel.cart.get(cartViewPointer + i).getTotalPrice()));	// 금액 레이블 텍스트 변경
				totalPriceLabels[i].setVisible(true);																				// 금액 레이블 보여주기

			} else {											// 주문 목록 view에서 보여줄 아이템이 없는 경우
				menuNameLabels[i].setVisible(false);			// 메뉴명 레이블 감추기
				quantityLabels[i].setVisible(false);			// 수량 레이블 감추기
				totalPriceLabels[i].setVisible(false);			// 금액 레이블 감추기
			}
			
		}
		
		// 주문 목록 view 스크롤 이동 버튼
		// - 위로 이동
		if(cartViewPointer == 0) {				// 주문 목록 view에서 보여지는 첫번째 아이템 이전에 아이템이 없는 경우
			scrollUpButton.setEnabled(false);	// 주문 목록 view 스크롤 패널에 위로 이동하는 스크롤 버튼 비활성화
			
		} else {								// 주문 목록 view에서 보여지는 첫번째 아이템 이전에도 아이템이 더 있는 경우
			scrollUpButton.setEnabled(true);	// 주문 목록 view 스크롤 패널에 위로 이동하는 스크롤 버튼 활성화
		}
		
		// - 아래로 이동
		if(cartViewPointer + 10 < UserModePanel.cart.size()) {	// 주문 목록 view에서 보여지는 마지막 아이템 이후에도 아이템이 더 있는 경우
			scrollDownButton.setEnabled(true);										// 주문 목록 view 스크롤 패널에 아래로 이동하는 스크롤 버튼 비활성화
		
		} else {								// 주문 목록 view에서 보여지는 마지막 아이템 이후에 아이템이 없는 경우
			scrollDownButton.setEnabled(false);	// 주문 목록 view 스크롤 패널에 아래로 이동하는 스크롤 버튼 활성화
		}
	}
	
	/* 클래스명: MoveCartViewScrollListener
	 * 설명: 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class MoveCartViewScrollListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 발생 시 이동된 위치에 맞는 주문 목록을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			if(button == scrollUpButton) {	// 위로 이동하는 스크롤 버튼 클릭 시
				cartViewPointer--;			// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스 1 감소
				
			} else {					// 아래로 이동하는 스크롤 버튼 클릭 시
				cartViewPointer++;		// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스 1 증가
			}
			
			updateCartView();	// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: PayListener
	 * 설명: 결제 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class PayListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 결제 버튼 클릭 이벤트 발생 시 결제 수단 선택 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(CartedMenu.getTOTAL_AMT() - discountAmt != 0) {	// 할인 금액 적용 후 결제할 금액이 남아 있는 경우
				System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(주문 세부 내역 패널) :: 결제 수단 선택 패널로 넘어갑니다.");
				
				UserModePanel.salesTotal.setTotalAmt(CartedMenu.getTOTAL_AMT() - discountAmt);	// 할인 금액이 적용된 후의 총 결제 금액 저장
				
				setVisible(false);										// 주문 세부 내역 패널 감추기
				UserModePanel.selectPayMethodPanel.setVisible(true);	// 결제 수단 선택 패널 보여주기
			
			} else {	// 총 결제 금액이 0원이면 데이터 처리 프로세스 진행
				System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(주문 세부 내역 패널) :: 데이터 처리 프로세스로 넘어갑니다.");
				
				UserModePanel.salesTotal.setTotalAmt(0);	// 결제 금액을 0원으로 저장
				
				// 거래 일시
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");	// 날짜 및 시간을 특정 문자열 포맷으로 변경하기 위한 객체
				String transDt = dateFormat.format(new Date());					// 현재 날짜 및 시간을 특정 문자열 포맷으로 변경하여 저장
				UserModePanel.salesTotal.setTransDt(transDt);	// 거래 일시 저장
				
				setVisible(false);				// 주문 세부 내역 패널 감추기
				UpdateDataProcess.updateData();	// 데이터 처리 프로세스 호출
			}

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
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(주문 세부 내역 패널) :: 메뉴 패널로 돌아갑니다.");
			
			setVisible(false);							// 주문 세부 내역 패널 감추기
			UserModePanel.menuPanel.setVisible(true);	// 메뉴 패널 보여주기
		}
		
	}
}
