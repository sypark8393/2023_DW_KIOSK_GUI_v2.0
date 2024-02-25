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
import system.main.KioskFrame;

public class BirthPromptDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7447021640506015557L;		// 버전 ID

	public static final int[] LAST_DAY_OF_MONTH = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // 월별 마지막 날짜
	
	private String birth = null;			// 생일 정보
	private boolean inputError = false;		// 입력값 예외 발생 여부 (true: 발생, false: 발생하지 않음)
	
	private JPanel backgroundPanel;											// 배경 패널
	private CustomLabel alertMessageLabel;									// 알림 메시지 레이블
	private CustomLabel infoMessageLabel;									// 정보 메시지 레이블
	private CustomLabel detailMessageLabel1, detailMessageLabel2;			// 상세 메시지 레이블
	private CustomLabel inputBirthLabel;									// 생일 입력 레이블
	private CustomButton[] numberPadNumberButton = new CustomButton[10];	// 숫자 키패드의 숫자 버튼
	private CustomButton numberPadResetButton;								// 숫자 키패드의 리셋 버튼
	private CustomButton numberPadEnterButton;								// 숫자 키패드의 입력 버튼
	private CustomButton cancelButton;										// 취소 버튼	
	
	/* 생성자: BirthPromptDialog
	 * 파라미터: 없음
	 * 기능 설명: 생일을 입력받는다.
	 */
	public BirthPromptDialog() {
		super(KioskFrame.getInstance(), "", true);	// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(생일 입력 다이얼로그) :: 생일을 입력받습니다.");
		
		// Dialog 속성
		this.setSize(520, 400);							// 다이얼로그 크기 (520px * 400px)
		this.setResizable(false);						// 다이얼로그 크기 변경 불가
		this.setUndecorated(true);						// 다이얼로그 타이틀바 제거
		this.setLocationRelativeTo(this.getParent());	// 다이얼로그를 부모 프레임의 중앙에 띄우기
				
		// 배경 패널
		backgroundPanel = new JPanel();															// 배경 패널 생성
		backgroundPanel.setLayout(null);														// 배경 패널의 레이아웃 매니저 제거
		backgroundPanel.setBackground(CustomColor.KIOSK_GRAY_242);								// 베경 패널의 배경색을 KIOSK_GRAY_242로 지정
		backgroundPanel.setBorder(new MatteBorder(2, 2, 2, 2, CustomColor.KIOSK_GRAY_217));		// 베경 패널의 테두리를 2px 두께의 KIOSK_GRAY_217로 지정
		this.add(backgroundPanel);																// 다이얼로그에 배경 패널 추가
		
		// 알림 메시지 레이블
		alertMessageLabel = new CustomLabel("맞춤형 정보 제공을 위해 생일을 입력해주세요.");	// 텍스트를 가지는 레이블 생성
		alertMessageLabel.applyAlertMessageDesign(2, 2);							// 알림 메시지 레이블을 의미하는 디자인 적용
		backgroundPanel.add(alertMessageLabel);										// 배경 패널에 알림 메시지 레이블 추가

		// 정보 메시지 레이블
		infoMessageLabel = new CustomLabel("(입력 예시: 8월 11일 => 0811)", JLabel.CENTER, 17);		// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		infoMessageLabel.setBounds(20, 50, 480, 30);											// 정보 메시지 레이블을 (20, 50)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(infoMessageLabel);													// 배경 패널에 정보 메시지 레이블 추가
		
		// 상세 메시지 레이블
		detailMessageLabel1 = new CustomLabel("- 정보 수신에 동의하지 않으시면 0000을 입력해주세요.", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel1.setBounds(20, 90, 480, 30);															// 상세 메시지 레이블1을 (20, 90)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel1);																// 배경 패널에 상세 메시지 레이블1 추가
		
		detailMessageLabel2 = new CustomLabel("- 포인트 적립을 취소하시려면 하단의 취소 버튼을 클릭해주세요.", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel2.setBounds(20, 110, 480, 30);															// 상세 메시지 레이블2을 (20, 110)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel2);																	// 배경 패널에 상세 메시지 레이블2 추가
		
		// 생일 입력 레이블
		inputBirthLabel = new CustomLabel();					// 텍스트를 가지지 않는 빈 레이블 생성
		inputBirthLabel.applyDialogInputDesign(20, 150);		// 다이얼로그 내 생일 입력 레이블을 의미하는 디자인 적용
		backgroundPanel.add(inputBirthLabel);					// 배경 패널에 생일 입력 레이블 추가
				
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
	
	/* 메소드명: getBirth
	 * 파라미터: 없음
	 * 반환값: String birh (생일)
	 * 기능 성명: 입력한 생일을 반환한다.
	 */
	public String getBirth() {
		return birth;
	}
	
	/* 메소드명: setPropertyOfNumberPadNumber
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadNumber() {
		int inputBirthLabelLength = inputBirthLabel.getText().length();	// 생일 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputBirthLabelLength >= 4) {						// 입력 가능한 최대 자리수에 도달한 경우
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
		int inputBirthLabelLength = inputBirthLabel.getText().length();	// 생일 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputBirthLabelLength == 0) {				// 입력된 내용이 없는 경우
			numberPadResetButton.setEnabled(false);		// 숫자 키패드의 리셋 버튼 비활성화
			
		} else {										// 입력된 내용이 있는 경우
			numberPadResetButton.setEnabled(true);		// 숫자 키패드의 리셋 버튼 활성화
			
		}
		
	}
	
	/* 메소드명: setPropertyOfNumberPadEnter
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadEnter() {
		int inputBirthLabelLength = inputBirthLabel.getText().length();	// 생일 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputBirthLabelLength == 4) {				// 입력 가능한 최대 자리수에 도달한 경우
			numberPadEnterButton.setEnabled(true);		// 숫자 키패드의 입력 버튼 활성화
			
		} else {										// 입력 가능한 최대 자리수에 도달하지 않은 경우
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
		 * 기능 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 발생 시 생일 입력 레이블에 클릭된 버튼의 숫자를 입력한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			inputBirthLabel.setText(inputBirthLabel.getText() + button.getText());		// 생일 입력 레이블의 텍스트 변경
			
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
		 * 기능 설명: 숫자 키패드의 리셋 버튼 클릭 이벤트 발생 시 생일 입력 레이블에 작성된 내용을 지운다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(inputError) {												// 직전에 입력값 예외가 발생한 경우
				inputBirthLabel.setForeground(CustomColor.KIOSK_BLACK);		// 생일 입력 레이블의 전경색을 KIOSK_BLACK로 지정
				inputError = false;											// 입력값 예외가 발생하지 않은 것으로 변경
			}
			
			inputBirthLabel.setText("");	// 생일 입력 레이블의 텍스트 변경
			
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
		 * 기능 설명: 숫자 키패드의 입력 버튼 클릭 이벤트 발생 시 입력된 생일 정보를 검증하고 저장한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String date = inputBirthLabel.getText();			// 입력된 생일
			
			int month = Integer.parseInt(date.substring(0, 2));	// 월
			int day = Integer.parseInt(date.substring(2));		// 일
			
			if(date.equals("0000")) {	// 입력된 값이 "0000"(정보 수신 미동의) 인 경우
				birth = date;			// 생일 정보 저장
				dispose();				// 다이얼로그 창 종료
			}
			
			if(month < 1 || month > 12 || day < 1 || day > LAST_DAY_OF_MONTH[month - 1]) {	// 입력된 월이 올바르지 않거나 입력된 일이 해당 월의 일 범위를 벗어나는 경우 
				
				inputError = true;		// 입력값 예외가 발생한 것으로 처리
				
				inputBirthLabel.setText("유효하지 않은 날짜입니다.");			// 생일 입력 레이블의 텍스트 변경
				inputBirthLabel.setForeground(CustomColor.KIOSK_RED);	// 생일 입력 레이블의 전경색을 KIOSK_RED로 지정

				setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
				setPropertyOfNumberPadReset();		// 숫자 키패드의 리셋 버튼의 활성화 여부를 적절하게 변경
				setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
				
			} else {			// 입력된 월과 일이 모두 올바른 경우
				birth = date;	// 생일 정보 저장
				dispose();		// 다이얼로그 창 종료
				
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
