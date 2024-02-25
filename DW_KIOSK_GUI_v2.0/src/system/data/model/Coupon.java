package system.data.model;

public class Coupon {
	public static final String FILE_NAME = "src/files/data/coupon.txt";				// 쿠폰 파일 경로
	public static final String TMP_FILE_NAME = "src/files/data/coupon_tmp.txt";		// 수정된 쿠폰 파일 임시로 저장하는 파일 경로
	
	public static final int INDEX_COUPON_ID = 0;	// 쿠폰 ID
	public static final int INDEX_PRICE = 1;		// 쿠폰 금액
	public static final int INDEX_USED = 2;			// 사용 여부
	
	private String id;		// 쿠폰 ID (C + 회원 ID(휴대전화 번호 11자리) + 발급 번호 3자리)
	private int price;		// 쿠폰 금액
	private String used;	// 사용 여부
	
	// 생성자
	public Coupon(String id, int price, String used) {
		this.id = id;
		this.price = price;
		this.used = used;
	}

	// getter & setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}
	
	// 메소드
	/* 메소드명: printAdminCoupon
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 관리자 모드에서 쿠폰 목록을 양식에 맞게 출력한다. 
	 */
	public void printAdminCoupon() {
		System.out.print("      ");
		System.out.printf("%12s", this.id);
		System.out.print("\t\t");
		System.out.printf("%,5d", this.price);
		System.out.print("\t\t");
		System.out.printf("%s", this.used);
		System.out.println();
	}
	
}
