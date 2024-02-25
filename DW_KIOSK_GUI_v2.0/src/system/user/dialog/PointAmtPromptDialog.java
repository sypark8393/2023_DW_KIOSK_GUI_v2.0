package system.user.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import system.custom.CustomButton;
import system.custom.CustomColor;
import system.custom.CustomLabel;
import system.data.model.CartedMenu;
import system.main.KioskFrame;

public class PointAmtPromptDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4532048461589440174L;		// 버전 ID
	
	private int availablePointAmt;			// 사용 가능한 포인트 금액
	private int usePointAmt = 0;			// 사용할 포인트 금액
	private boolean inputError = false;		// 입력값 예외 발생 여부 (true: 발생, false: 발생하지 않음)
	
	private JPanel backgroundPanel;											// 배경 패널
	private CustomLabel alertMessageLabel;									// 알림 메시지 레이블
	private CustomLabel infoMessageLabel;									// 정보 메시지 레이블
	private CustomLabel detailMessageLabel1, detailMessageLabel2;			// 상세 메시지 레이블
	private CustomLabel inputPointAmtLabel;									// 포인트 금액 입력 레이블
	private CustomButton[] numberPadNumberButton = new CustomButton[10];	// 숫자 키패드의 숫자 버튼
	private CustomButton numberPadResetButton;								// 숫자 키패드의 리셋 버튼
	private CustomButton numberPadEnterButton;								// 숫자 키패드의 입력 버튼
	private CustomButton cancelButton;										// 취소 버튼	
	
	/* 생성자: PointAmtPromptDialog
	 * 파라미터: int availablePointAmt(사용 가능한 포인트 금액)
	 * 기능 설명: 사용할 포인트 금액을 입력받는다.
	 */
	public PointAmtPromptDialog(int availablePointAmt) {
		super(KioskFrame.getInstance(), "", true);	// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트 금액 입력 다이얼로그) :: 사용할 포인트 금액을 입력받습니다.");
		
		// Dialog 속성
		this.setSize(520, 400);							// 다이얼로그 크기 (520px * 400px)
		this.setResizable(false);						// 다이얼로그 크기 변경 불가
		this.setUndecorated(true);						// 다이얼로그 타이틀바 제거
		this.setLocationRelativeTo(this.getParent());	// 다이얼로그를 부모 프레임의 중앙에 띄우기
		
		this.availablePointAmt = availablePointAmt;		// 사용 가능한 포인트 금액 저장
		
		// 배경 패널
		backgroundPanel = new JPanel();															// 배경 패널 생성
		backgroundPanel.setLayout(null);														// 배경 패널의 레이아웃 매니저 제거
		backgroundPanel.setBackground(CustomColor.KIOSK_GRAY_242);								// 베경 패널의 배경색을 KIOSK_GRAY_242로 지정
		backgroundPanel.setBorder(new MatteBorder(2, 2, 2, 2, CustomColor.KIOSK_GRAY_217));		// 베경 패널의 테두리를 2px 두께의 KIOSK_GRAY_217로 지정
		this.add(backgroundPanel);																// 다이얼로그에 배경 패널 추가
		
		// 알림 메시지 레이블
		alertMessageLabel = new CustomLabel("사용하실 포인트 금액을 입력해주세요.");	// 텍스트를 가지는 레이블 생성
		alertMessageLabel.applyAlertMessageDesign(2, 2);					// 알림 메시지 레이블을 의미하는 디자인 적용
		backgroundPanel.add(alertMessageLabel);								// 배경 패널에 알림 메시지 레이블 추가

		// 정보 메시지 레이블
		String infoMessageLabelText = "(현재 보유 포인트: " + String.format("%,dp", availablePointAmt)
										+ " / 결제 금액: " + String.format("%,d원)", CartedMenu.getTOTAL_AMT());	// 정보 메시지 레이블에 들어갈 텍스트
		infoMessageLabel = new CustomLabel(infoMessageLabelText, JLabel.CENTER, 17);							// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		infoMessageLabel.setBounds(20, 50, 480, 30);															// 정보 메시지 레이블을 (20, 50)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(infoMessageLabel);																	// 배경 패널에 정보 메시지 레이블 추가
		
		// 상세 메시지 레이블
		detailMessageLabel1 = new CustomLabel("- 포인트는 최소 100p 부터 최대 99,900p까지만 사용 가능합니다.", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel1.setBounds(20, 90, 480, 30);																	// 상세 메시지 레이블1을 (20, 90)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel1);																		// 배경 패널에 상세 메시지 레이블1 추가
		
		detailMessageLabel2 = new CustomLabel("- 포인트 사용을 취소하시려면 하단의 취소 버튼을 클릭해주세요.", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel2.setBounds(20, 110, 480, 30);															// 상세 메시지 레이블2을 (20, 110)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel2);																	// 배경 패널에 상세 메시지 레이블2 추가
		
		// 포인트 금액 입력 레이블
		inputPointAmtLabel = new CustomLabel("00");				// 텍스트를 가지는 레이블 생성
		inputPointAmtLabel.applyDialogInputDesign(20, 150);		// 다이얼로그 내 포인트/쿠폰 금액 입력 레이블을 의미하는 디자인 적용
		backgroundPanel.add(inputPointAmtLabel);				// 배경 패널에 포인트/쿠폰 금액 입력 레이블 추가
		
		// 숫자 키패드의 숫자 버튼
		for(int i=0; i<10; i++) {														// 0부터 9까지
			numberPadNumberButton[i] = new CustomButton(i + "");						// 텍스트를 가지는 버튼 생성
			numberPadNumberButton[i].addActionListener(new NumberPadNumberListener());	// 숫자 키패드의 숫자 버튼 클릭 이벤트 처리를 위한 리스너 등록
			backgroundPanel.add(numberPadNumberButton[i]);								// 배경 패널에 숫자 키패드의 숫자 버튼 추가
		}
		
		for(int i=1; i<10; i++) {				// 1부터 9까지
			int x = 20 + 80 * ((i-1) % 5);		// 20: 첫번째 버튼의 원점 x좌표, 80: 버튼 폭, (i-1) % (한 행에 들어가는 숫자 키패드의 버튼 개수): 행 기준 n번째
			int y = 200 + 70 * ((i-1) / 5);		// 200: 첫번째 버튼의 원점 y좌표, 70: 버튼 높이, (i-1) / (한 행에 들어가는 숫자 키패드의 버튼 개수): 열 기준 n번째
			
			numberPadNumberButton[i].applyDialogNumberPadDesign(x, y);	// 숫자 다이얼로그 내 숫자 키패드의 버튼을 의미하는 디자인 적용
		}
		numberPadNumberButton[0].applyDialogNumberPadDesign(340, 270);	// 0번 버튼에 다이얼로그 내 숫자 키패드의 버튼을 의미하는 디자인 적용
		
		// 숫자 키패드의 리셋 버튼
		numberPadResetButton = new CustomButton("C");							// 텍스트를 가지는 버튼 생성
		numberPadResetButton.applyDialogNumberPadDesign(420, 200);				// 다이얼로그 내 숫자 키패드의 버튼을 의미하는 디자인
		numberPadResetButton.setEnabled(false);									// 숫자 키패드의 리셋 버튼 비활성화
		numberPadResetButton.addActionListener(new NumberPadResetListener());	// 숫자 키패드의 리셋 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(numberPadResetButton);								// 배경 패널에 숫자 키패드의 리셋 버튼 추가
		
		// 숫자 키패드의 입력 버튼
		numberPadEnterButton = new CustomButton("입력");							// 텍스트를 가지는 버튼 생성
		numberPadEnterButton.applyDialogNumberPadDesign(420, 270);				// 다이얼로그 내 숫자 키패드의 버튼을 의미하는 디자인
		numberPadEnterButton.setEnabled(false);									// 숫자 키패드의 입력 버튼 비활성화
		numberPadEnterButton.addActionListener(new NumberPadEnterListener());	// 숫자 키패드의 입력 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(numberPadEnterButton);								// 배경 패널에 숫자 키패드의 입력 버튼 추가

		// 취소 버튼
		cancelButton = new CustomButton("취소");						// 텍스트를 가지는 버튼 생성
		cancelButton.applyDialogCancelDesign(210, 350);				// 다이얼로그 내 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());		// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(cancelButton);							// 배경 패널에 취소 버튼 추가

		this.setVisible(true);	// 다이얼로그 보여주기
		
	}
	
	/* 메소드명: getUsePointAmt
	 * 파라미터: 없음
	 * 반환값: int usePointAmt (사용할 포인트 금액)
	 * 기능 성명: 사용할 포인트 금액을 반환한다.
	 */
	public int getUsePointAmt() {
		return usePointAmt;
	}

	/* 메소드명: setPropertyOfNumberPadNumber
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadNumber() {
		int inputPointAmtLabelLength = inputPointAmtLabel.getText().length();	// 포인트 금액 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputPointAmtLabelLength >= 5) {						// 입력 가능한 최대 자리수에 도달한 경우
			for(int i=0; i<10; i++) {								
				numberPadNumberButton[i].setEnabled(false);		// 숫자 키패드의 숫자 버튼 비활성화
			}
		
		} else {												// 입력 가능한 최대 자리수에 도달하지 않은 경우
			for(int i=0; i<10; i++) {
				numberPadNumberButton[i].setEnabled(true);		// 숫자 키패드의 숫자 버튼 활성화
			}
		}
		
	}
	
	/* 메소드명: setPropertyOfNumberPadReset
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 리셋 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadReset() {
		int inputPointAmtLabelLength = inputPointAmtLabel.getText().length();	// 포인트 금액 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputPointAmtLabelLength == 2) {				// 기본값(00) 길이에 도달한 경우
			numberPadResetButton.setEnabled(false);		// 숫자 키패드의 리셋 버튼 비활성화
			
		} else {										// 기본값(00) 길이에 도달하지 않은 경우
			numberPadResetButton.setEnabled(true);		// 숫자 키패드의 리셋 버튼 활성화
			
		}
		
	}
	
	/* 메소드명: setPropertyOfNumberPadEnter
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadEnter() {
		int inputPointAmtLabelLength = inputPointAmtLabel.getText().length();	// 포인트 금액 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputPointAmtLabelLength >= 3 && inputPointAmtLabelLength <= 5) {	// 사용 가능한 자리수인 경우
			numberPadEnterButton.setEnabled(true);								// 숫자 키패드의 입력 버튼 활성화
			
		} else {										// 사용 가능한 최소 자리수에 도달하지 않은 경우
			numberPadEnterButton.setEnabled(false);		// 숫자 키패드의 입력 버튼 비활성화
			
		}
		
	}
	
	/* 클래스명: NumberPadNumberListener
	 * 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NumberPadNumberListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 발생 시 최대 길이에 도달하지 않은 경우에 한하여 포인트 금액 입력 레이블에 클릭된 버튼의 숫자를 입력한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			int inputPointAmtLabelLength = inputPointAmtLabel.getText().length() - 2;	// 포인트 금액 입력 레이블에 입력되어 있는 텍스트에서 기본값(00)을 뺀 값의 길이
			
			if(inputPointAmtLabelLength == 0 && button.getText().equals("0")) {	// 기본값(00)만 입력된 상태에서 0이 입력된 경우
				return;															// 변화 없음
				
			}
			
			inputPointAmtLabel.setText(inputPointAmtLabel.getText().substring(0, inputPointAmtLabelLength) + button.getText() + "00");	// 포인트 금액 입력 레이블 텍스트 변경 (기존 입력 값 + 새로운 입력 값 + 기본값(00))
			
			setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadReset();		// 숫자 키패드의 리셋 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
		}
		
	}
	
	/* 클래스명: NumberPadResetListener
	 * 설명: 숫자 키패드의 리셋 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NumberPadResetListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 숫자 키패드의 리셋 버튼 클릭 이벤트 발생 시 최소 길이에 도달하지 않은 경우에 한하여 포인트 금액 입력 레이블에 작성된 내용을 지우고 기본값(00) 상태로 만든다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(inputError) {												// 직전에 입력값 예외가 발생한 경우
				inputPointAmtLabel.setForeground(CustomColor.KIOSK_BLACK);	// 포인트 금액 입력 레이블의 전경색을 KIOSK_BLACK로 지정
				inputError = false;											// 입력값 예외가 발생하지 않은 것으로 변경
			}
			
			inputPointAmtLabel.setText("00");	// 포인트 금액 입력 레이블 텍스트 수정
			
			setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
			setPropertyOfNumberPadReset();		// 숫자 키패드의 리셋 버튼의 활성화 여부를 적절하게 변경
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
		 * 기능 설명: 숫자 키패드의 입력 버튼 클릭 이벤트 발생 시 최소 길이에 도달하지 않은 경우에 입력한 포인트 금액을 적용한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int amt = Integer.parseInt(inputPointAmtLabel.getText());													// 입력한 포인트 금액
			int min = (availablePointAmt < CartedMenu.getTOTAL_AMT())? availablePointAmt : CartedMenu.getTOTAL_AMT();	// 사용 가능한 포인트 금액과 총 결제 금액 중 더 작은값 구하기
			
			if(amt <= min) {		// 입력한 포인트 금액이 사용 가능한 값이면
				usePointAmt = amt;	// 입력한 포인트 금액 저장
				dispose();			// 다이얼로그 창 종료
			
			} else {																			// 입력한 포인트 금액이 사용 불가능한 값이면 (사용 가능한 포인트 금액을 초과하거나 결제 금액을 초과하는 경우)
				inputError = true;																// 입력값 예외가 발생한 것으로 처리
				
				inputPointAmtLabel.setText("보유하신 포인트 혹은 결제 금액보다 작은 값으로 입력해주세요.");	// 포인트 금액 입력 레이블 텍스트 변경
				inputPointAmtLabel.setForeground(CustomColor.KIOSK_RED);						// 포인트 금액 입력 레이블의 전경색을 KIOSK_RED로 지정
				
				setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
				setPropertyOfNumberPadReset();		// 숫자 키패드의 리셋 버튼의 활성화 여부를 적절하게 변경
				setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
			}
			
		}

	}
	
	/* 클래스명: CancelListener
	 * 설명: 취소 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class CancelListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트 객체)
		 * 반환값: 없음
		 * 기능 설명: 취소 버튼이 클릭되면 다이얼로그 창을 종료한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();	// 다이얼로그 창 종료
		}
		
	}
}
