package system.data.model;

public class SalesDetail {
	public static final String FILE_DIRECTORY = "src/files/sales_data/";	// 상세 매출 내역 파일 경로
	
	public static final int INDEX_ORDER_NO = 0;		// 주문번호
	public static final int INDEX_TYPE = 1;			// 상품 유형
	public static final int INDEX_ID = 2;			// 상품 ID
	public static final int INDEX_NAME = 3;			// 상품 내용
	public static final int INDEX_PRICE = 4;		// 상품 단가
	public static final int INDEX_QUANTITY = 5;		// 주문 수량
	public static final int INDEX_TOTAL_PRICE = 6;	// 금액
	
	private String fileName = "sales_detail";		//파일명
	
	private String orderNo = " ";	// 주문 번호
	private String type = " ";		// 상품 유형 (M(메뉴)/C(쿠폰)/P(포인트))
	private String id = " ";		// 상품 ID
	private String name = " ";		// 상품 내용
	private int price = 0;			// 상품 단가
	private int quantity = 0;		// 주문 수량
	private int totalPrice = 0;		// 금액 (단가 * 주문 수량)
	
	// 생성자
	public SalesDetail(String orderNo, String type, String id, String name, int price, int quantity) {
		this.orderNo = orderNo;
		this.type = type;
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		totalPrice = price * quantity;
		
		fileName = FILE_DIRECTORY + this.fileName + "(" + this.orderNo.substring(0, 8) + ").txt";	// 파일명 설정
	}

	// getter
	public String getFileName() {
		return fileName;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public String getType() {
		return type;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
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

	/* 메소드명: toDataTemplate
	 * 파라미터: 없음
	 * 반환값: String str (상세 매출 내역 파일에 작성되는 형식으로 구성된 문자열)
	 * 기능 설명: 상세 매출 내역 객체에 저장된 정보를 파일에 작성되는 형식의 문자열로 구성하여 반환한다.
	 */
	public String toDataTemplate() {
		String str = "";
		str += orderNo + "|" + type + "|" + id + "|" + name + "|";
		str += price + "|" + quantity + "|" + totalPrice;
		
		return str;
	}
}
