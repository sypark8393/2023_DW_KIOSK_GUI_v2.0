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
import system.data.model.CartedMenu;
import system.data.model.Member;
import system.user.dialog.BirthPromptDialog;
import system.user.process.UpdateDataProcess;

public class EarnPointPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -320281743194241609L;	// 버전 ID
	
	private String backgroundImageSource = "src/img/background.png";	// 배경 이미지 경로

	private CustomLabel guideMessageLabel;									// 안내 메시지 레이블
	private CustomLabel inputMemberIdLabel;									// 회원 ID 입력 레이블
	private CustomLabel inputMemberIdExampleLabel;							// 회원 ID 입력 예시 레이블
	private CustomButton[] numberPadNumberButton = new CustomButton[10];	// 숫자 키패드의 숫자 버튼
	private CustomButton numberPadEraseButton;								// 숫자 키패드의 지우기 버튼
	private CustomButton numberPadEnterButton;								// 숫자 키패드의 입력 버튼
	private CustomButton notEarnPointButton;								// 포인트 미적립 버튼

	/* 생성자: EarnPointPanel
	 * 파라미터: 없음
	 * 기능 설명: 포인트를 적립하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public EarnPointPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("포인트 적립을 위해 휴대전화 번호를 입력해주세요.");	// 텍스트를 가지는 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);								// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);													// 패널에 안내 메시지 레이블 추가
		
		// 회원 ID 입력 레이블
		inputMemberIdLabel = new CustomLabel("010");		// 텍스트를 가지는 레이블 생성
		inputMemberIdLabel.applyInputIdDesign(140, 220);	// 회원 ID 입력을 위한 레이블을 의미하는 디자인 적용
		this.add(inputMemberIdLabel);						// 패널에 회원 ID 입력 레이블 추가
		
		// 회원 ID 입력 예시 레이블
		inputMemberIdExampleLabel = new CustomLabel("예시) 01012345678");		// 텍스트를 가지는 레이블 생성
		inputMemberIdExampleLabel.applyInputIdExampleDesign(240, 300);		// 회원 ID 입력 예시 레이블을 의미하는 디자인 적용
		this.add(inputMemberIdExampleLabel);								// 패널에 회원 ID 입력 예시 레이블 추가
		
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
		
		// 지우기 버튼
		numberPadEraseButton = new CustomButton("<");							// 텍스트를 가지는 버튼 생성
		numberPadEraseButton.applyNumberPadDesign(135, 680);					// 숫자 키패드의 버튼을 의미하는 디자인 적용
		numberPadEraseButton.setEnabled(false);									// 숫자 키패드의 지우기 버튼 비활성화
		numberPadEraseButton.addActionListener(new NumberPadEraseListener());	// 숫자 키패드의 지우기 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(numberPadEraseButton);											// 패널에 숫자 키패드의 지우기 버튼 추가
		
		// 입력 버튼
		numberPadEnterButton = new CustomButton("입력");							// 텍스트를 가지는 버튼 생성
		numberPadEnterButton.applyNumberPadDesign(435, 680);					// 숫자 키패드의 버튼을 의미하는 디자인 적용
		numberPadEnterButton.setEnabled(false);									// 숫자 키패드의 입력 버튼 비활성화
		numberPadEnterButton.addActionListener(new NumberPadEnterListener());	// 숫자 키패드의 입력 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(numberPadEnterButton);											// 패널에 숫자 키패드의 입력 버튼 추가
				
		// 포인트 미적립 버튼
		notEarnPointButton = new CustomButton("미적립");						// 텍스트를 가지는 버튼 생성
		notEarnPointButton.applyNotEarnPointDesign(520, 900);				// 포인트 미적립 버튼을 의미하는 디자인 적용
		notEarnPointButton.addActionListener(new NotEarnPointListener());	// 포인트 미적립 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(notEarnPointButton);										// 패널에 포인트 미적립 버튼 추가
		
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
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 포인트 적립 패널에서 사용하는 구성 요소의 내용 및 속성을 초기화한다.
	 */
	public void initialize() {
		inputMemberIdLabel.setText("010");	// 회원 ID 입력 레이블 텍스트 변경
		
		setPropertyOfNumberPadNumber();		// 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경
		setPropertyOfNumberPadErase();		// 숫자 키패드의 지우기 버튼의 활성화 여부를 적절하게 변경
		setPropertyOfNumberPadEnter();		// 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경
	}
	
	/* 메소드명: setPropertyOfNumberPadNumber
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 숫자 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadNumber() {
		int inputMemberIdLabelLength = inputMemberIdLabel.getText().length();		// 회원 ID 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputMemberIdLabelLength == 11) {					// 회원 ID 길이에 도달한 경우
			for(int i=0; i<10; i++) {								
				numberPadNumberButton[i].setEnabled(false);		// 숫자 키패드의 숫자 버튼 비활성화
			}
			
		} else {											// 회원 ID 길이에 도달하지 않은 경우
			for(int i=0; i<10; i++) {
				numberPadNumberButton[i].setEnabled(true);	// 숫자 키패드의 숫자 버튼 활성화
			}
			
		}
		
	}

	/* 메소드명: setPropertyOfNumberPadErase
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 지우기 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadErase() {
		int inputMemberIdLabelLength = inputMemberIdLabel.getText().length();	// 회원 ID 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputMemberIdLabelLength == 3) {				// 기본값 길이에 도달한 경우
			numberPadEraseButton.setEnabled(false);		// 숫자 키패드의 지우기 버튼 비활성화
			
		} else {										// 기본값 길이에 도달하지 않은 경우
			numberPadEraseButton.setEnabled(true);		// 숫자 키패드의 지우기 버튼 활성화
			
		}
		
	}
	
	/* 메소드명: setPropertyOfNumberPadEnter
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 입력 버튼의 활성화 여부를 적절하게 변경한다.
	 */
	public void setPropertyOfNumberPadEnter() {
		int inputMemberIdLabelLength = inputMemberIdLabel.getText().length();	// 회원 ID 입력 레이블에 입력되어 있는 텍스트의 길이
		
		if(inputMemberIdLabelLength == 11) {			// 회원 ID 길이에 도달한 경우
			numberPadEnterButton.setEnabled(true);		// 숫자 키패드의 입력 버튼 활성화
			
		} else {										// 회원 ID 길이에 도달하지 않은 경우
			numberPadEnterButton.setEnabled(false);		// 숫자 키패드의 입력 버튼 비활성화
			
		}
		
	}
	
	/* 메소드명: searchMember
	 * 파라미터: String memberId (조회할 회원 ID)
	 * 반환값: Member member (회원 조회 성공 시 해당 회원의 정보가 담긴 Member 객체)
	 * 기능 설명: 파라미터로 받은 회원 ID로 회원 조회 후 해당 회원의 정보가 담긴 Member 객체를 반환한다.
	 */
	public Member searchMember(String memberId) {
		Member member = new Member(memberId, 0, "0000");	// 비회원일 때의 정보를 가진 Member 객체
		
		BufferedReader in;	// 입력 스트림
		
		try {
			System.out.println("EarnPointPanel >> searchMember 메소드에서 회원 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Member.FILE_NAME));		// 파일 내용을 읽어오기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("회원 파일이 존재하지 않습니다.");
			return null;
		}
		
		String s;		// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;	// 구분자를 기준으로 분리된 데이터가 저장될 배열
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				if(tmp[Member.INDEX_MEMBER_ID].equals(memberId)) {		// 일치하는 회원 ID가 있는 경우
					System.out.println("회원 조회에 성공하였습니다.");
					
					member.setMemberId(tmp[Member.INDEX_MEMBER_ID]);				// 회원 ID
					member.setPoint(Integer.parseInt(tmp[Member.INDEX_POINT]));		// 포인트
					member.setBirth(tmp[Member.INDEX_BIRTH]);						// 생일
					
					break;
				}
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			
		} catch (IOException e) {	// 입출력 예외가 발생한 경우
			System.out.println("회원 조회에 실패하였습니다.");
			return null;
			
		}
		
		return member;
	}
	
	/* 클래스명: NumberPadNumberListener
	 * 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NumberPadNumberListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 숫자 키패드의 숫자 버튼 클릭 이벤트 발생 시 회원 ID 입력 레이블에 클릭된 버튼의 숫자를 입력한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			inputMemberIdLabel.setText(inputMemberIdLabel.getText() + button.getText());	// 회원 ID 입력 레이블에 클릭된 버튼의 숫자를 입력

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
		 * 기능 설명: 숫자 키패드의 지우기 버튼 클릭 이벤트 발생 시 회원 ID 입력 레이블에 입력된 마지막 숫자를 지운다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int inputMemberIdLabelLength = inputMemberIdLabel.getText().length();	// 회원 ID 입력 레이블에 입력되어 있는 텍스트의 길이
			
			inputMemberIdLabel.setText(inputMemberIdLabel.getText().substring(0, inputMemberIdLabelLength-1));	// 회원 ID 입력 레이블에 입력된 마지막 숫자를 지움

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
		 * 기능 설명: 숫자 키패드의 입력 버튼 클릭 이벤트 발생 시 회원 ID 입력 레이블에 입력된 회원 ID로 회원 조회 후 생일 정보 유무에 따라 다음 프로세스를 진행한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Member member = searchMember(inputMemberIdLabel.getText());		// 회원 정보가 담긴 Member 객체
			String birth = member.getBirth();								// 생일
			
			if(birth.equals("0000")) {							// 입력된 생일 정보가 없는 회원이거나 최초 적립하는 비회원인 경우
				birth = new BirthPromptDialog().getBirth();		// 생일 입력을 위한 다이얼로그를 호출하고 입력한 생일을 받아옴
				
			} 
			
			if(birth != null) {														// 입력된 생일 정보가 있는 회원이거나 생일 입력 다이얼로그에서 생일을 입력한 경우
				
				UserModePanel.salesTotal.setMemberId(inputMemberIdLabel.getText());	// 포인트를 적립한 회원 ID 저장
				
				int rewardPoints = (int)(CartedMenu.getTOTAL_AMT() * 0.01);			// 적립할 포인트 금액 계산
				
				UserModePanel.salesTotal.setRewardPts(rewardPoints);						// 적립할 포인트 금액 저장
				UserModePanel.salesTotal.setTotalPts(member.getPoint() + rewardPoints);		// 누적 포인트 금액 저장
				
				System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트 적립 패널) :: 포인트 적립이 완료되어 데이터 처리 프로세스를 진행합니다.");
				
				setVisible(false);					// 포인트 적립 패널 감추기
				UpdateDataProcess.setBirth(birth);	// 생일 정보 저장 메소드 호출
				UpdateDataProcess.updateData();		// 데이터 처리 프로세스 호출
			}
			
		}
		
	}
	
	/* 클래스명: NotEarnPointListener
	 * 설명: 포인트 미적립 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NotEarnPointListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 포인트 미적립 버튼 클릭 이벤트 발생 시 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(포인트 적립 패널) :: 데이터 처리 프로세스를 진행합니다.");
			
			setVisible(false);					// 포인트 적립 패널 감추기
			UpdateDataProcess.updateData();		// 데이터 처리 프로세스 호출
		}
		
	}
		
}
