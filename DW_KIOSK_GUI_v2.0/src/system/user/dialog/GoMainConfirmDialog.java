package system.user.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import system.custom.*;
import system.main.KioskFrame;
import system.user.UserModePanel;

public class GoMainConfirmDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6179540656238076074L;

	private JPanel backgroundPanel;							// 배경 패널
	private CustomLabel alertMessageLabel;					// 알림 메시지 레이블
	private CustomLabel detailMessageLabel;					// 상세 메시지 레이블
	private CustomButton cancelButton, confirmButton;		// 취소, 확인 버튼
	
	/* 생성자: MenuAddFailDialog
	 * 파라미터: 없음
	 * 기능 설명: 주문 목록에 담긴 메뉴가 있는 상태에서 메인 화면으로 돌아가려는 경우 초기화 안내 및 진행 여부를 묻는다.
	 */
	public GoMainConfirmDialog() {
		super(KioskFrame.getInstance(), "", true);	// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		// Dialog 속성
		this.setSize(520, 150);							// 다이얼로그 크기 (520px * 150px)
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
		alertMessageLabel = new CustomLabel("메인으로 돌아가면 주문 목록이 초기화됩니다.");	// 텍스트를 가지는 레이블 생성
		alertMessageLabel.applyAlertMessageDesign(2, 2);							// 알림 메시지 레이블을 의미하는 디자인 적용
		backgroundPanel.add(alertMessageLabel);										// 배경 패널에 알림 메시지 레이블 추가
		
		// 상세 메시지 레이블
		detailMessageLabel = new CustomLabel("계속 진행하시겠습니까?", JLabel.CENTER, 17.0f);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel.setBounds(20, 60, 480, 30);									// 상세 메시지 레이블을 (20, 210)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel);										// 배경 패널에 상세 메시지 레이블 추가
		
		// 취소 버튼
		cancelButton = new CustomButton("취소");						// 텍스트를 가지는 버튼 생성
		cancelButton.applyDialogCancelDesign(155, 100);				// 다이얼로그 내 취소 버튼을 의미하는 디자인 적용
		cancelButton.addActionListener(new CancelListener());		// 취소 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(cancelButton);							// 배경 패널에 취소 버튼 추가
		
		// 확인 버튼
		confirmButton = new CustomButton("확인");						// 텍스트를 가지는 버튼 생성
		confirmButton.applyDialogConfirmDesign(265, 100);			// 다이얼로그 내 확인 버튼을 의미하는 디자인 적용
		confirmButton.addActionListener(new ConfirmListener());		// 확인 버튼 클릭 이벤트 처리를 위한 리스너 등록
		backgroundPanel.add(confirmButton);							// 배경 패널에 확인 버튼 추가
		
		this.setVisible(true);	// 다이얼로그 보여주기
	}
	
	/* 클래스명: ConfirmListener
	 * 설명: 확인 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class ConfirmListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트 객체)
		 * 반환값: 없음
		 * 기능 설명: 확인 버튼이 클릭되면 사용자 모드에서 진행한 모든 내용을 초기화(삭제)하고 메인을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();	// 다이얼로그 창 종료
			
			System.out.println("=======================================");
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(메뉴 패널) :: 메인으로 돌아갑니다.");
			
			UserModePanel.initialize();		// 사용자 모드 패널의 initialize() 메소드 호출
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
