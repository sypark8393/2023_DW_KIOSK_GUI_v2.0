package system.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.custom.CustomButton;
import system.custom.CustomColor;
import system.custom.CustomLabel;
import system.user.dialog.WaitNoAndReceiptDialog;

public class SelectPrintRecieptPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1733368646736263361L;		// 버전 ID

	private String backgroundImageSource = "src/img/background.png"; 		// 배경 이미지 경로
	private String printRecieptImageSource = "src/img/img_receipt.png";		// 영수증 출력 이미지 경로
	
	private CustomLabel guideMessageLabel;		// 안내 메시지 레이블
	private CustomLabel recieptLabel;			// 영수증 레이블
	private CustomLabel requestMessageLabel;	// 요청 메시지 레이블
	private CustomLabel imageLabel;				// 이미지 레이블
	private CustomButton notPrintButton;		// 미출력 버튼
	private CustomButton printButton;			// 출력 버튼
	
	/* 생성자: SelectPrintRecieptPanel
	 * 파라미터: 없음
	 * 기능 설명: 영수증 출력 선택 패널에서 보여질 구성요소의 속성을 설정한다.
	 */
	public SelectPrintRecieptPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
		
		// 안내 메시지 레이블
		guideMessageLabel = new CustomLabel("영수증 출력 여부를 선택해주세요.", JLabel.CENTER, 25);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		guideMessageLabel.applyGuideMessageDesign(0, 60);									// 생성된 레이블에 안내 메시지 레이블을 의미하는 디자인 적용
		this.add(guideMessageLabel);														// 패널에 안내 메시지 레이블 추가
				
		// 영수증 레이블
		recieptLabel = new CustomLabel("영수증", JLabel.CENTER, 18);		// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		recieptLabel.setForeground(CustomColor.KIOSK_GRAY_120);			// 영수증 레이블의 전경색을 KIOSK_GRAY_120로 지정
		recieptLabel.setBounds(300, 300, 120, 30);						// 영수증 레이블을 (300, 300)에 위치시키고 크기는 120px * 120px로 설정
		this.add(recieptLabel);											// 패널에 영수증 레이블 추가
				
		// 요청 메시지 레이블
		requestMessageLabel = new CustomLabel("영수증 출력을 원하시면 출력 버튼을 클릭해주세요.", JLabel.CENTER, 20);	// 텍스트를 가지고 정렬 방법과 폰트 크기가 지정된 레이블 생성
		requestMessageLabel.setBounds(110, 330, 500, 40);													// 요청 메시지 레이블을 (110, 330)에 위치시키고 크기는 500px * 40px로 설정
		this.add(requestMessageLabel);																		// 패널에 요청 메시지 레이블 추가
				
		// 이미지 레이블
		imageLabel = new CustomLabel(new ImageIcon(printRecieptImageSource));	// 이미지 아이콘을 가지는 레이블 생성
		imageLabel.setBounds(80, 280, 560, 360);								// 이미지 레이블을 (80, 280)에 위치시키고 크기는 560px * 360px 로 설정
		this.add(imageLabel);													// 패널에 이미지 레이블 추가
		
		// 미출력 버튼
		notPrintButton = new CustomButton("미출력");					// 텍스트를 가지는 버튼 생성
		notPrintButton.applyNotPrintDesign(120, 680);				// (영수증) 미출력 버튼을 의미하는 디자인 적용
		notPrintButton.addActionListener(new NotPrintListener());	// 미출력 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(notPrintButton);									// 패널에 미출력 버튼 추가 
		
		// 출력 버튼
		printButton = new CustomButton("출력");					// 텍스트를 가지는 버튼 생성
		printButton.applyPrintDesign(360, 680);					// (영수증) 출력 버튼을 의미하는 디자인 적용
		printButton.addActionListener(new PrintListener());		// 출력 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(printButton);									// 패널에 출력 버튼 추가 	
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
	
	/* 클래스명: NotPrintListener
	 * 설명: 미출력 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class NotPrintListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 미출력 버튼 클릭 이벤트 발생 시 대기번호 출력을 위한 다이얼로그를 호출한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new WaitNoAndReceiptDialog(false);	// 대기번호 출력을 위한 다이얼로그 호출

		}
		
	}

	/* 클래스명: PrintListener
	 * 설명: 출력 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class PrintListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 출력 버튼 클릭 이벤트 발생 시 대기번호와 영수증 출력을 위한 다이얼로그를 호출한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new WaitNoAndReceiptDialog(true);	// 대기번호 출력을 위한 다이얼로그 호출
			
		}
		
	}
}
