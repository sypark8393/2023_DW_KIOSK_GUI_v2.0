package system.main;

import java.io.File;
import java.util.Calendar;

import system.data.model.Category;
import system.data.model.Coupon;
import system.data.model.Member;
import system.data.model.Menu;

public class FileCheck {
	private static final String MENU_FILE = Menu.FILE_NAME;				// 메뉴 파일명
	private static final String CATEGORY_FILE = Category.FILE_NAME;		// 카테고리 파일명
	private static final String MEMBER_FILE = Member.FILE_NAME;			// 회원 파일명
	private static final String COUPON_FILE = Coupon.FILE_NAME;			// 쿠폰 파일명
	
	/* 메소드명: allFilesCheck
	 * 파라미터: 없음
	 * 반환값: boolean fileCheck (true: 모든 주문 처리 파일이 존재, false: 주문 처리 파일이 1개라도 존재하지 않음)
	 * 기능 설명: 최초 실행 시 모든 주문 처리 파일이 존재하는지 검사한다.
	 * 			(주문 처리 파일: menu.txt, category.txt, member.txt, coupon.txt)
	 */
	public static boolean allFilesCheck() {
		boolean fileCheck = true;		// 모든 파일이 존재하는 상태로 가정
		
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 시스템 실행 전 주문 처리 파일을 검사합니다.");
		
		// 메뉴
		if(!new File(MENU_FILE).exists()) {		// 메뉴 파일이 존재하지 않는 경우
			System.out.println(MENU_FILE + "이 존재하지 않습니다.");
			fileCheck = false;
		
		} else {	// 메뉴 파일이 존재하는 경우
			System.out.println(MENU_FILE + "이 존재합니다.");
		}
		
		// 카테고리
		if(!new File(CATEGORY_FILE).exists()) {		// 카테고리 파일이 존재하지 않는 경우
			System.out.println(CATEGORY_FILE + "이 존재하지 않습니다.");
			fileCheck = false;
		
		} else {	// 카테고리 파일이 존재하는 경우
			System.out.println(CATEGORY_FILE + "이 존재합니다.");
		}
		
		// 회원
		if(!new File(MEMBER_FILE).exists()) {		// 회원 파일이 존재하지 않는 경우
			System.out.println(MEMBER_FILE + "이 존재하지 않습니다.");
			fileCheck = false;
		
		} else {	// 회원 파일이 존재하는 경우
			System.out.println(MEMBER_FILE + "이 존재합니다.");
		}
		
		// 쿠폰
		if(!new File(COUPON_FILE).exists()) {		// 쿠폰 파일이 존재하지 않는 경우
			System.out.println(COUPON_FILE + "이 존재하지 않습니다.");
			fileCheck = false;
		
		} else {	// 쿠폰 파일이 존재하는 경우
			System.out.println(COUPON_FILE + "이 존재합니다.");
		}
		
		return fileCheck;
	}
	
}
