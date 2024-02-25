package system.data.model;

public class CartedMenu {
	private static int TOTAL_QUANTITY = 0;	// 현재 주문 목록에 있는 상품의 총 수량
	private static int TOTAL_AMT = 0;		// 현재 주문 목록에 있는 상품의 총 금액
	
	private int menuIndex;			// 메뉴 리스트에서의 인덱스 번호
	private String categoryId;		// 카테고리 ID
	private String menuNo;			// 메뉴 순번
	private String menuName;		// 메뉴명
	private int price;				// 가격
	private int quantity;			// 수량
	private int totalPrice;			// 금액
	
	/* 생성자: CartedMenuDTO
	 * 파라미터: int menuIndex(메뉴 리스트에서의 인덱스 번호), MenuDTO menuDto (메뉴 정보를 가지고 있는 MenuDTO 객체), int quantity (수량)
	 * 기능 설명: MenuDTO 객체에 저장된 정보와 수량 정보를 이용하여 멤버 필드의 값을 초기화한다.
	 */
	public CartedMenu(int menuIndex, Menu menuDto, int quantity) {
		this.menuIndex = menuIndex;					// 메뉴 리스트에서의 인덱스 번호 
		
		this.categoryId = menuDto.getCategoryId();	// 카테고리 ID 초기화
		this.menuNo = menuDto.getMenuNo();			// 메뉴 순번 초기화
		this.menuName = menuDto.getName();			// 메뉴명 초기화
		this.price = menuDto.getPrice();			// 가격 초기화
		this.quantity = quantity;					// 수량 초기화
		
		this.totalPrice = price * quantity;			// 금액 = 가격 * 수량
		
		TOTAL_QUANTITY += quantity;					// 주문할 수량만큼 총 주문 수량 증가
		TOTAL_AMT += totalPrice;					// 주문할 메뉴의 금액만큼 총 금액 증가
	}
	
	// getter & setter
	public static int getTOTAL_QUANTITY() {
		return TOTAL_QUANTITY;
	}

	public static int getTOTAL_AMT() {
		return TOTAL_AMT;
	}
	
	public int getMenuIndex() {
		return menuIndex;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public String getMenuName() {
		return menuName;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	/* 메소드명: updateQuantity
	 * 파라미터: int defference (수량 변경량)
	 * 반환값: 없음
	 * 기능 설명: 수량 변경량만큼 수량, 금액, 총 수량, 총 금액을 업데이트한다.
	 */
	public void updateQuantity(int defference) {
		this.quantity += defference;				// 수량 정보 업데이트
		this.totalPrice += defference * price;		// 금액 정보 업데이트
		
		TOTAL_QUANTITY += defference;				// 총 수량 정보 업데이트
		TOTAL_AMT += defference * price;			// 총 금액 정보 업데이트
	}
	
	/* 메소드명: initialize
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 총 수량과 총 금액을 0으로 초기화한다.
	 */
	public static void initialize() {
		TOTAL_QUANTITY = 0;		// 총 수량 0으로 초기화
		TOTAL_AMT = 0;			// 총 금액 0으로 초기화
	}
}
