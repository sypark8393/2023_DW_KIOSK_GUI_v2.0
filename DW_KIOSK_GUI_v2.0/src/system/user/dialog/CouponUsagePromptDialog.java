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

public class CouponUsagePromptDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1669157490367614561L;		// 버전 ID

	private int availableCouponAmt;		// 사용 가능한 쿠폰 금액
	private int useCouponAmt = 0;		// 사용할 쿠폰 금액
	
	private JPanel backgroundPanel;									// 배경 패널
	private CustomLabel alertMessageLabel;							// 알림 메시지 레이블
	private CustomLabel infoMessageLabel;							// 정보 메시지 레이블
	private CustomLabel detailMessageLabel1, detailMessageLabel2;	// 상세 메시지 레이블
	private CustomButton cancelButton, confirmButton;				// 취소, 확인 버튼
	private CustomLabel cautionMessageLabel;						// 주의사항 레이블
	
	/* 생성자: CouponAmtPromptDialog
	 * 기능 설명: 쿠폰 사용 여부를 입력받는다.
	 */
	public CouponUsagePromptDialog(int availableCouponAmt) {
		super(KioskFrame.getInstance(), "", true);	// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(쿠폰 사용 여부 입력 다이얼로그) :: 쿠폰 사용 여부를 입력받습니다.");
		
		// Dialog 속성
		if(availableCouponAmt <= CartedMenu.getTOTAL_AMT()) {
			this.setSize(520, 200);		// 다이얼로그 크기 (520px * 200px)
			
		} else {
			this.setSize(520, 240);		// 다이얼로그 크기 (520px * 240px)
			
		}

		this.setResizable(false);						// 다이얼로그 크기 변경 불가
		this.setUndecorated(true);						// 다이얼로그 타이틀바 제거
		this.setLocationRelativeTo(this.getParent());	// 다이얼로그를 부모 프레임의 중앙에 띄우기
		
		this.availableCouponAmt = availableCouponAmt;	// 사용 가능한 쿠폰 금액 저장
		
		// 배경 패널
		backgroundPanel = new JPanel();															// 배경 패널 생성
		backgroundPanel.setLayout(null);														// 배경 패널의 레이아웃 매니저 제거
		backgroundPanel.setBackground(CustomColor.KIOSK_GRAY_242);								// 베경 패널의 배경색을 KIOSK_GRAY_242로 지정
		backgroundPanel.setBorder(new MatteBorder(2, 2, 2, 2, CustomColor.KIOSK_GRAY_217));		// 베경 패널의 테두리를 2px 두께의 KIOSK_GRAY_217로 지정
		this.add(backgroundPanel);																// 다이얼로그에 배경 패널 추가
		
		// 알림 메시지 레이블
		alertMessageLabel = new CustomLabel("쿠폰 사용 여부를 입력해주세요.");		// 텍스트를 가지는 레이블 생성
		alertMessageLabel.applyAlertMessageDesign(2, 2);					// 알림 메시지 레이블을 의미하는 디자인 적용
		backgroundPanel.add(alertMessageLabel);								// 배경 패널에 알림 메시지 레이블 추가

		// 정보 메시지 레이블
		String infoMessageLabelText = "쿠폰 금액: " + String.format("%,d원", availableCouponAmt)
									+ " / 결제 금액: " + String.format("%,d원)", CartedMenu.getTOTAL_AMT());	// 정보 메시지 레이블에 들어갈 텍스트
		infoMessageLabel = new CustomLabel(infoMessageLabelText, JLabel.CENTER, 17);						// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		infoMessageLabel.setBounds(20, 50, 480, 30);														// 정보 메시지 레이블을 (20, 50)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(infoMessageLabel);																// 배경 패널에 정보 메시지 레이블 추가
		
		// 상세 메시지 레이블
		detailMessageLabel1 = new CustomLabel("- 쿠폰을 사용하시려면 확인 버튼을 클릭해주세요.", JLabel.CENTER, 17);		// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel1.setBounds(20, 90, 480, 30);															// 상세 메시지 레이블1을 (20, 90)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel1);																// 배경 패널에 상세 메시지 레이블1 추가
		
		detailMessageLabel2 = new CustomLabel("- 쿠폰 사용을 취소하시려면 취소 버튼을 클릭해주세요.", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel2.setBounds(20, 110, 480, 30);														// 상세 메시지 레이블2을 (20, 110)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel2);																// 배경 패널에 상세 메시지 레이블2 추가
		
		// 취소 버튼
		cancelButton = new CustomButton("취소");						// 텍스트를 가지는 버튼 생성
		cancelButton.applyDialogCancelDesign(155, 150);				// 다이얼로그 내 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());		// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(cancelButton);							// 배경 패널에 취소 버튼 추가

		// 확인 버튼
		confirmButton = new CustomButton("확인");						// 텍스트를 가지는 버튼 생성
		confirmButton.applyDialogConfirmDesign(265, 150);			// 다이얼로그 내 확인 버튼을 의미하는 디자인 적용
		confirmButton.addActionListener(new ConfirmListener());		// 확인 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(confirmButton);							// 배경 패널에 확인 버튼 추가
		
		// 주의사항 레이블
		if(availableCouponAmt > CartedMenu.getTOTAL_AMT()) {													// 사용 가능한 쿠폰 금액이 결제 금액보다 큰 경우
			cautionMessageLabel =  new CustomLabel("※쿠폰 사용 후 남은 금액은 반환되지 않습니다.※", JLabel.CENTER, 17);	// 텍스트를 가지고 정렬 기준과 폰트 크기가 지정된 레이블 생성
			cautionMessageLabel.setForeground(CustomColor.KIOSK_RED);											// 레이블의 전경색을 KIOSK_RED로 지정
			cautionMessageLabel.setBounds(20, 195, 480, 30);													// 주의사항 레이블을 (20, 195)에 위치시키고 크기를 480px * 30px로 설정
			backgroundPanel.add(cautionMessageLabel);															// 배경 패널에 주의사항 레이블 추가
		}
		
		this.setVisible(true);	// 다이얼로그 보여주기
		
	}
	
	/* 메소드명: getUseCouponAmt
	 * 파라미터: 없음
	 * 반환값: int useCouponAmt (쿠폰 금액 혹은 결제 금액(쿠폰 금액이 결제 금액보다 더 큰 경우))
	 * 기능 설명: 쿠폰 금액 혹은 결제 금액을 반환한다.
	 */
	public int getUseCouponAmt() {
		return useCouponAmt;
	}
	
	/* 클래스명: ConfirmListener
	 * 설명: 확인 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class ConfirmListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트 객체)
		 * 반환값: 없음
		 * 기능 설명: 확인 버튼이 클릭되면 쿠폰 금액 혹은 결제 금액(쿠폰 금액이 결제 금액보다 더 큰 경우)을 저장하고 다이얼로그 창을 종료한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			useCouponAmt = (availableCouponAmt <= CartedMenu.getTOTAL_AMT())? availableCouponAmt: CartedMenu.getTOTAL_AMT();	// 쿠폰 금액이 결제 금액보다 작거나 같으면 쿠폰 금액, 크면 결제 금액 저장
			
			dispose();	// 다이얼로그 창 종료
			
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
