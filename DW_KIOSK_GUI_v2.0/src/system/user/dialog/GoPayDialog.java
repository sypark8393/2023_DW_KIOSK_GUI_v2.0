package system.user.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.Timer;

import system.custom.CustomColor;
import system.custom.CustomLabel;
import system.main.KioskFrame;
import system.user.UserModePanel;
import system.user.process.UpdateDataProcess;

public class GoPayDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3383215547835234974L;		// 버전 ID
	
	private String imageSource = "src/img/paying(90px).gif";	// 이미지 레이블에 들어갈 이미지 경로
	
	private final static String[] CARD_NAME_LIST = {"비씨", "국민", "하나", "삼성", "신한", "현대", "롯데", "씨티", "농협", "우리"}; // 제휴되어 있는 카드사 목록
	
	private JPanel backgroundPanel;				// 배경 패널
	private CustomLabel alertMessageLabel;		// 알림 메시지 레이블
	private CustomLabel detailMessageLabel;		// 상세 메시지 레이블
	private CustomLabel countDownLabel;			// 카운트다운 레이블
	private CustomLabel imageLabel;				// 이미지 레이블
	
	private Timer timer;	// 타이머 객체
	
	/* 생성자: GoPayDialog
	 * 파라미터: char type (진행 유형, C(카드 결제)/S(간편 결제))
	 * 기능 설명: 승인 요청 버튼 클릭 시 승인 진행중 메시지를 출력한다.
	 */
	public GoPayDialog(char type) {
		super(KioskFrame.getInstance(), "", true);	// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(승인 요청 다이얼로그) :: 승인 요청 중입니다.");
		
		// Dialog 속성
		this.setSize(520, 200);							// 다이얼로그 크기 (520px * 200px)
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
		alertMessageLabel = new CustomLabel("승인 요청 중입니다.");	// 텍스트를 가지는 레이블 생성
		alertMessageLabel.applyAlertMessageDesign(2, 2);		// 알림 메시지 레이블을 의미하는 디자인 적용
		backgroundPanel.add(alertMessageLabel);					// 배경 패널에 알림 메시지 레이블 추가
		
		// 상세 메시지 레이블
		detailMessageLabel = new CustomLabel("", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel.setBounds(20, 50, 480, 30);					// 상세 메시지 레이블을 (20, 50)에 위치시키고 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel);						// 배경 패널에 상세 메시지 레이블 추가
		
		if(type == 'C') {														// 진행 유형이 카드 결제인 경우
			detailMessageLabel.setText("결제가 완료될 때까지 카드를 빼지 마세요.");		// 상세 메시지 레이블 텍스트 변경
			
		} else {																// 진행 유형이 간편 결제인 경우
			detailMessageLabel.setText("결제가 완료될 때까지 휴대전화를 떼지 마세요.");	// 상세 메시지 레이블 텍스트 변경
			
		}
		
		// 카운트다운 레이블
		countDownLabel = new CustomLabel("4", JLabel.CENTER);		// 텍스트를 가지고 정렬 방법이 지정된 레이블 생성
		countDownLabel.changeFontToHighLight(50);					// 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 50으로 지정
		countDownLabel.setBounds(220, 90, 80, 80);					// 카운트다운 레이블을 (220, 90)에 위치시키고 80px * 80px로 설정
		backgroundPanel.add(countDownLabel);						// 배경 패널에 카운트다운 레이블 추가
		
		// 타이머
		timer = new Timer(1000, new TimerListener());	// 1초마다 이벤트가 발생하는 타이머 객체 생성
		timer.start();									// 타이머 시작
		
		// 이미지 레이블
		imageLabel = new CustomLabel(new ImageIcon(imageSource));	// 아이콘을 가지는 레이블 생성
		imageLabel.setBounds(400, 80, 120, 120);					// 이미지 레이블을 (400, 80)에 위치시키고 120px * 120px로 설정
		backgroundPanel.add(imageLabel);							// 배경 패널에 이미지 레이블 추가
		
		this.setVisible(true);	// 다이얼로그 보여주기
		
	}
	
	/* 클래스명: TimerListener
	 * 설명: 카운트다운 이벤트 처리를 위한 리스너
	 */
	private class TimerListener implements ActionListener {
		int count = 3;	// 남은 시간(초 단위)
		
		/* /* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 카운트다운을 수행하면서 카운트다운 레이블에 남은 시간을 표시하고
		 * 			남은 시간이 0이 되면 포인트/쿠폰 사용 여부에 따라 포인트 적립 혹은 데이터 처리 단계로 넘어간다. 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(count == 0) {					// 남은 시간이 0이 되면
				((Timer)e.getSource()).stop();	// 타이머 중지
				
				Random random = new Random();	// 난수 생성을 위한 Random 객체
				
				// 카드사명
				int size = CARD_NAME_LIST.length;						// 제휴되어 있는 카드사 개수
				String cardName = CARD_NAME_LIST[random.nextInt(size)];	// 제휴되어 있는 카드사 중 하나 추출해서 저장
				UserModePanel.salesTotal.setCardName(cardName);			// 카드사명 저장
				
				// 카드번호 - 난수 6자리 + '*' 6자리 + 난수 4자리
				String cardNo = String.format("%06d", random.nextInt(1000000)) + "******" + String.format("%04d", random.nextInt(10000));
				UserModePanel.salesTotal.setCardNo(cardNo);		// 카드번호 저장
				
				// 할부개월 - 00(일시불) 고정
				UserModePanel.salesTotal.setCardQuota("00");	// 할부 개월 저장
				
				// 승인번호 - 난수 8자리
				UserModePanel.salesTotal.setAuthCode(String.format("%08d", random.nextInt(100000000)));	// 승인번호 저장
				
				if(UserModePanel.salesTotal.getDiscountId().equals(" ")) {	// 포인트나 쿠폰을 사용하지 않은 경우
					System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(승인 요청 다이얼로그) :: 승인 요청이 완료되어 포인트 적립 패널로 넘어갑니다.");
					
					UserModePanel.payCardOrSimplePanel.setVisible(false);	// 결제 요쳥 패널 감추기
					UserModePanel.earnPointPanel.setVisible(true);			// 포인트 적립 패널 보여주기
					UserModePanel.earnPointPanel.initialize();				// 포인트 적립 패널 초기화 메소드 호출
					
				} else {	// 포인트나 쿠폰을 사용한 경우
					System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(승인 요청 다이얼로그) :: 승인 요청이 완료되어 데이터 처리 프로세스를 진행합니다.");
					
					UserModePanel.payCardOrSimplePanel.setVisible(false);	// 결제 요쳥 패널 감추기
					UpdateDataProcess.updateData();							// 데이터 처리 프로세스 호출
				}
				
				dispose();		// 다이얼로그 창 종료
				
			}
			
			// 남은 시간이 0이 아니라면
			countDownLabel.setText(count + "");		// 감소된 시간으로 카운트다운 레이블 텍스트 변경
			count--;								// 남은 시간 1 감소
		}
		
	}
	
}
