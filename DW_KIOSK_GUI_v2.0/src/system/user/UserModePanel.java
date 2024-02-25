package system.user;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import system.data.model.*;
import system.main.KioskFrame;
import system.main.Main;

public class UserModePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508704426316255936L; // 버전 ID

	public static HashMap<String, String> categories = new HashMap<String, String>();		// 카테고리 목록, {카테고리 ID : 카테고리명} 형식
	public static ArrayList<Menu> menus = new ArrayList<Menu>();							// 메뉴 목록
	public static ArrayList<Integer> firstMenuIndexOfCategories = new ArrayList<Integer>();	// 카테고리별 첫번째 메뉴의 인덱스 목록
	public static ArrayList<CartedMenu> cart = new ArrayList<CartedMenu>();					// 주문 목록
	
	public static SalesTotal salesTotal = new SalesTotal();								// 통합 매출 내역 객체
	public static ArrayList<SalesDetail> salesDetails = new ArrayList<SalesDetail>();	// 상세 매출 내역 객체 목록

	public static SelectHereOrTakeoutPanel selectHereOrTakeoutPanel;					// 매장 이용 방법 선택 패널
	public static MenuPanel menuPanel;													// 메뉴 패널
	public static SelectUsageOfPointOrCouponPanel selectUsageOfPointOrCouponPanel;		// 포인트/쿠폰 사용 여부 선택 패널
	public static UsePointOrCouponPanel usePointOrCouponPanel;							// 포인트/쿠폰 사용 패널
	public static OrderDetailsPanel orderDetailsPanel;									// 주문 세부 내역 패널
	public static SelectPayMethodPanel selectPayMethodPanel;							// 결제 수단 선택 패널
	public static PayCardOrSimplePanel payCardOrSimplePanel;							// 결제 요청 패널
	public static EarnPointPanel earnPointPanel;										// 포인트 적립 패널
	public static SelectPrintRecieptPanel selectPrintRecieptPanel;						// 영수증 출력 선택 패널
	public static FinishOrderPanel finishOrderPanel;									// 주문 완료 패널
	
	private static UserModePanel userModePanel = new UserModePanel(); 	// 싱글톤 객체 선언 및 생성
	
	/* 생성자: UserModePanel
	 * 파라미터: 없음
	 * 기능 설명: 사용자 모드 화면에서 보여질 패널을 생성한 후 추가한다.
	 */
	public UserModePanel() {
		this.setLayout(null);	// 패널의 레이아웃 매니저 제거
		
		// 매장 이용 방법 선택 패널
		selectHereOrTakeoutPanel = new SelectHereOrTakeoutPanel();	// 매장 이용 방법 선택 패널 생성
		this.add(selectHereOrTakeoutPanel);							// 매장 이용 방법 선택 패널 추가
		
		// 메뉴 패널
		menuPanel = new MenuPanel();	// 메뉴 패널 생성
		menuPanel.setVisible(false);	// 메뉴 패널 감추기
		this.add(menuPanel);			// 메뉴 패널 추가
		
		// 포인트/쿠폰 사용 여부 선택 패널
		selectUsageOfPointOrCouponPanel = new SelectUsageOfPointOrCouponPanel();	// 포인트/쿠폰 사용 여부 선택 패널 생성
		selectUsageOfPointOrCouponPanel.setVisible(false);							// 포인트/쿠폰 사용 여부 선택 패널 감추기
		this.add(selectUsageOfPointOrCouponPanel);									// 포인트/쿠폰 사용 여부 선택 패널 추가
		
		// 포인트/쿠폰 사용 패널
		usePointOrCouponPanel = new UsePointOrCouponPanel();	// 포인트/쿠폰 사용 패널 생성
		usePointOrCouponPanel.setVisible(false);				// 포인트/쿠폰 사용 패널 감추기
		this.add(usePointOrCouponPanel);						// 포인트/쿠폰 사용 패널 추가
		
		// 주문 세부 내역 패널
		orderDetailsPanel = new OrderDetailsPanel();	// 주문 세부 내역 패널 생성
		orderDetailsPanel.setVisible(false);			// 주문 세부 내역 패널 감추기
		this.add(orderDetailsPanel);					// 주문 세부 내역 패널 추가
		
		// 결제 수단 선택 패널
		selectPayMethodPanel = new SelectPayMethodPanel();	// 결제 수단 선택 패널 생성
		selectPayMethodPanel.setVisible(false);				// 결제 수단 선택 패널 감추기
		this.add(selectPayMethodPanel);						// 결제 수단 선택 패널 추가
		
		// 결제 요청 패널
		payCardOrSimplePanel = new PayCardOrSimplePanel();	// 결제 요청 패널 생성
		payCardOrSimplePanel.setVisible(false);				// 결제 요청 패널 감추기
		this.add(payCardOrSimplePanel);						// 결제 요청 패널 추가
		
		// 포인트 적립 패널
		earnPointPanel = new EarnPointPanel();		// 포인트 적립 패널 생성
		earnPointPanel.setVisible(false);			// 포인트 적립 패널 감추기
		this.add(earnPointPanel);					// 포인트 적립 패널 추가
		
		// 영수증 출력 여부 선택 패널
		selectPrintRecieptPanel = new SelectPrintRecieptPanel();	// 영수증 출력 선택 패널 생성
		selectPrintRecieptPanel.setVisible(false);					// 영수증 출력 선택 패널 감추기
		this.add(selectPrintRecieptPanel);							// 영수증 출력 선택 패널 추가
		
		// 주문 완료 패널
		finishOrderPanel = new FinishOrderPanel();	// 주문 완료 패널 생성
		finishOrderPanel.setVisible(false);			// 주문 완료 패널 감추기
		this.add(finishOrderPanel);					// 주문 완료 패널 추가
	}
	
	/* 메소드명: getInstance
	 * 파라미터: 없음
	 * 반환값: UserModePanel userModePanel (UserModePanel의 싱글톤 객체)
	 * 기능 설명: 멤버 필드로 선언 및 생성된 UserModePanel 객체를 반환한다.
	 */
	public static UserModePanel getInstance() {
		return userModePanel;
	}
	
	/* 메소드명: initialize
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 사용자 모드에서 진행한 모든 내용을 초기화(삭제)하고 메인을 보여준다.
	 */
	public static void initialize() {
		categories.clear();					// 카테고리 목록 초기화
		menus.clear();						// 메뉴 목록 초기화
		firstMenuIndexOfCategories.clear();	// 카테고리별 첫번째 메뉴의 인덱스 목록 초기화
		cart.clear();						// 주문 목록 초기화
		CartedMenu.initialize();			// CartedMenuDTO의 static 변수 초기화
		salesTotal.initialize();			// 통합 매출 내역 객체 초기화
		salesDetails.clear();				// 상세 매출 내역 객체 목록 초기화

		selectHereOrTakeoutPanel.setVisible(true);			// 매장 이용 방법 선택 패널 보여주기
		menuPanel.setVisible(false);						// 메뉴 패널 감추기
		selectUsageOfPointOrCouponPanel.setVisible(false);	// 포인트/쿠폰 사용 여부 선택 패널 감추기
		usePointOrCouponPanel.setVisible(false);			// 포인트/쿠폰 사용 패널 감추기
		orderDetailsPanel.setVisible(false);				// 주문 세부 내역 패널 감추기
		selectPayMethodPanel.setVisible(false);				// 결제 수단 선택 패널 감추기
		payCardOrSimplePanel.setVisible(false);				// 결제 요청 패널 감추기
		earnPointPanel.setVisible(false);					// 포인트 적립 패널 감추기
		selectPrintRecieptPanel.setVisible(false);			// 영수증 출력 선택 패널 감추기
		finishOrderPanel.setVisible(false);					// 주문 완료 패널 감추기
		userModePanel.setVisible(false);					// 사용자 모드 패널 감추기
		
		KioskFrame.mainPanel.setVisible(true);	// 메인 패널 보여주기
		Main.currentMode = 'M';					// 실행 모드를 메인으로 변경
	}
}
