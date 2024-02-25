package system.user.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import system.data.model.CartedMenu;
import system.data.model.Coupon;
import system.data.model.Member;
import system.data.model.Menu;
import system.data.model.SalesDetail;
import system.data.model.SalesTotal;
import system.user.UserModePanel;

public class UpdateDataProcess {
	// 필요한 객체를 좀 더 쉽게 참조하기 위해 사용자 모드 패널에서 생성한 객체를 받아오기
	private static ArrayList<CartedMenu> cart = UserModePanel.cart;			// 주문 목록
	
	private static SalesTotal salesTotal = UserModePanel.salesTotal;					// 통합 매출 내역 객체
	private static ArrayList<SalesDetail> salesDeatils = UserModePanel.salesDetails;	// 상세 매출 내역 객체 목록
	
	private static String birth;	// 생일 입력 다이얼로그에서 입력한 생일
	
	/* 메소드명: updateData
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 
	 */
	public static void updateData() {
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(데이터 처리 프로세스) :: 데이터 처리를 진행합니다.");
		
		// 상세 매출 내역 리스트
		for(CartedMenu cartedMenu : cart) {
			String orderNo = salesTotal.getOrderNo();							// 주문번호
			String type = "M";													// 상품 유형
			String id = cartedMenu.getCategoryId() + cartedMenu.getMenuNo();	// 상품 ID
			String name = cartedMenu.getMenuName();								// 상품 내용
			int price = cartedMenu.getPrice();									// 상품 단가
			int quantity = cartedMenu.getQuantity();							// 주문 수량
			
			salesDeatils.add(new SalesDetail(orderNo, type, id, name, price, quantity));	// 상세 매출 내역 객체 목록에 상세 매출 내역 객체 추가
		}
		
		// 메뉴 재고 업데이트
		updateMenuStock();	// 메뉴 재고량 업데이트 메소드 호출
		
		// 회원 누적 포인트, 쿠폰 사용 여부 업데이트
		String discountId = salesTotal.getDiscountId();							// 할인 ID(포인트: 회원 ID, 쿠폰: 쿠폰 ID)
		int discountAmt = CartedMenu.getTOTAL_AMT() - salesTotal.getTotalAmt();	// 할인 금액(주문 목록에 담긴 메뉴의 총 금액 - 결제 금액)
		
		if(!discountId.equals(" ")) {							// 포인트 혹은 쿠폰을 사용한 경우
			if(discountId.length() == 11) {						// 포인트를 사용한 경우
				updateMemberPoint(discountId, discountAmt);		// 누적 포인트 금액 업데이트 메소드 호출
				
				salesDeatils.add(new SalesDetail(salesTotal.getOrderNo(), "P", discountId, "포인트 사용", -discountAmt, 1));	// 상세 매출 내역 객체 목록에 포인트 사용 정보가 담긴 상세 매출 내역 객체 추가
				
			} else {							// 쿠폰을 사용한 경우
				updateCouponUsed(discountId);	// 쿠폰 사용 여부 업데이트 메소드 호출
				
				salesDeatils.add(new SalesDetail(salesTotal.getOrderNo(), "C", discountId, "쿠폰 사용", -discountAmt, 1));		// 상세 매출 내역 객체 목록에 쿠폰 사용 정보가 담긴 상세 매출 내역 객체 추가
			}
			
			if(salesTotal.getPayMethod().equals("신용카드") || salesTotal.getPayMethod().equals("간편결제")) {	// 신용카드 혹은 간편결제 거래 건인 경우 (포인트나 쿠폰 전액 결제 건이 아닌 경우)
				salesTotal.setDiscountId(" ");																// 할인 ID 정보 초기화
				
			}
		}
		
		// 통합 매출 내역 업데이트
		updateSalesTotal();		// 통합 매출 내역 객체에 저장된 정보를 통합 매출 내역 파일에 업데이트하는 메소드 호출
		
		// 상세 매출 내역 업데이트
		updateSalesDetail();	// 상세 매출 내역 객체 목록에 저장된 정보를 상세 매출 내역 파일에 업데이트하는 메소드 호출
		
		// 회원 포인트 적립
		if(!salesTotal.getMemberId().equals(" ")) {									// 포인트를 적립한 경우
			updateMemberInfo(salesTotal.getMemberId(), salesTotal.getTotalPts());	// 회원 정보 업데이트 메소드 호출
		}
		
		System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(데이터 처리 프로세스) :: 데이터 처리가 완료되어 영수증 출력 선택 패널로 넘어갑니다.");
		
		UserModePanel.selectPrintRecieptPanel.setVisible(true);		// 영수증 출력 선택 패널 보여주기
		
	}
	
