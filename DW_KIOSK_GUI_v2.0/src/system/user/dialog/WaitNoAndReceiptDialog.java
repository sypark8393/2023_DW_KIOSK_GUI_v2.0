package system.user.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

import system.custom.CustomButton;
import system.custom.CustomColor;
import system.data.model.SalesDetail;
import system.main.KioskFrame;
import system.user.UserModePanel;

public class WaitNoAndReceiptDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7645011099822759154L;		// 버전 ID

	// 기본 상점 정보
	private static final String STORE_NAME = "대우커피 상봉역점";					// 상호
	private static final String STORE_LICENSE_NO = "123-45-67890";				// 사업자번호
	private static final String CEO = "홍길동";									// 대표자명
	private static final String TEL = "02-000-0000";							// 전화번호
	private static final String ADDRESS = "서울특별시 중랑구 망우로 291 상봉빌딩 6층";	// 주소
	
	private static final Font FONT = new Font("Malgun Gothic", Font.PLAIN, 14);		// 대기번호 및 영수증 출력에 사용할 폰트 - 맑은 고딕
	
	private static int waitNoTextLine = 0;	// 대기번호 내용의 라인 수
	private static int receiptTextLine = 0;	// 영수증 내용의 라인 수
	
	private JPanel backgroundPanel;		// 배경 패널
	private CustomButton closeButton;	// 닫기 버튼
	private JTextArea waitNoArea;		// 대기번호용 텍스트 영역
	private JTextArea receiptArea;		// 영수증용 텍스트 영역
	private JScrollPane scrollPane;		// 스크롤 페인
	
	/* 생성자: WaitNoAndReceiptDialog
	 * 파라미터: boolean print (true: 영수증 출력, false: 영수증 미출력)
	 * 기능 설명: 영수증 출력 여부에 따라 대기번호 및 영수증을 보여준다.
	 */
	public WaitNoAndReceiptDialog(boolean print) {
		super(KioskFrame.getInstance(), "", true);		// 부모 클래스(JDialog)의 생성자 호출, modal창으로 지정
		
		if(print) {	// 영수증을 출력하는 경우
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(대기번호/영수증 출력 다이얼로그) :: 대기번호와 영수증을 출력합니다.");
		
		} else {	// 영수증을 출력하지 않는 경우
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(대기번호/영수증 출력 다이얼로그) :: 대기번호를 출력합니다.");
			
		}

		// Dialog 속성
		this.setSize(480, 750);							// 다이얼로그 크기 (480px * 750px)
		this.setResizable(false);						// 다이얼로그 크기 변경 불가
		this.setUndecorated(true);						// 다이얼로그 타이틀바 제거
		this.setLocationRelativeTo(this.getParent());	// 다이얼로그를 부모 프레임의 중앙에 띄우기
		
		// 배경 패널
		backgroundPanel = new JPanel();															// 배경 패널 생성
		backgroundPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));						// 배경 패널의 레이아웃을 FlowLayout으로 지정하고 왼쪽 정렬에 상하좌우 여백을 10으로 설정
		backgroundPanel.setBackground(CustomColor.KIOSK_GRAY_242);								// 베경 패널의 배경색을 KIOSK_GRAY_242로 지정
		backgroundPanel.setBorder(new MatteBorder(2, 2, 2, 2, CustomColor.KIOSK_GRAY_217));		// 베경 패널의 테두리를 2px 두께의 KIOSK_GRAY_217로 지정
		this.add(backgroundPanel);																// 다이얼로그에 배경 패널 추가
		
		int height;		// 배경 패널 높이 계산을 위한 변수

     	// 닫기 버튼
     	closeButton = new CustomButton("닫기");					// 텍스트를 가지는 버튼 생성
     	closeButton.applyDialogCloseDesign();					// 다이얼로그 닫기 버튼을 의미하는 디자인 적용
     	closeButton.setPreferredSize(new Dimension(480, 30));	// 닫기 버튼 크기를 480px * 30px로 지정
     	closeButton.addActionListener(new CloseListener());		// 닫기 버튼 클릭 이벤트 처리를 위한 이벤트 리스너 등록
		this.add(closeButton, BorderLayout.NORTH);				// 닫기 버튼을 화면 상단에 배치
		
		// 대기번호용 텍스트 영역
		waitNoArea = new JTextArea(toWaitNoText());										// 대기번호 텍스트 영역에 들어갈 내용으로 대기번호용 텍스트 영역 내용 설정
		waitNoArea.setPreferredSize(new Dimension(280, waitNoTextLine * 19));			// 크기 지정
		waitNoArea.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_BLACK));	// KIOSK_BLACK의 실선으로 테두리 지정
		waitNoArea.setFont(FONT);														// 폰트 지정
		waitNoArea.setEditable(false);													// 내용 수정이 불가하도록 지정
		backgroundPanel.add(waitNoArea);												// 배경 패널에 대기번호용 텍스트 영역 추가
		
		height = 10 + (int)waitNoArea.getPreferredSize().getHeight() + 20;	// 배경 패널 높이를 의미하는 변수에 대기번호용 텍스트 영역 높이 저장
		
		// 영수증용 텍스트 영역
		if(print) {				// 영수증을 출력하는 경우
			receiptArea = new JTextArea(toReceiptText());									// 영수증용 텍스트 영역에 들어갈 내용으로 영수증용 텍스트 영역 내용 설정
			receiptArea.setPreferredSize(new Dimension(420, receiptTextLine * 19));			// 크기 지정
			receiptArea.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_BLACK));	// KIOSK_BLACK의 실선으로 테두리 지정
			receiptArea.setFont(FONT);														// 폰트 지정
			receiptArea.setEditable(false);													// 내용 수정이 불가하도록 지정
			backgroundPanel.add(receiptArea);												// 배경 패널에 영수증용 텍스트 영역 추가
			
			height += 10 + (int)receiptArea.getPreferredSize().getHeight();	// 배경 패널 높이를 의미하는 변수에 영수증용 텍스트 영역 높이 저장
		}
		
		backgroundPanel.setPreferredSize(new Dimension(490, height));	// 배경 패널 크기 지정
		
		this.add(backgroundPanel, BorderLayout.CENTER);		// 다이얼로그에 배경 패널을 추가하고, 화면 중앙에 배치
		
		// 스크롤 페인
		scrollPane = new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	// 배경 패널에 세로 스크롤 바만 제공하는 스크롤 페인 생성
		this.add(scrollPane);																											// 다이얼로그에 스크롤 페인 추가
		
		this.setVisible(true);	// 다이얼로그 보여주기
	}
	
	/* 메소드명: toWaitNoText
	 * 파라미터: 없음
	 * 반환값: String text (대기번호 내용)
	 * 기능 설명: 대기번호 영역에 들어갈 내용을 문자열로 생성하여 반환한다.
	 */
	public String toWaitNoText() {
		waitNoTextLine = 0;

		// 대기번호 내용
		String text = "[교환권]\n";
		text += UserModePanel.salesTotal.getOrderType() + "\n";
		text += toOrderDateAndTime(UserModePanel.salesTotal.getTransDt()) + "\n";
		text += "===========================\n";
		text += "대기번호: " + UserModePanel.salesTotal.getWaitNo() + "\n";
		text += "===========================\n";
		text += "메뉴명\t\t 수량\n";
		text += "--------------------------------------------\n";
		for(SalesDetail salesDetail : UserModePanel.salesDetails) {
			if(!salesDetail.getType().equals("M")) continue;
			
			text += String.format("%-20s", salesDetail.getName());
			text += String.format("        %2d", salesDetail.getQuantity()) + "\n";
		}
		text += "===========================\n\n";
		
		// 대기번호용 텍스트 영역의 높이값을 설정하기 위한 라인수 계산
		for(char c : text.toCharArray()) {
			if(c == '\n') waitNoTextLine++;
		}
		waitNoTextLine++;
		
		return text;
	}
	
	/* 메소드 설명: toReceiptText
	 * 파라미터: 없음
	 * 반환값: String text (영수증 내용)
	 * 기능 설명: 영수증 영역에 들어갈 내용을 문자열로 생성하여 반환한다.
	 */
	public String toReceiptText() {
		receiptTextLine = 0;
		
		// 영수증 내용
		String text = "[영수증]\n";
		text += UserModePanel.salesTotal.getOrderType() + "\n";
		text += toOrderDateAndTime(UserModePanel.salesTotal.getTransDt()) + "\n";
		text += "=========================================\n";
		text += "상호: " + STORE_NAME + "\n";
		text += "사업자번호: " + STORE_LICENSE_NO + "\n";
		text += "대표: " + CEO + "\n";
		text += "TEL: " + TEL + "\n";
		text += "주소: " + ADDRESS + "\n";
		text += "\n";
		text += "#" + UserModePanel.salesTotal.getOrderNo() + "\n";
		text += "=========================================\n";
		text += "메뉴명\t\t 단가   수량\t  금액\n";
		text += "--------------------------------------------------------------------\n";
		
		for(SalesDetail salesDetail : UserModePanel.salesDetails) {
			text += String.format("%-20s   ", salesDetail.getName());
			text += String.format("%,8d    %2d   %,8d", salesDetail.getPrice(), salesDetail.getQuantity(), salesDetail.getTotalPrice()) + "\n";
		}
		
		text += "=========================================\n";
		text += "합계금액\t\t\t" + String.format("%,8d", UserModePanel.salesTotal.getTotalAmt()) + "\n";
		text += "공급가액\t\t\t" + String.format("%,8d", UserModePanel.salesTotal.getSupplyAmt()) + "\n";
		text += "부가가치세\t\t\t" + String.format("%,8d", UserModePanel.salesTotal.getVat()) + "\n";
		text += "=========================================\n";
		text += "결제방법\t\t\t" + String.format("%s", UserModePanel.salesTotal.getPayMethod()) + "\n";
		
		if(UserModePanel.salesTotal.getTotalAmt() != 0) {
			text += "카드사명\t\t\t" + String.format("%s", UserModePanel.salesTotal.getCardName()) + "\n";
			text += "카드번호\t\t\t" + String.format("%s", UserModePanel.salesTotal.getCardNo()) + "\n";
			text += "할부개월\t\t\t" + String.format("%s", UserModePanel.salesTotal.getCardQuota()) + "\n";
			text += "승인번호\t\t\t" + String.format("%s", UserModePanel.salesTotal.getAuthCode()) + "\n";
		}
		text += "=========================================\n";
		
		if(!UserModePanel.salesTotal.getMemberId().equals(" ")) {
			text += "[포인트 적립]\n";
			text += "적립포인트\t\t\t" + String.format("%,8d", UserModePanel.salesTotal.getRewardPts()) + "\n";
			text += "누적포인트\t\t\t" + String.format("%,8d", UserModePanel.salesTotal.getTotalPts()) + "\n";
			text += "=========================================\n";
		}
		text += "\n\n";
		
		// 영수증용 텍스트 영역의 높이값을 설정하기 위한 라인수 계산
		for(char c : text.toCharArray()) {
			if(c == '\n') receiptTextLine++;
		}
		receiptTextLine++;
		
		return text;
	}
	
	/* 메소드명: toOrderDateAndTime
	 * 파라미터: String transDt (YYYYMMDDHHMMss 형식의 문자열: 통합 매출 내역에 저장된 거래 일시)
	 * 반환값: String (YYYY-MM-DD HH:MM:ss 형식 문자열)
	 * 기능 설명: YYYYMMDDHHMMss 형식의 문자열을 받아 YYYY-MM-DD HH:MM:ss 형식으로 변환하여 반환한다.
	 */
	public String toOrderDateAndTime(String transDt) {
		return transDt.substring(0, 4) + "-" + transDt.substring(4, 6) + "-" + transDt.substring(6, 8)
				+ " " + transDt.substring(8, 10) + ":" + transDt.substring(10, 12) + ":" + transDt.substring(12, 14);
	}
	
	/* 클래스명: CloseListener
	 * 설명: 닫기 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class CloseListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 닫기 버튼 클릭 이벤트 발생 시 주문 완료 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();	// 다이얼로그 창 종료
			
			UserModePanel.selectPrintRecieptPanel.setVisible(false);	// 영수증 출력 선택 패널 감추기
			UserModePanel.finishOrderPanel.setVisible(true);			// 주문 완료 패널 보여주기
			UserModePanel.finishOrderPanel.initialize();				// 주문 완료 패널 초기화 메소드 호출
		}
		
	}
}
