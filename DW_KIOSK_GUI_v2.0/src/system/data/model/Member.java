package system.data.model;

public class Member {
	public static final String FILE_NAME = "src/files/data/member.txt";				// 회원 파일 경로
	public static final String TMP_FILE_NAME = "src/files/data/member_tmp.txt";		// 수정된 회원 파일 임시로 저장하는 파일 경로
	
	public static final int INDEX_MEMBER_ID = 0;	// 회원 ID
	public static final int INDEX_POINT = 1;		// 포인트
	public static final int INDEX_BIRTH = 2;		// 생일
	
	private String memberId;	// 회원 ID(휴대전화 번호 11자리)
	private int point;			// 포인트
	private String birth;		// 생일(MMDD)
	
	// 생성자
	public Member(String memberId, int point, String birth) {
		this.memberId = memberId;
		this.point = point;
		this.birth = birth;
	}

	// getter & setter
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	// 메소드
	/* 메소드명: printAdminMember
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 관리자 모드에서 회원 목록을 양식에 맞게 출력한다. 
	 */
	public void printAdminMember() {
		System.out.print("      ");
		System.out.printf("%11s", this.memberId);
		System.out.print("\t");
		System.out.printf("%,6d", this.point);
		System.out.print("\t\t  ");
		System.out.printf("%4s", this.birth);
		System.out.println();
	}
	
}
