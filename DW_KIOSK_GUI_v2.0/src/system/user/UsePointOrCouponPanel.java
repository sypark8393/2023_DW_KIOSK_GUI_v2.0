package system.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import system.custom.CustomButton;
import system.custom.CustomLabel;
import system.data.model.Member;
import system.user.dialog.CouponUsagePromptDialog;
import system.user.dialog.PointAmtPromptDialog;
import system.data.model.Coupon;

public class UsePointOrCouponPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336615659993307711L;		// 버전 ID
	
	private String backgroundImageSource = "src/img/background.png";		// 배경 이미지 경로
	
	private char type;							// 진행 유형 (P: 포인트, C: 쿠폰)
	private String inputIdExampleLabelText;		// ID 입력 예시 레이블 텍스트
	private int defaultValueLength;				// 기본값 길이
	private int idLength;						// ID 길이

	private CustomLabel guideMessageLabel;									// 안내 메시지 레이블
	private CustomLabel inputIdLabel;										// ID 입력 레이블
	private CustomLabel inputIdExampleLabel;								// ID 입력 예시 레이블
	private CustomButton[] numberPadNumberButton = new CustomButton[10];	// 숫자 키패드의 숫자 버튼
	private CustomButton numberPadEraseButton;								// 숫자 키패드의 지우기 버튼
	private CustomButton numberPadEnterButton;								// 숫자 키패드의 입력 버튼
	private CustomLabel invalidReasonLabel;									// 사용 불가 이유 표시 레이블
	private CustomButton cancelButton;										// 취소 버튼

	/* 생성자: UsePointOrCouponPanel
	 * 파라미터: 없음
	 * 기능 설명: 포인트/쿠폰 사용 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public UsePointOrCouponPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel();				// 내용을 가지지 않는 빈 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);	// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);						// 패널에 안내 메시지 레이블 추가
		
		// ID 입력 레이블
		inputIdLabel = new CustomLabel();			// 내용을 가지지 않는 빈 레이블 생성
		inputIdLabel.applyInputIdDesign(140, 220);	// ID 입력을 위한 레이블을 의미하는 디자인 적용
		this.add(inputIdLabel);						// 패널에 ID 입력 레이블 추가
		
		// ID 입력 예시 레이블
		inputIdExampleLabel = new CustomLabel();					// 내용을 가지지 않는 빈 레이블 생성
		inputIdExampleLabel.applyInputIdExampleDesign(240, 300);	// ID 입력 예시 레이블을 의미하는 디자인 적용
		this.add(inputIdExampleLabel);								// 패널에 ID 입력 예시 레이블 추가
		
		// 숫자 키패드의 숫자 버튼
		for(int i=0; i<10; i++) {														// 0부터 9까지
			numberPadNumberButton[i] = new CustomButton(i + "");						// 텍스트를 가지는 버튼 생성
			numberPadNumberButton[i].addActionListener(new NumberPadNumberListener());	// 숫자 키패드의 숫자 버튼 클릭 이벤트 처리를 위한 리스너 등록
			this.add(numberPadNumberButton[i]);											// 패널에 숫자 키패드의 숫자 버튼 추가
		}
		
		for(int i=1; i<10; i++) {				// 1부터 9까지
			int x = 135 + 150 * ((i-1) % 3);	// 135: 첫번째 버튼의 원점 x좌표, 150: 버튼 폭, (i-1) % (한 행에 들어가는 숫자 키패드의 버튼 개수): 행 기준 n번째
			int y = 350 + 110 * ((i-1) / 3);	// 350: 첫번째 버튼의 원점 y좌표, 110: 버튼 높이, (i-1) / (한 행에 들어가는 숫자 키패드의 버튼 개수): 열 기준 n번째
			
			numberPadNumberButton[i].applyNumberPadDesign(x, y);	// 숫자 키패드의 버튼을 의미하는 디자인 적용
		}
		numberPadNumberButton[0].applyNumberPadDesign(285, 680);	// 0번 버튼에 숫자 키패드의 버튼을 의미하는 디자인 적용

		// 숫자 키패드의 지우기 버튼
		numberPadEraseButton = new CustomButton("<");							// 텍스트를 가지는 버튼 생성
		numberPadEraseButton.applyNumberPadDesign(135, 680);					// 숫자 키패드의 버튼을 의미하는 디자인 적용
		numberPadEraseButton.addActionListener(new NumberPadEraseListener());	// 숫자 키패드의 지우기 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(numberPadEraseButton);											// 패널에 숫자 키패드의 지우기 버튼 추가
		
		// 숫자 키패드의 입력 버튼
		numberPadEnterButton = new CustomButton("입력");							// 텍스트를 가지는 버튼 생성
		numberPadEnterButton.applyNumberPadDesign(435, 680);					// 숫자 키패드의 버튼을 의미하는 디자인 적용
		numberPadEnterButton.addActionListener(new NumberPadEnterListener());	// 숫자 키패드의 입력 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(numberPadEnterButton);											// 패널에 숫자 키패드의 입력 버튼 추가
		
		// 사용 불가 이유 표시 레이블
		invalidReasonLabel = new CustomLabel("");				// 내용을 가지지 않는 빈 레이블 생성
		invalidReasonLabel.applyInvalidReasonDesign(135, 820);	// 사용 불가 이유 레이블을 의미하는 디자인 적용
		this.add(invalidReasonLabel);							// 패널에 사용 불가 이유 레이블 추가
		
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
	
	/* 메소드명: initialize
	 * 파라미터: char type (진행 유형, P(포인트)/C(쿠폰))
	 * 반환값: 없음
	 * 기능 설명: 파라미터로 받은 진행 유형에 따라 포인트/쿠폰 사용 패널에서 사용하는 변수와 구성 요소의 내용 및 속성을 초기화한다.
	 */
	public void initialize(char type) {
		this.type = type;	// 진행 유형

		if(type == 'P') {	// 포인트로 진행하는 경우
			guideMessageLabel.setText("포인트를 사용할 회원의 휴대전화 번호를 입력해주세요.");	// 안내 메세지 레이블 텍스트 변경
			
			inputIdExampleLabelText = "01012345678";	// ID 입력 예시 레이블 텍스트
			defaultValueLength = 3;						// 기본값(010) 길이
			idLength = 11;								// ID 길이
			
		} else {	// 쿠폰으로 진행하는 경우
			guideMessageLabel.setText("사용할 쿠폰 번호를 입력해주세요.");	// 안내 메세지 레이블 텍스트 변경
			
			inputIdExampleLabelText = "C01012345678001";	// ID 입력 예시 레이블 텍스트
			defaultValueLength = 4;							// 기본값(C010) 길이
			idLength = 15;									// ID 길이
		}

		// ID 입력 레이블
		inputIdLabel.setText(inputIdExampleLabelText.substring(0, defaultValueLength));		// ID 입력 레이블 텍스트 변경
		
		// ID 입력 예시 레이블
		inputIdExampleLabel.setText("예시) " + inputIdExampleLabelText);	// ID 입력 예시 레이블 텍스트 변경
		
		setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
		setPropertyOfNumberPadErase();		// 숫자 키패드의 지우기 버튼의 활성화 여부를 적절하게 변경
		setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
		
		// 사용 불가 이유 표시 레이블
		invalidReasonLabel.setText("");		// 사용 불가 이유 표시 레이블의 텍스트를 빈 문자열로 초기화
		
	}

	/* 메소드명: setPropertyOfNumberPadNumber
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadNumber() {
		int inputIdLabelLength = inputIdLabel.getText().length();	// ID 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputIdLabelLength == idLength) {					// ID 길이에 도달한 경우
			for(int i=0; i<10; i++) {								
				numberPadNumberButton[i].setEnabled(false);		// 숫자 키패드의 숫자 버튼 비활성화
			}
			
		} else {												// ID 길이에 도달하지 않은 경우
			for(int i=0; i<10; i++) {
				numberPadNumberButton[i].setEnabled(true);		// 숫자 키패드의 숫자 버튼 활성화
			}
			
		}
		
	}

	/* 메소드명: setPropertyOfNumberPadErase
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 지우기 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadErase() {
		int inputIdLabelLength = inputIdLabel.getText().length();	// ID 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputIdLabelLength == defaultValueLength) {		// 기본값 길이에 도달한 경우
			numberPadEraseButton.setEnabled(false);			// 숫자 키패드의 지우기 버튼 비활성화
			
		} else {											// 기본값 길이에 도달하지 않은 경우
			numberPadEraseButton.setEnabled(true);			// 숫자 키패드의 지우기 버튼 활성화
			
		}
		
	}
	
	/* 메소드명: setPropertyOfNumberPadEnter
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadEnter() {
		int inputIdLabelLength = inputIdLabel.getText().length();	// ID 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputIdLabelLength == idLength) {				// ID 길이에 도달한 경우
			numberPadEnterButton.setEnabled(true);			// 숫자 키패드의 입력 버튼 활성화
			
		} else {											// ID 길이에 도달하지 않은 경우
			numberPadEnterButton.setEnabled(false);			// 숫자 키패드의 입력 버튼 비활성화
			
		}
		
	}
	
	/* 메소드명: searchAvailablePointAmt
	 * 파라미터: String memberId (회원 ID)
	 * 반환값: int availablePointAmt (사용 가능한 포인트 금액)
	 * 기능 설명: 파라미터로 받은 회원 ID를 이용하여 회원 조회 후 사용 가능한 포인트 금액을 반환한다.
	 */
	private int searchAvailablePointAmt(String memberId) {
		BufferedReader in;	// 입력 스트림
		
		try {
			System.out.println("UsePointOrCouponPanel >> searchAvailablePointAmt 메소드에서 회원 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Member.FILE_NAME));		// 파일 내용을 읽어오기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("회원 파일이 존재하지 않습니다.");
			invalidReasonLabel.setText("회원 조회에 실패하였습니다.");
			return 0;
		}
		
		String s;						// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;					// 구분자를 기준으로 분리된 데이터가 저장될 배열
		int availablePointAmt = -9999;	// 사용 가능한 포인트 금액 (초기값: -9999)
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				if(tmp[Member.INDEX_MEMBER_ID].equals(memberId)) {					// 일치하는 회원 ID가 있는 경우
					System.out.println("회원 조회에 성공하였습니다.");
					availablePointAmt = Integer.parseInt(tmp[Member.INDEX_POINT]);	// 포인트 금액 가져오기
					
					break;
				}
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			
		} catch (IOException e) {	// 입출력 예외가 발생한 경우
			System.out.println("회원 조회에 실패하였습니다.");
			invalidReasonLabel.setText("회원 조회에 실패하였습니다.");
			return 0;
			
		}
		
		if(availablePointAmt == -9999) {	// 일치하는 회원이 없는 경우
			System.out.println("회원 정보를 찾을 수 없습니다.");
			invalidReasonLabel.setText("회원 정보를 찾을 수 없습니다.");
			return 0;
			
		} else if(availablePointAmt < 100) {	// 보유 포인트 금액이 100 미만인 경우
			System.out.println("포인트 금액이 부족합니다.(현재 보유 포인트: " + availablePointAmt + ")");
			String invalidReasonLabelText = "<html><div style=\"text-align:center\">포인트는 최소 100p부터 사용 가능합니다.<br>"
											+ "<font size=5>(현재 보유 포인트: " + availablePointAmt + "p)</font></div></html>";
			invalidReasonLabel.setText(invalidReasonLabelText);
			return 0;
			
		}
		
		return availablePointAmt;
	}
	
	/* 메소드명: searchAvailableCouponAmt
	 * 파라미터: String couponId (쿠폰 ID)
	 * 반환값: int availableCouponAmt (사용 가능한 쿠폰 금액)
	 * 기능 설명: 파라미터로 받은 쿠폰 ID를 이용하여 쿠폰 조회 후 사용 가능한 쿠폰 금액을 반환한다.
	 */
	private int searchAvailableCouponAmt(String couponId) {
		BufferedReader in;	// 입력 스트림
		
		try {
			System.out.println("UsePointOrCouponPanel >> searchAvailableCouponAmt 메소드에서 쿠폰 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Coupon.FILE_NAME));		// 파일 내용을 읽어오기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("쿠폰 파일이 존재하지 않습니다.");
			invalidReasonLabel.setText("쿠폰 조회에 실패하였습니다.");
			return 0;
		}
		
		String s;							// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;						// 구분자를 기준으로 분리된 데이터가 저장될 배열
		int availableCouponAmt = -9999;		// 사용 가능한 쿠폰 금액 (초기값: -9999)
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				if(tmp[Coupon.INDEX_COUPON_ID].equals(couponId)) {			// 일치하는 쿠폰 ID가 있는 경우 
					
					if(tmp[Coupon.INDEX_USED].equals("Y")) {					// 이미 사용된 쿠폰인 경우
						System.out.println("쿠폰 조회에 성공하였습니다. (사용 불가)");
						availableCouponAmt = -1;								// 쿠폰 금액을 -1로 설정
						
					} else {																// 사용된 적이 없는 쿠폰
						System.out.println("쿠폰 조회에 성공하였습니다. (사용 가능)");
						availableCouponAmt = Integer.parseInt(tmp[Coupon.INDEX_PRICE]);		// 쿠폰 금액 가져오기
					}
						
					break;
				}
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			
		} catch (IOException e) {	// 입출력 예외 발생 시
			System.out.println("쿠폰 조회에 실패하였습니다.");
			invalidReasonLabel.setText("쿠폰 조회에 실패하였습니다.");
			return 0;
			
		}

		if(availableCouponAmt == -9999) {	// 일치하는 쿠폰이 없는 경우
			System.out.println("쿠폰 정보를 찾을 수 없습니다.");
			invalidReasonLabel.setText("쿠폰 정보를 찾을 수 없습니다.");
			return 0;
			
		} else if(availableCouponAmt == -1) {	// 이미 사용된 쿠폰인 경우
			invalidReasonLabel.setText("이미 사용된 쿠폰입니다.");
			return 0;
			
		}
		
		return availableCouponAmt;
		
	}
	
	/* 클래스명: NumberPadNumberListener
	 * 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NumberPadNumberListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 발생 시 ID 길이에 도달하지 않은 경우에 한하여 ID 입력 레이블에 클릭된 버튼의 숫자를 입력한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			inputIdLabel.setText(inputIdLabel.getText() + button.getText());	// ID 입력 레이블에 클릭된 버튼의 숫자를 입력

			setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadErase();		// 숫자 키패드의 지우기 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
		}
		
	}
	
	/* 클래스명: NumberPadEraseListener
	 * 설명: 숫자 키패드의 지우기 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NumberPadEraseListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 숫자 키패드의 지우기 버튼 클릭 이벤트 발생 시 ID 입력 레이블에 입력된 마지막 숫자를 지운다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int inputIdLabelLength = inputIdLabel.getText().length();	// ID 입력 레이블에 입력되어 있는 텍스트의 길이
			
			inputIdLabel.setText(inputIdLabel.getText().substring(0, inputIdLabelLength-1));	// ID 입력 레이블에 입력된 마지막 숫자를 지움
			invalidReasonLabel.setText("");														// 사용 불가 이유 표시 레이블의 텍스트를 빈 문자열로 초기화

			setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadErase();		// 숫자 키패드의 지우기 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
		}
		
	}
	
	/* 클래스명: NumberPadEnterListener
	 * 설명: 숫자 키패드의 입력 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NumberPadEnterListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 숫자 키패드의 입력 버튼 클릭 이벤트 발생 시 진행 유형에 따라 포인트/쿠폰 금액을 조회한 후 사용자 입력/선택에 따라 다음 프로세스를 진행한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int discountAmt = 0;	// 사용할 포인트/쿠폰 금액
			
			String discountId = inputIdLabel.getText();				// 포인트/쿠폰 ID
			UserModePanel.salesTotal.setDiscountId(discountId);		// UserModePanel에서 선언 및 생성한 통합 매출 내역 객체의 할인 ID 정보로 회원 ID 혹은 쿠폰 ID 저장
			
			if(type == 'P') {													// 진행 유형이 포인트인 경우
				int availablePointAmt = searchAvailablePointAmt(discountId); 	// 사용 가능한 포인트 금액 가져오기
				
				if(availablePointAmt != 0) {														// 사용 가능한 포인트 금액이 0이 아니라면
					discountAmt = new PointAmtPromptDialog(availablePointAmt).getUsePointAmt();		// 포인트 사용 금액 입력을 위한 다이얼로그를 호출하고 입력한 포인트 금액을 받아옴
				}
				
			} else {															// 진행 유형이 쿠폰인 경우
				int availableCouponAmt = searchAvailableCouponAmt(discountId);	// 사용 가능한 쿠폰 금액 가져오기
																				
				if(availableCouponAmt != 0) {														// 사용 가능한 쿠폰 금액이 0이 아니라면
					discountAmt = new CouponUsagePromptDialog(availableCouponAmt).getUseCouponAmt();	// 쿠폰 사용 여부 선택을 위한 다이얼로그를 호출하고 쿠폰 금액 혹은 결제 금액을 받아옴
				}
			}
			
			if(discountAmt != 0) {	// 사용할 포인트/쿠폰 금액이 있는 경우
				System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트/쿠폰 사용 패널) :: 주문 세부 내역 패널로 넘어갑니다.");
				
				setVisible(false);											// 포인트/쿠폰 사용 패널 숨기기
				UserModePanel.orderDetailsPanel.setVisible(true);			// 주문 세부 내역 패널 보여주기
				UserModePanel.orderDetailsPanel.initialize(discountAmt);	// 주문 세부 내역 패널 초기화 메소드 호출
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
		 * 기능 설명: 취소 버튼 클릭 이벤트 발생 시 포인트/쿠폰 사용 여부 선택 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트/쿠폰 사용 패널) :: 포인트/쿠폰 사용 여부 선택 패널로 돌아갑니다.");
			
			setVisible(false);												// 포인트/쿠폰 사용 패널 감추기
			UserModePanel.selectUsageOfPointOrCouponPanel.setVisible(true);	// 포인트/쿠폰 사용 여부 선택 패널 보여주기
		}
		
	}
}