	/* 메소드명: updateMenuStock
	 * 파라미터: 없음
	 * 반환값 boolean (true: 메뉴 재고 업데이트 성공, false: 메뉴 재고 업데이트 실패)
	 * 기능 설명: 메뉴 재고량을 업데이트한다.
	 */
	private static boolean updateMenuStock() {
		BufferedReader in;	// 입력 스트림
		PrintWriter out;	// 출력 스트림
		
		try {
			System.out.println("UpdateDataProcess >> updateMenuStock 메소드에서 메뉴 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Menu.FILE_NAME));	// 파일 내용을 읽어오기 위한 스트림
			out = new PrintWriter(new FileWriter(Menu.TMP_FILE_NAME));	// 파일 내용을 작성하기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("메뉴 파일이 존재하지 않습니다.");
			return false;
			
		} catch (IOException e) {	// 입출력 예외가 발생한 경우
			System.out.println("입출력 예외가 발생하였습니다.");
			return false;
		}
		
		String s;		// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;	// 구분자를 기준으로 분리된 데이터가 저장될 배열
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				String categoryId = tmp[Menu.INDEX_CATEGORY_ID];	// 카테고리 ID
				String menuNo = tmp[Menu.INDEX_MENU_NO];			// 메뉴 순번
				
				for(int i=0; i<cart.size(); i++) {
					if(cart.get(i).getCategoryId().equals(categoryId) && cart.get(i).getMenuNo().equals(menuNo)) {	// 파일에서 읽은 메뉴가 주문 목록에 담긴 메뉴과 같은 메뉴라면
						int oldStock = Integer.parseInt(tmp[Menu.INDEX_STOCK]);				// 변경 전 재고량
						tmp[Menu.INDEX_STOCK] = oldStock - cart.get(i).getQuantity() + "";	// 변경 후 재고량
						
						System.out.print(s + " ===> ");		// 변경 전 내용 출력
						
						s = String.join("|", tmp);			// 변경 후 내용 저장
						System.out.println(s);				// 변경 후 내용 출력
						
						break;
					}
				}
				
				out.println(s);		// 파일에 변경 후 내용 쓰기
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			out.close();	// 데이터 쓰기가 끝나면 스트림 닫기
			
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("예외가 발생하였습니다.");
			return false;
		}
		
		// 변경 전 파일 삭제
		File file = new File(Menu.FILE_NAME);
		file.delete();
		
		// 변경 후 파일의 파일명을 변경 전 파일명으로 변경
		file = new File(Menu.TMP_FILE_NAME);
		file.renameTo(new File(Menu.FILE_NAME));
		
		System.out.println(Menu.FILE_NAME + " 파일의 업데이트가 완료되었습니다.");
		return true;
	}
	
	/* 메소드명: updateMemberPoint
	 * 파라미터: String memberId (포인트를 사용한 회원 ID)
	 * 			int usePointAmt (사용한 포인트 금액)
	 * 반환값: boolean (true: 회원 포인트 업데이트 성공, false: 회원 포인트 업데이트 실패)
	 * 기능 설명: 포인트를 사용한 경우 파라미터로 받은 회원 ID로 회원 조회 후 누적 포인트 금액을 업데이트한다.
	 */
	private static boolean updateMemberPoint(String memberId, int usePointAmt) {
		BufferedReader in;	// 입력 스트림
		PrintWriter out;	// 출력 스트림
		
		try {
			System.out.println("UpdateDataProcess >> updateMemberPoint 메소드에서 회원 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Member.FILE_NAME));	// 파일 내용을 읽어오기 위한 스트림
			out = new PrintWriter(new FileWriter(Member.TMP_FILE_NAME));	// 파일 내용을 작성하기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("회원 파일이 존재하지 않습니다.");
			return false;
			
		} catch (IOException e) {	// 입출력 예외가 발생한 경우
			System.out.println("입출력 예외가 발생하였습니다.");
			return false;
		}
		
		String s;		// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;	// 구분자를 기준으로 분리된 데이터가 저장될 배열
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				if(tmp[Member.INDEX_MEMBER_ID].equals(memberId)) {											// 회원 ID가 일치하는 경우
					tmp[Member.INDEX_POINT] = Integer.parseInt(tmp[Member.INDEX_POINT]) - usePointAmt + "";	// 새로운 포인트 금액 계산
					
					System.out.print(s + " ===> ");		// 변경 전 내용 출력
					
					s = String.join("|", tmp);			// 변경 후 내용 저장
					System.out.println(s);				// 변경 후 내용 출력
				}
				
				out.println(s);		// 파일에 변경 후 내용 쓰기
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			out.close();	// 데이터 쓰기가 끝나면 스트림 닫기
			
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("예외가 발생하였습니다.");
			return false;
		}
		
		// 변경 전 파일 삭제
		File file = new File(Member.FILE_NAME);
		file.delete();
		
		// 변경 후 파일의 파일명을 변경 전 파일명으로 변경
		file = new File(Member.TMP_FILE_NAME);
		file.renameTo(new File(Member.FILE_NAME));
		
		System.out.println(Member.FILE_NAME + " 파일의 업데이트가 완료되었습니다.");
		return true;
	}
	
