package system.custom;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;

public class CustomLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674082877444729754L; // 버전 ID
	
	/* 생성자: CustomLabel
	 * 파라미터: 없음
	 * 기능 설명: 내용을 가지지 않는 빈 레이블을 생성하고 전경색을 지정한다.
	 */
	public CustomLabel() {
		super();	// 부모 클래스(JLabel)의 생성자 호출
		
		// 전경색
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
	}
	
	/* 생성자: CustomLabel
	 * 파라미터: String text (레이블에 들어갈 내용)
	 * 기능 설명: 파라미터로 전달받은 문자열을 가지는 레이블을 생성하고 전경색을 지정한다.
	 */
	public CustomLabel(String text) {
		super(text);	// 부모 클래스(JLabel)의 생성자 호출
		
		// 전경색
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
	}
	
	/* 생성자: CustomLabel
	 * 파라미터: Icon icon (레이블에 들어갈 아이콘)
	 * 기능 설명: 파라미터로 전달받은 아이콘을 가지는 레이블을 생성하고 전경색을 지정한다.
	 */
	public CustomLabel(Icon icon) {
		super(icon);	// 부모 클래스(JLabel)의 생성자 호출

		// 전경색
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
	}
	
	/* 생성자: CustomLabel
	 * 파라미터:String text (레이블에 들어갈 내용), int horizontalAlignment(텍스트 정렬 기준)
	 * 기능 설명: 파라미터로 전달받은 문자열을 가지고 정렬 방법이 지정된 레이블을 생성한다. (전경색을 함께 지정한다.)
	 */
	public CustomLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);	// 부모 클래스(JLabel)의 생성자 호출
		
		// 전경색
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
	}
		
	/* 생성자: CustomLabel
	 * 파라미터:String text (레이블에 들어갈 내용), int horizontalAlignment(텍스트 정렬 기준), float fontSize(글자 크기)
	 * 기능 설명: 파라미터로 전달받은 문자열을 가지고 정렬 방법과 폰트 크기가 지정된 레이블을 생성한다. (전경색을 함께 지정한다.)
	 */
	public CustomLabel(String text, int horizontalAlignment, float fontSize) {
		super(text, horizontalAlignment);	// 부모 클래스(JLabel)의 생성자 호출
		
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, (int)fontSize));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 파라미터로 전달받은 값)
		
		// 전경색
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
	}
	
	
	// 패널
	/* 메소드명: applyGuideMessageDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 안내 메시지 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyGuideMessageDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 25));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setOpaque(true);							// 레이블의 배경색을 표시하기 위해 불투명하게 설정
		this.setBackground(CustomColor.KIOSK_YELLOW);	// 레이블의 배경색을 KIOSK_YELLOW로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 720, 80);	// 레이블을 (x, y)에 위치시키고 크기는 720px * 80px로 설정
	}
	
	/* 메소드명: changeFontToHighLight
	 * 파라미터: int fontSize(글자 크기)
	 * 반환값: 없음
	 * 기능 설명: 레이블의 폰트를 강조용 폰트로 변경하고 폰트 크기를 지정한다.
	 */
	public void changeFontToHighLight(int fontSize) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, fontSize));
	}
	
	
	/* 메소드명: applyTotalQuantityDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 총 주문 수량 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyTotalQuantityDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 20));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setOpaque(true);							// 레이블의 배경색을 표시하기 위해 불투명하게 설정
		this.setBackground(Color.white);				// 레이블의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 160, 60);	// 레이블을 (x, y)에 위치시키고 크기는 160px * 60px로 설정
	}
	
	/* 메소드명: applyMenuNameInMenuDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: MenuPanel에 있는 메뉴명 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyMenuNameInMenuDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 15));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 15)

		// 위치, 크기
		this.setBounds(x, y, 220, 30);	// 레이블을 (x, y)에 위치시키고 크기는 220px * 30px로 설정
	}
	
	/* 메소드명: applyQuantityInMenuDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: MenuPanel에 있는 수량 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyQuantityInMenuDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 15));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 15)

		// 위치, 크기
		this.setBounds(x, y, 30, 30);	// 레이블을 (x, y)에 위치시키고 크기는 30px * 30px로 설정
	}
	
	/* 메소드명: applyTotalPriceInMenuDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: MenuPanel에 있는 금액 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyTotalPriceInMenuDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.RIGHT);						// 텍스트 오른쪽 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 15));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 15)

		// 위치, 크기
		this.setBounds(x, y, 90, 30);	// 레이블을 (x, y)에 위치시키고 크기는 90px * 30px로 설정
	}
	
	
	/* 메소드명: applyInputIdDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: ID 입력 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyInputIdDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);							// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 40));	// 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 40)
		
		// 배경색, 전경색
		this.setOpaque(true);							// 레이블의 배경색을 표시하기 위해 불투명하게 설정
		this.setBackground(CustomColor.KIOSK_YELLOW);	// 레이블의 배경색을 KIOSK_YELLOW로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 440, 70);	// 레이블을 (x, y)에 위치시키고 크기는 440px * 70px로 설정
	}
	
	/* 메소드명: applyInputIdExampleDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: ID 입력 예시 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyInputIdExampleDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 20));	// 버튼의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 전경색
		this.setForeground(CustomColor.KIOSK_GRAY_120);	// 레이블의 전경색을 KIOSK_GRAY_120으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 240, 20);	// 레이블을 (x, y)에 위치시키고 크기는 240px * 20px로 설정
	}
	
	/* 메소드명: applyInvalidReasonDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 사용 불가 이유 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyInvalidReasonDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 25));	// 버튼의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 전경색
		this.setForeground(CustomColor.KIOSK_RED);	// 레이블의 전경색을 KIOSK_RED로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 450, 60);	// 레이블을 (x, y)에 위치시키고 크기는 450px * 60px로 설정
	}


	/* 메소드명: applyMenuNameInOrderDetailsDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: OrderDetailsPanel에 있는 메뉴명 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyMenuNameInOrderDetailsDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 17));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 17)
		
		// 테두리
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CustomColor.KIOSK_GRAY_242));	// 레이블의 하단 테두리를 KIOSK_GRAY_242의 실선으로 설정
		
		// 위치, 크기
		this.setBounds(x, y, 360, 40);	// 레이블을 (x, y)에 위치시키고 크기는 360px * 40px로 설정
	}
	
	/* 메소드명: applyQuantityInOrderDetailsDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: OrderDetailsPanel에 있는 수량 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyQuantityInOrderDetailsDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 17));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 17)
		
		// 테두리
		this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, CustomColor.KIOSK_GRAY_242));	// 레이블의 좌, 우, 하단 테두리를 KIOSK_GRAY_242의 실선으로 설정
		
		// 위치, 크기
		this.setBounds(x, y, 90, 40);	// 레이블을 (x, y)에 위치시키고 크기는 90px * 40px로 설정
	}
	
	/* 메소드명: applyTotalInOrderDetailsDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: OrderDetailsPanel에 있는 금액 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyTotalInOrderDetailsDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.RIGHT);						// 텍스트 오른쪽 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 17));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 17)
		
		// 테두리
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CustomColor.KIOSK_GRAY_242));	// 레이블의 하단 테두리를 KIOSK_GRAY_242의 실선으로 설정
		
		// 위치, 크기
		this.setBounds(x, y, 130, 40);	// 레이블을 (x, y)에 위치시키고 크기는 130px * 40px로 설정
	}
	

	// 다이얼로그
	/* 메소드명: applyAlertMessageDesign
	 * 파라미터: int x, int y (레이블이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 알림 메시지 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyAlertMessageDesign(int x, int y) {
		// 폰트
		this.setHorizontalAlignment(JLabel.CENTER);						// 텍스트 가운데 정렬
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 25));	// 레이블의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setOpaque(true);							// 레이블의 배경색을 표시하기 위해 불투명하게 설정
		this.setBackground(CustomColor.KIOSK_YELLOW);	// 레이블의 배경색을 KIOSK_YELLOW로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 516, 48);	// 레이블을 (x, y)에 위치시키고 크기는 516px * 48px로 설정
	}

	/* 메소드명: applyDialogInputDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 다이얼로그 내 입력 레이블을 의미하는 디자인을 적용한다.
	 */
	public void applyDialogInputDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 18));	// 버튼의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 18)

		// 배경색, 전경색
		this.setOpaque(true);							// 레이블의 배경색을 표시하기 위해 불투명하게 설정
		this.setBackground(Color.white);				// 레이블의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 레이블의 전경색을 KIOSK_BLACK로 지정
		
		// 테두리
		this.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_GRAY_120));	// 버튼의 테두리를 KIOSK_GRAY_120의 실선으로 설정
		
		// 위치, 크기
		this.setBounds(x, y, 480, 40);	// 레이블을 (x, y)에 위치시키고 크기는 480px * 40px로 설정
	}
	
}
