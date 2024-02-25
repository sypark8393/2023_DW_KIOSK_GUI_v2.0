package system.user.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import system.custom.*;
import system.main.KioskFrame;

public class AddMenuFailAlertDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2556031334143710337L;		// 버전 ID
	
	private String imageSource = "src/img/request_fail.gif";	// 이미지 레이블에 들어갈 이미지 경로
	
	private JPanel backgroundPanel;				// 배경 패널
	private CustomLabel alertMessageLabel;		// 알림 메시지 레이블
	private CustomLabel infoMessageLabel;		// 정보 메시지 레이블
	private CustomLabel imageLabel;				// 이미지 레이블
	private CustomLabel detailMessageLabel;		// 상세 메시지 레이블
	private CustomButton confirmButton;			// 확인 버튼
	
	/* 생성자: MenuAddFailDialog
	 * 파라미터: 없음
	 * 기능 설명: 총 주문 수량이 50인 상태에서 메뉴 추가 시 알림 메시지를 출력한다.
	 */
	public AddMenuFailAlertDialog() {
		super(KioskFrame.getInstance(), "", true);	// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		// Dialog 속성
		this.setSize(520, 320);							// 다이얼로그 크기 (520px * 320px)
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
		alertMessageLabel = new CustomLabel("주문 가능한 수량이 초과되었습니다.");	// 텍스트를 가지는 레이블 생성
		alertMessageLabel.applyAlertMessageDesign(2, 2);					// 알림 메시지 레이블을 의미하는 디자인 적용
		backgroundPanel.add(alertMessageLabel);								// 배경 패널에 알림 메시지 레이블 추가
		
		// 정보 메시지 레이블
		infoMessageLabel = new CustomLabel("(최대 주문 가능 수량: 50)", JLabel.CENTER, 17.0f);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		infoMessageLabel.setBounds(20, 50, 480, 30);										// 정보 메시지 레이블을 (20, 50)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(infoMessageLabel);												// 배경 패널에 정보 메시지 레이블 추가
		
		// 이미지 레이블
		imageLabel = new CustomLabel(new ImageIcon(imageSource));	// 이미지 아이콘을 가지는 레이블 생성
		imageLabel.setBounds(185, 90, 150, 110);					// 이미지 레이블을 (185, 90)에 위치시키고 크기는 150px * 110px로 설정
		backgroundPanel.add(imageLabel);							// 배경 패널에 이미지 레이블 추가
		
		// 상세 메시지 레이블
		String detailMessageLabelText = "<html><div style=\"text-align:center\">메뉴를 추가하시려면 주문 목록에서 메뉴를 삭제하시거나<br>"
										+ "수량을 감소시킨 후 다시 진행하시기 바랍니다.</div></html>";		// 상세 메시지 레이블에 들어갈 텍스트
		detailMessageLabel = new CustomLabel(detailMessageLabelText, JLabel.CENTER, 17.0f);		// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		detailMessageLabel.setBounds(20, 210, 480, 50);											// 상세 메시지 레이블을 (20, 210)에 위치시키고 크기는 480px * 30px로 설정
		backgroundPanel.add(detailMessageLabel);												// 배경 패널에 상세 메시지 레이블 추가
		
		// 확인 버튼
		confirmButton = new CustomButton("확인");						// 텍스트를 가지는 버튼 생성
		confirmButton.applyDialogConfirmDesign(210, 270);			// 다이얼로그 내 확인 버튼을 의미하는 디자인 적용
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
		 * 기능 설명: 확인 버튼이 클릭되면 다이얼로그 창을 종료한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();	// 다이얼로그 창 종료
		}
		
	}
}
