package system.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import system.custom.CustomLabel;

public class FinishOrderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3945068467359119890L;		// 버전 ID
	
	private String backgroundImageSource = "src/img/background.png"; 		// 배경 이미지 경로
	private String imageSource = "src/img/img_order_finish.gif";			// 이미지 경로
	private String backImageSource = "src/img/img_order_finish_back.png";	// 뒤에 들어갈 이미지 경로
	
	private CustomLabel guideMessageLabel;		// 안내 메시지 레이블
	private CustomLabel waitNoLabel;			// 대기번호 레이블
	private CustomLabel imageLabel;				// 이미지 레이블
	private CustomLabel countDownLabel;			// 카운트다운 레이블
	private CustomLabel backImageLabel;			// 뒤에 들어갈 이미지 레이블
	
	private Timer timer;	// 타이머 객체
	
	/* 생성자: FinishOrderPanel
	 * 파라미터: 없음
	 * 기능 설명: 주문 완료 패널에서 보여질 구성요소의 속성을 설정한다.
	 */
	public FinishOrderPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("주문이 완료되었습니다.", JLabel.CENTER, 25);		// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);								// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);													// 패널에 안내 메시지 레이블 추가
						
		// 대기번호 레이블
		waitNoLabel = new CustomLabel("", JLabel.CENTER);	// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		waitNoLabel.changeFontToHighLight(45);				// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 45로 지정
		waitNoLabel.setBounds(160, 220, 400, 45);			// 대기번호 레이블을 (160, 220)에 위치시키고 크기는 400px * 45px로 설정
		this.add(waitNoLabel);								// 패널에 대기번호 레이블 추가
		
		// 이미지 레이블
		imageLabel = new CustomLabel(new ImageIcon(imageSource));	// 이미지 아이콘을 가지는 레이블 생성
		imageLabel.setBounds(200, 290, 320, 320);					// 이미지 레이블을 (200, 290)에 위치시키고 크기는 320px * 320px로 설정
		this.add(imageLabel);										// 패널에 이미지 레이블 추가
		
		// 카운트다운 레이블
		countDownLabel = new CustomLabel("", JLabel.CENTER);	// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		countDownLabel.changeFontToHighLight(35);				// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 35로 지정
		countDownLabel.setBounds(135, 610, 450, 80);			// 카운트다운 레이블을 (135, 610)에 위치시키고 크기는 450px * 80px로 설정
		this.add(countDownLabel);								// 패널에 카운트다운 레이블 추가
		
		// 뒤에 들어갈 이미지 레이블
		backImageLabel = new CustomLabel(new ImageIcon(backImageSource));	// 이미지 아이콘을 가지는 레이블 생성
		backImageLabel.setBounds(80, 270, 560, 440);						// 뒤에 들어갈 이미지 레이블을 (80, 270)에 위치시키고 크기는 560px * 440px로 설정
		this.add(backImageLabel);											// 패널에 뒤에 들어갈 이미지 레이블 추가
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
	 * 파라미터: String waitNo (대기번호)
	 * 반환값: 없음
	 * 기능 설명: 주문 완료 패널의 대기번호 레이블과 카운트다운 레이블의 내용을 초기화한 후 카운트다운을 시작한다.
	 */
	public void initialize() {
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(주문 완료 패널) :: 주문이 완료되어 5초 후 메인으로 돌아갑니다.");
		
		waitNoLabel.setText("대기번호 : " + UserModePanel.salesTotal.getWaitNo());		// 대기번호 레이블의 텍스트 변경
		
		countDownLabel.setText("<html><div style=\"text-align:center\">5초 후<br>메인 화면으로 돌아갑니다.</div></html>");
		
		timer = new Timer(1000, new TimerListener());	// 1초마다 이벤트가 발생하는 타이머 객체 생성
		timer.start();									// 타이머 시작
	}
	
	/* 클래스명: TimerListener
	 * 설명: 카운트다운 이벤트 처리를 위한 리스너
	 */
	private class TimerListener implements ActionListener {
		int count = 4;	// 남은 시간(초 단위)
		
		/* /* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 카운트다운을 수행하면서 카운트다운 레이블에 남은 시간을 표시하고
		 * 			남은 시간이 0이 되면 사용자 모드에서 진행한 모든 내용을 초기화(삭제)하고 메인을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(count == 0) {					// 남은 시간이 0이 되면
				((Timer)e.getSource()).stop();	// 타이머 중지
			
				UserModePanel.initialize();		// 사용자 모드 패널의 initialize() 메소드 호출
			}
			
			// 남은 시간이 0이 아니라면
			countDownLabel.setText("<html><div style=\"text-align:center\">"+ count +"초 후<br>메인 화면으로 돌아갑니다.</div></html>");	// 감소된 시간으로 카운트다운 레이블 텍스트 변경
			count--;																												// 남은 시간 1 감소
		}
		
	}
}