	/* 메소드명: updateCouponUsed
	 * 파라미터: String couponId (사용한 쿠폰 ID)
	 * 반환값: boolean (true: 쿠폰 사용 여부 업데이트 성공, false: 쿠폰 사용 여부 업데이트 실패)
	 * 기능 설명: 쿠폰을 사용한 경우 파라미터로 받은 쿠폰 ID로 쿠폰 조회 후 쿠폰 사용 여부를 업데이트한다.
	 */
	private static boolean updateCouponUsed(String couponId) {
		BufferedReader in;	// 입력 스트림
		PrintWriter out;	// 출력 스트림
		
		try {
			System.out.println("UpdateDataProcess >> updateCouponUsed 메소드에서 쿠폰 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Coupon.FILE_NAME));		// 파일 내용을 읽어오기 위한 스트림
			out = new PrintWriter(new FileWriter(Coupon.TMP_FILE_NAME));	// 파일 내용을 작성하기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("쿠폰 파일이 존재하지 않습니다.");
			return false;
			
		} catch (IOException e) {	// 입출력 예외가 발생한 경우
			System.out.println("입출력 예외가 발생하였습니다.");
			return false;
		}
		
		String s;		// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;	// 구분자를 기준으로 분리된 데이터가 저장될 배열
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				if(tmp[Coupon.INDEX_COUPON_ID].equals(couponId)) {	// 쿠폰 ID가 일치하는 경우
					tmp[Coupon.INDEX_USED] = "Y";					// 쿠폰을 사용한 것으로 변경
					
					System.out.print(s + " ===> ");		// 변경 전 내용 출력
					
					s = String.join("|", tmp);			// 변경 후 내용 저장
					System.out.println(s);				// 변경 후 내용 출력
				}
				
				out.println(s);		// 파일에 변경 후 내용 쓰기
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			out.close();	// 데이터 쓰기가 끝나면 스트림 닫기
			
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("예외가 발생하였습니다.");
			return false;
		}
		
		// 변경 전 파일 삭제
		File file = new File(Coupon.FILE_NAME);
		file.delete();
		
		// 변경 후 파일의 파일명을 변경 전 파일명으로 변경
		file = new File(Coupon.TMP_FILE_NAME);
		file.renameTo(new File(Coupon.FILE_NAME));
		
		System.out.println(Coupon.FILE_NAME + " 파일의 업데이트가 완료되었습니다.");
		return true;
	}
	
	/* 메소드명: updateSalesTotal
	 * 파라미터: 없음
	 * 반환값: boolean (true: 통합 매출 내역 업데이트 성공, false: 통합 매출 내역 업데이트 실패)
	 * 기능 설명: 통합 매출 내역 객체에 저장된 정보를 통합 매출 내역 파일에 업데이트한다.
	 */
	private static boolean updateSalesTotal() {
		try {
			System.out.println("UpdateDataProcess >> updateSalesTotal 메소드에서 통합 매출 내역을 작성합니다.");
			
			PrintWriter out = new PrintWriter(new FileWriter(salesTotal.getFileName(), true));	// 파일 내용을 작성하기 위한 스트림
			
			out.println(salesTotal.toDataTemplate());					// 통합 매출 데이터 작성
			System.out.println("[add]" + salesTotal.toDataTemplate());	// 작성된 내용 출력
			
			out.close();	// 데이터 쓰기가 끝나면 스트림 닫기
		
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("예외가 발생하였습니다.");
			return false;
		}
		
		System.out.println(salesTotal.getFileName() + " 파일의 업데이트가 완료되었습니다.");
		return true;
	}
	
