package system.data.model;

public class Category {
	public static final String FILE_NAME = "src/files/data/category.txt";			// 카테고리 파일 경로
	public static final String TMP_FILE_NAME = "src/files/data/category_tmp.txt";	// 수정된 카테고리 파일을 임시로 저장하는 파일 경로
	
	public static final int INDEX_CATEGORY_ID = 0;	// 카테고리 ID
	public static final int INDEX_NAME = 1;			// 카테고리명
	public static final int INDEX_ENABLE = 2;		// 활성화 여부
	
	private String categoryId;	// 카테고리 ID
	private String name;		// 카테고리명
	private String enable;		// 활성화 여부
	
	// 생성자
	public Category(String categoryId, String name, String enable) {
		this.categoryId = categoryId;
		this.name = name;
		this.enable = enable;
	}

	// getter & setter
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	// 메소드
	/* 메소드명: printCategory
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 관리자 모드에서 카테고리 목록을 양식에 맞게 출력한다. 
	 */
	public void printCategory() {
		System.out.printf("%6s", this.categoryId);
		System.out.print("\t");
		System.out.printf("%12s",this.name);
		System.out.print("\t");
		System.out.printf("%4s", this.enable);
		System.out.println();
	}
}
