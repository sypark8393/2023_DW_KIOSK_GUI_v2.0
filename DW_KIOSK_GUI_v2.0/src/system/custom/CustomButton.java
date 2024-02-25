package system.custom;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class CustomButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5270781513887458190L; // 버전 ID
	
	/* 생성자: CustomButton
	 * 파라미터: 없음
	 * 기능 설명: 내용을 가지지 않는 빈 버튼을 생성하고, 테두리와 포커스 속성을 설정한다.
	 */
	public CustomButton() {
		super();						// 부모 클래스(JButton)의 생성자 호출
		this.setBorder(null);			// 버튼의 테두리를 설정하지 않음
		this.setFocusPainted(false);	// 버튼의 포커스를 표시하지 않음
	}
	
	/* 생성자: CustomButton
	 * 파라미터: String text (버튼에 들어갈 내용)
	 * 기능 설명: 파라미터로 전달받은 문자열을 내용을 가지는 버튼을 생성하고, 테두리와 포커스 속성을 설정한다.
	 */
	public CustomButton(String text) {
		super(text);					// 부모 클래스(JButton)의 생성자 호출
		this.setBorder(null);			// 버튼의 테두리를 설정하지 않음
		this.setFocusPainted(false);	// 버튼의 포커스를 표시하지 않음
	}
	
	/* 생성자: CustomButton
	 * 파라미터: String text (버튼에 들어갈 내용), Icon icon (버튼에 들어갈 아이콘)
	 * 기능 설명: 파라미터로 전달받은 문자열과 아이콘을 가지는 버튼을 생성하고, 테두리와 포커스 속성을 설정한다.
	 */
	public CustomButton(String text, Icon icon) {
		super(text, icon);				// 부모 클래스(JButton)의 생성자 호출
		this.setBorder(null);			// 버튼의 테두리를 설정하지 않음
		this.setFocusPainted(false);	// 버튼의 포커스를 표시하지 않음
	}
	
	/* 생성자: CustomButton
	 * 파라미터: Icon icon (버튼에 들어갈 아이콘)
	 * 기능 설명: 파라미터로 전달받은 아이콘을 가지는 버튼을 생성하고, 테투리와 포커스 속성을 설정한다.
	 */
	public CustomButton(Icon icon) {
		super(icon);					// 부모 클래스(JButton)의 생성자 호출
		this.setBorder(null);			// 버튼의 테두리를 설정하지 않음
		this.setFocusPainted(false);	// 버튼의 포커스를 표시하지 않음
	}
	
	
	// 패널
	/* 메소드명: applySelectOneDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 2개 이상의 선택지 중 하나를 선택해야 하는 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applySelectOneDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 40)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 40)
		
		// 배경색, 전경색
		this.setBackground(Color.white);				// 버튼의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 버튼의 전경색을 KIOSK_BLACK으로 지정
		
		// 테두리
		this.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_GRAY_217));	// 버튼의 테두리를 KIOSK_GRAY_217의 실선으로 설정
		
		// 텍스트 위치
		this.setVerticalTextPosition(JButton.BOTTOM);	// 수직에서의 텍스트 위치는 버튼 하단
		this.setHorizontalTextPosition(JButton.CENTER);	// 수평에서의 텍스트 위치는 버튼 중앙
		
		// 위치, 크기
		this.setBounds(x, y, 220, 260);	// 버튼을 (x, y)에 위치시키고 크기는 220px * 260px로 설정
	}
	
	/* 메소드명: applyCancelDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 취소 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyCancelDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 160, 60);	// 버튼을 (x, y)에 위치시키고 크기는 160px * 60px로 설정
	}
	
	/* 메소드명: applyCancelOnCenterDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 화면 중앙에 위치한 취소 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyCancelOnCenterDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 220, 60);	// 버튼을 (x, y)에 위치시키고 크기는 220px * 60px로 설정
	}
	
	/* 메소드명: applyNumberPadDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 숫자 키패드의 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyNumberPadDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 40)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 40)
		
		// 배경색, 전경색
		this.setBackground(Color.white);				// 버튼의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 버튼의 전경색을 KIOSK_BLACK으로 지정
		
		// 테두리
		this.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_GRAY_217));	// 버튼의 테두리를 KIOSK_GRAY_217의 실선으로 설정
				
		// 위치, 크기
		this.setBounds(x, y, 150, 110);	// 버튼을 (x, y)에 위치시키고 크기는 150px * 110px로 설정
	}
	
	
	/* 메소드명: applyStartDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 시작 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyStartDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 40)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 40)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.WHITE);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 720, 300);	// 버튼을 (x, y)에 위치시키고 크기는 720px * 300px로 설정
	}
	
	
	/* 메소드명: applyMainDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 메인 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyMainDesign(int x, int y) {
		// 배경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 40, 40);	// 버튼을 (x, y)에 위치시키고 크기는 40px * 40px로 설정
	}
	
	/* 메소드명: applyMoveCategoryPageDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 카테고리 페이지 이동 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyMoveCategoryPageDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 20, 30);	// 버튼을 (x, y)에 위치시키고 크기는 20px * 30px로 설정
	}
	
	/* 메소드명: applyCategoryDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: (선택되지 않은 상태의) 카테고리 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyCategoryDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 20)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 120, 40);	// 버튼을 (x, y)에 위치시키고 크기는 120px * 40px로 설정
	}
	
	/* 메소드명: applyMoveMenuPageDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 메뉴 페이지 이동 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyMoveMenuPageDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 20, 40);	// 버튼을 (x, y)에 위치시키고 크기는 20px * 40px로 설정
	}
	
	/* 메소드명: applyMenuDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 메뉴 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyMenuDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 14)); // 버튼의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 14)
		
		// 배경색, 전경색
		this.setBackground(Color.white);				// 버튼의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 버튼의 전경색을 KIOSK_BLACK으로 지정
		
		// 테두리
		this.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_GRAY_217));	// 버튼의 테두리를 KIOSK_GRAY_217의 실선으로 설정
		
		// 텍스트 위치
		this.setVerticalTextPosition(JButton.BOTTOM);	// 수직에서의 텍스트 위치는 버튼 하단
		this.setHorizontalTextPosition(JButton.CENTER);	// 수평에서의 텍스트 위치는 버튼 중앙
		
		// 위치, 크기
		this.setBounds(x, y, 130, 160);	// 버튼을 (x, y)에 위치시키고 크기는 130px * 160px로 설정
	}
	
	/* 메소드명: applyClearCartDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 주문 목록 전체 삭제 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyClearCartDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 20)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 80, 100);	// 버튼을 (x, y)에 위치시키고 크기는 80px * 100px로 설정
	}
	
	/* 메소드명: applyPayWithAmountInfoDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 금액 정보가 함께 표시되는 결제 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyPayWithAmountInfoDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 20)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 160, 60);	// 버튼을 (x, y)에 위치시키고 크기는 160px * 60px로 설정
	}
	
	/* 메소드명: applyDeleteDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 주문 목록 아이템 삭제 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyDeleteCartItemDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 20)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 30, 30);	// 버튼을 (x, y)에 위치시키고 크기는 30px * 30px로 설정
	}
	
	/* 메소드명: applyDecreaseQuantityDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 수량 감소 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyDecreaseQuantityDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 20)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 30, 30);	// 버튼을 (x, y)에 위치시키고 크기는 30px * 30px로 설정
	}
	
	/* 메소드명: applyIncreaseQuantityDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 수량 증가 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyIncreaseQuantityDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 20)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 20)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 30, 30);	// 버튼을 (x, y)에 위치시키고 크기는 30px * 30px로 설정
	}
	
	/* 메소드명: applyScrollInMenuDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 주문 목록 view 스크롤 이동 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyScrollInMenuDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 15)); // 버튼의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 15) (*강조용 폰트에서 기호 지원을 안해서 텍스트용 폰트 사용)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GRAY_217);	// 버튼의 배경색을 KIOSK_GRAY_217으로 지정
		this.setForeground(CustomColor.KIOSK_GREEN);	// 버튼의 전경색을 KIOSK_GREEN으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 20, 20);	// 버튼을 (x, y)에 위치시키고 크기는 20px * 20px로 설정
	}
	
	/* 메소드명: changePayDesignByEnabledState
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 결제 버튼의 활성화 여부에 따라 디자인을 변경한다.
	 */
	public void changePayDesignByEnabledState() {
		// 버튼이 활성화 상태이면
		if(this.isEnabled()) {
			this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
			
		} else { // 버튼이 비활성화 상태이면
			this.setBackground(CustomColor.KIOSK_GRAY_217);	// 버튼의 배경색을 KIOSK_GRAY_217으로 지정
		}
	}
	
	/* 메소드명: changeSelectedCategoryDesign
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 선택된 상태의 카테고리 버튼을 의미하는 디자인을 적용한다.
	 */
	public void changeSelectedCategoryDesign() {
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GRAY_242);	// 버튼의 배경색을 KIOSK_GRAY_242으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 버튼의 전경색을 KIOSK_BLACK으로 지정
	}
	
	/* 메소드명: changeUnselectedCategoryDesign
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 선택되지 않은 상태의 카테고리 버튼을 의미하는 디자인을 적용한다.
	 */
	public void changeUnselectedCategoryDesign() {
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
	}
	
	/* 메소드명: changeIncreaseQuantityDesignByEnabledState
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 수량 증가 버튼의 활성화 여부에 따라 디자인을 변경한다.
	 */
	public void changeIncreaseQuantityDesignByEnabledState() {
		// 버튼이 활성화 상태이면
		if(this.isEnabled()) {
			this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
			
		} else { // 버튼이 비활성화 상태이면
			this.setBackground(CustomColor.KIOSK_GRAY_217);	// 버튼의 배경색을 KIOSK_GRAY_217으로 지정
		}
	}
	
	/* 메소드명: changeClearCartDesignByEnabledState
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 주문 목록 전체 삭제 버튼의 활성화 여부에 따라 디자인을 변경한다.
	 */
	public void changeClearCartDesignByEnabledState() {
			// 버튼이 활성화 상태이면
			if(this.isEnabled()) {
				this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
				
			} else { // 버튼이 비활성화 상태이면
				this.setBackground(CustomColor.KIOSK_GRAY_217);	// 버튼의 배경색을 KIOSK_GRAY_217으로 지정
			}
		}
	
	
	/* 메소드명: applyNotUsePointAndCouponDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 포인트/쿠폰 미사용 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyNotUsePointAndCouponDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
				
		// 위치, 크기
		this.setBounds(x, y, 160, 60);	// 버튼을 (x, y)에 위치시키고 크기는 160px * 60px로 설정
	}
	
	
	/* 메소드명: applyScrollInOrderDetailsDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: OrderDetails 패널에 있는 주문 목록 view 스크롤 이동 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyScrollInOrderDetailsDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_TEXT, Font.PLAIN, 15)); // 버튼의 폰트 지정(텍스트용 폰트, 스타일 없음, 폰트 크기: 15) (*강조용 폰트에서 기호 지원을 안해서 텍스트용 폰트 사용)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GRAY_217);	// 버튼의 배경색을 KIOSK_GRAY_217으로 지정
		this.setForeground(CustomColor.KIOSK_GREEN);	// 버튼의 전경색을 KIOSK_GREEN으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 40, 40);	// 버튼을 (x, y)에 위치시키고 크기는 40px * 40px로 설정
	}
	
	/* 메소드명: applyPayDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 결제 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyPayDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 160, 60);	// 버튼을 (x, y)에 위치시키고 크기는 160px * 60px로 설정
	}
	
	
	/* 메소드명: applySelectPayMethodDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 결제 수단 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applySelectPayMethodDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(Color.white);				// 버튼의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 버튼의 전경색을 KIOSK_BLACK으로 지정
		
		// 테두리
		this.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_GRAY_217));	// 버튼의 테두리를 KIOSK_GRAY_217의 실선으로 설정
		
		// 텍스트 위치
		this.setVerticalTextPosition(JButton.BOTTOM);	// 수직에서의 텍스트 위치는 버튼 하단
		this.setHorizontalTextPosition(JButton.CENTER);	// 수평에서의 텍스트 위치는 버튼 중앙
		
		// 위치, 크기
		this.setBounds(x, y, 160, 120);	// 버튼을 (x, y)에 위치시키고 크기는 160px * 120px로 설정
	}
	
	
	/* 메소드명: applyRequestPayDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 승인 요청 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyRequestPayDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 220, 60);	// 버튼을 (x, y)에 위치시키고 크기는 220px * 60px로 설정
	}
		
	
	/* 메소드명: applyNotEarnPointDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 포인트 미적립 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyNotEarnPointDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
				
		// 위치, 크기
		this.setBounds(x, y, 160, 60);	// 버튼을 (x, y)에 위치시키고 크기는 160px * 60px로 설정
	}
	
	
	/* 메소드명: applyNotPrintDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: (영수증) 미출력 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyNotPrintDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 220, 60);	// 버튼을 (x, y)에 위치시키고 크기는 220px * 60px로 설정
	}
	
	/* 메소드명: applyPrintDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: (영수증) 미출력 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyPrintDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 25)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 25)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 220, 60);	// 버튼을 (x, y)에 위치시키고 크기는 220px * 60px로 설정
	}
	
	
	// 다이얼로그
	/* 메소드명: applyDialogConfirmDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 다이얼로그 내 확인 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyDialogConfirmDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 17)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 17)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_GREEN);	// 버튼의 배경색을 KIOSK_GREEN으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 100, 35);	// 버튼을 (x, y)에 위치시키고 크기는 100px * 35px로 설정
	}
	
	/* 메소드명: applyDialogCancelDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 다이얼로그 내 취소 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyDialogCancelDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 17)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 17)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
		
		// 위치, 크기
		this.setBounds(x, y, 100, 35);	// 버튼을 (x, y)에 위치시키고 크기는 100px * 35px로 설정
	}

	/* 메소드명: applyDialogNumberPadDesign
	 * 파라미터: int x, int y (버튼이 위치할 x, y 좌표값)
	 * 반환값: 없음
	 * 기능 설명: 다이얼로그 내 숫자 키패드의 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyDialogNumberPadDesign(int x, int y) {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 17)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 17)
		
		// 배경색, 전경색
		this.setBackground(Color.white);				// 버튼의 배경색을 흰색으로 지정
		this.setForeground(CustomColor.KIOSK_BLACK);	// 버튼의 전경색을 KIOSK_BLACK으로 지정
		
		// 테두리
		this.setBorder(BorderFactory.createLineBorder(CustomColor.KIOSK_GRAY_217));	// 버튼의 테두리를 KIOSK_GRAY_217의 실선으로 설정
				
		// 위치, 크기
		this.setBounds(x, y, 80, 70);	// 버튼을 (x, y)에 위치시키고 크기는 80px * 70px로 설정
	}

	
	/* 메소드명: applyCloseDesign
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 다이얼로그 닫기 버튼을 의미하는 디자인을 적용한다.
	 */
	public void applyDialogCloseDesign() {
		// 폰트
		this.setFont(new Font(CustomFont.FONT_HIGHLIGHT, Font.PLAIN, 18)); // 버튼의 폰트 지정(강조용 폰트, 스타일 없음, 폰트 크기: 18)
		
		// 배경색, 전경색
		this.setBackground(CustomColor.KIOSK_BLACK);	// 버튼의 배경색을 KIOSK_BLACK으로 지정
		this.setForeground(Color.white);				// 버튼의 전경색을 흰색으로 지정
	}
}