	/* 메소드명: updateSalesDetail
	 * 파라미터: 없음
	 * 반환값: boolean (true: 상세 매출 내역 업데이트 성공, false: 상세 매출 내역 업데이트 실패)
	 * 기능 설명: 상세 매출 내역 객체 목록에 저장된 정보를 상세 매출 내역 파일에 업데이트한다.
	 */
	private static boolean updateSalesDetail() {
		try {
			System.out.println("UpdateDataProcess >> updateSalesDetail 메소드에서 상세 매출 내역을 작성합니다.");
			
			PrintWriter out = new PrintWriter(new FileWriter(salesDeatils.get(0).getFileName(), true));	// 파일 내용을 작성하기 위한 스트림
			
			for(SalesDetail salesDetail : salesDeatils) {
				out.println(salesDetail.toDataTemplate());					// 상세 매출 데이터 작성
				System.out.println("[add]" + salesDetail.toDataTemplate());	// 작성된 내용 출력
			}
			
			out.close();	// 데이터 쓰기가 끝나면 스트림 닫기
		
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("예외가 발생하였습니다.");
			return false;
		}
		
		System.out.println(salesDeatils.get(0).getFileName() + " 파일의 업데이트가 완료되었습니다.");
		return true;
	}
	
	/* 메소드명: updateMemberInfo
	 * 파라미터: Strimg memberId (포인트를 적립한 회원 Id)
	 * 			int point (새로운 포인트 금액)
	 * 반환값: boolean (true: 회원 정보 업데이트 성공, false: 회원 정보 업데이트 실패)
	 * 기능 설명: 파라미터로 받은 회원 ID로 회원 조회 후 기존 회원이면 포인트, 생일 정보를 업데이트 하고 신규 회원이면 새로운 내용을 작성한다.
	 */
	private static boolean updateMemberInfo(String memberId, int point) {
		BufferedReader in;	// 입력 스트림
		PrintWriter out;	// 출력 스트림
		
		try {
			System.out.println("UpdateDataProcess >> updateMemberInfo 메소드에서 회원 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Member.FILE_NAME));		// 파일 내용을 읽어오기 위한 스트림
			out = new PrintWriter(new FileWriter(Member.TMP_FILE_NAME));	// 파일 내용을 작성하기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("회원 파일이 존재하지 않습니다.");
			return false;
			
		} catch (IOException e) {	// 입출력 예외가 발생한 경우
			System.out.println("입출력 예외가 발생하였습니다.");
			return false;
		}
		
		String s;					// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;				// 구분자를 기준으로 분리된 데이터가 저장될 배열
		boolean isNewMember = true;	// 신규 회원 여부 (true: 신규 회원, false: 기존 회원)
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				if(tmp[Member.INDEX_MEMBER_ID].equals(memberId)) {	// 회원 ID가 일치하는 경우
					isNewMember = false;							// 신규 회원 여부 변수에 기존 회원을 의미하는 false 저장
					
					tmp[Member.INDEX_POINT] = point + "";			// 포인트 저장
					tmp[Member.INDEX_BIRTH] = birth;				// 생일 저장
					
					System.out.print(s + " ===> ");		// 변경 전 내용 출력
					
					s = String.join("|", tmp);			// 변경 후 내용 저장
					System.out.println(s);				// 변경 후 내용 출력
					
				}
				
				out.println(s);		// 파일에 변경 후 내용 쓰기
			}
			
			if(isNewMember) {		// 신규 회원인 경우
				out.println(memberId + "|" + point + "|" + birth);	// 새로운 내용으로 파일에 내용 쓰기기
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			out.close();	// 데이터 쓰기가 끝나면 스트림 닫기
			
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("예외가 발생하였습니다.");
			return false;
		}
		
		// 변경 전 파일 삭제
		File file = new File(Member.FILE_NAME);
		file.delete();
		
		// 변경 후 파일의 파일명을 변경 전 파일명으로 변경
		file = new File(Member.TMP_FILE_NAME);
		file.renameTo(new File(Member.FILE_NAME));
		
		System.out.println(Member.FILE_NAME + " 파일의 업데이트가 완료되었습니다.");
		return true;
	}
	
	/* 메소드명: setBirth
	 * 파라미터: String birth (생일 입력 다이얼로그에서 입력한 생일 정보)
	 * 반환값: 없음
	 * 기능 설명: 파라미터로 받은 생일 정보를 저장한다.
	 */
	public static void setBirth(String birth) {
		UpdateDataProcess.birth = birth;
	}
}
