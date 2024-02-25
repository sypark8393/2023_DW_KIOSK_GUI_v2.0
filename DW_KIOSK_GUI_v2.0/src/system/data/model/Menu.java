package system.data.model;

public class Menu {
	
	public static final String FILE_NAME = "src/files/data/menu.txt";			// 메뉴 파일 경로
	public static final String TMP_FILE_NAME = "src/files/data/menu_tmp.txt";	// 수정된 메뉴 파일을 임시로 저장하는 파일 경로
	
	public static final int INDEX_CATEGORY_ID = 0;	// 카테고리 ID
	public static final int INDEX_MENU_NO = 1;	  	// 메뉴 순번
	public static final int INDEX_NAME = 2;		  	// 메뉴명
	public static final int INDEX_PRICE = 3;	  	// 가격
	public static final int INDEX_STOCK = 4;	  	// 재고
	public static final int INDEX_ENABLE = 5;		// 활성화 여부
	
	private String categoryId;	// 카테고리 ID
	private String menuNo;		// 메뉴 순번
	private String name;		// 메뉴명
	private int price;			// 가격
	private int stock;			// 재고
	private String enable;		// 활성화 여부
	
	// 생성자
	public Menu(String categoryId, String menuNo, String name, int price, int stock, String enable) {
		this.categoryId = categoryId;
		this.menuNo = menuNo;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.enable = enable;
	}
	
	// getter & setter
	public String getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getMenuNo() {
		return menuNo;
	}
	
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getEnable() {
		return enable;
	}
	
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	// 메소드
	/* 메소드명: printAdminMenu
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 관리자 모드에서 메뉴 목록을 양식에 맞게 출력한다. 
	 */
	public void printAdminMenu() {
		System.out.printf("%6s", this.categoryId);
		System.out.print("\t\t");
		System.out.printf("%6s", this.menuNo);
		System.out.print("\t");
		System.out.printf("%25s", this.name);
		System.out.print("\t");
		System.out.printf("%,4d", this.price);
		System.out.print("\t");
		System.out.printf("%4d", this.stock);
		System.out.print("\t");
		System.out.printf("%4s", this.enable);
		System.out.println();
	}
	
	
	
	
}
