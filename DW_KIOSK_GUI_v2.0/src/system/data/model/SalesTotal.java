package system.data.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SalesTotal {
	public static final String FILE_DIRECTORY = "src/files/sales_data/";	// 통합 매출 내역 파일 경로
	
	public static final int INDEX_ORDER_TYPE = 0; 	// 주문 유형
	public static final int INDEX_WAIT_NO = 1; 		// 대기번호
	public static final int INDEX_TRANS_DT = 2; 	// 거래일시
	public static final int INDEX_ORDER_NO = 3; 	// 주문번호
	public static final int INDEX_TOTAL_AMT = 4; 	// 총 거래 금액
	public static final int INDEX_SUPPLY_AMT = 5; 	// 공급가액
	public static final int INDEX_VAT = 6; 			// 부가세
	public static final int INDEX_PAY_METHOD = 7; 	// 결제 수단
	public static final int INDEX_DISCOUNT_ID = 8; 	// 할인 ID
	public static final int INDEX_CARD_NAME = 9; 	// 카드사명
	public static final int INDEX_CARD_NO = 10; 	// 카드 번호
	public static final int INDEX_CARD_QUOTA = 11; 	// 할부개월
	public static final int INDEX_AUTH_CODE = 12; 	// 승인번호
	public static final int INDEX_MEMBER_ID = 13; 	// 적립 ID
	public static final int INDEX_REWARD_PTS = 14; 	// 적립 포인트 금액
	public static final int INDEX_TOTAL_PTS = 15; 	// 누적 포인트 금액
	
	private String fileName = "sales_total";
	
	private String orderType = " ";		// 주문 유형 (매장/포장)
	private String waitNo = " ";		// 대기번호 (숫자 4자리)
	private String transDt = " ";		// 거래일시 (YYYYMMDDHHMIDD)
	private String orderNo = " ";		// 주문번호 (YYYYMMDD + '-' + 대기번호)
	private int totalAmt = 0;			// 총 거래 금액
	private int supplyAmt = 0;			// 공급가액 (거래금액의 90%)
	private int vat = 0;				// 부가세 (거래금액의 10%)
	private String payMethod = " ";		// 결제 수단 (포인트/쿠폰/신용카드/간편결제)
	private String discountId = " ";	// 할인 ID (포인트 전액 결제 시 회원 ID/쿠폰 전액 결제 시 쿠폰 ID/할인 없을 경우 null)
	private String cardName = " ";		// 카드사명 (비씨/국민/신한/현대/롯데/씨티/농협/우리/하나)
	private String cardNo = " ";		// 카드번호 (7~12 자리는 '*'로 마스킹 처리)
	private String cardQuota = " ";		// 할부개월 (00: 일시불, 02: 2개월, …)
	private String authCode = " ";		// 승인번호 (숫자 6자리)
	private String memberId = " ";		// 적립 ID (포인트를 적립한 경우 회원 ID/미적립 시 null)
	private int rewardPts = 0;			// 적립 포인트 금액
	private int totalPts = 0;			// 누적 포인트 금액
	
	// getter & setter
	public String getFileName() {
		return fileName;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getWaitNo() {
		return waitNo;
	}
	
	public void setWaitNo(String waitNo) {
		this.waitNo = waitNo;
	}
	
	public String getTransDt() {
		return transDt;
	}
	
	public void setTransDt(String transDt) {
		this.transDt = transDt;
		
		// 거래일시 정보가 설정되면 거래일자 정보를 이용해 파일명(fileName), 대기번호(waitNo), 주문번호(orderNo) 설정
		fileName = FILE_DIRECTORY + this.fileName + "(" + this.transDt.substring(0, 8) + ").txt";	// 파일명 설정
		
		int waitNo = 1;		// 대기번호 (당일 최초 대기번호는 1)
		BufferedReader in;	// 입력 스트림
		
		try {
			System.out.println("SalesTotal >> setTransDt 메소드에서 통합 매출 내역 목록을 읽어옵니다.");
			
			in = new BufferedReader(new FileReader(fileName));	// 파일 내용을 읽어오기 위한 스트림
			
		} catch(FileNotFoundException e) {								// 파일이 존재하지 않는 경우
			this.waitNo = String.format("%04d", waitNo);				// 대기번호
			orderNo = this.transDt.substring(0, 8) + '-' + this.waitNo; // 주문번호

			return;
		}
		
		try {
			while(in.readLine() != null) {	// 읽어온 데이터가 있는 경우
				waitNo++;					// 대기번호 1 증가
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			
		} catch (Exception e) {		// 예외가 발생한 경우
			System.out.println("통합 매출 내역 파일 읽기에 실패하였습니다.");
			return;
		}
		
		this.waitNo = String.format("%04d", waitNo);				// 대기번호
		orderNo = this.transDt.substring(0, 8) + '-' + this.waitNo; // 주문번호
		
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public int getTotalAmt() {
		return totalAmt;
	}
	
	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
		
		// 거래 금액 설정 시 공급가액, 부가세 계산
		supplyAmt = (int)(totalAmt * 0.9);	// 공급가액
		vat = (int)(totalAmt * 0.1);		// 부가세
	}
	
	public int getSupplyAmt() {
		return supplyAmt;
	}

	public int getVat() {
		return vat;
	}

	public String getPayMethod() {
		return payMethod;
	}
	
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
	public String getDiscountId() {
		return discountId;
	}
	
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getCardQuota() {
		return cardQuota;
	}
	
	public void setCardQuota(String cardQuota) {
		this.cardQuota = cardQuota;
	}
	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public String getMemberId() {
		return memberId;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public int getRewardPts() {
		return rewardPts;
	}
	
	public void setRewardPts(int rewardPts) {
		this.rewardPts = rewardPts;
	}
	
	public int getTotalPts() {
		return totalPts;
	}
	
	public void setTotalPts(int totalPts) {
		this.totalPts = totalPts;
	}
	
	/* 메소드명: toDataTemplate
	 * 파라미터: 없음
	 * 반환값: String str (통합 매출 내역 파일에 작성되는 형식으로 구성된 문자열)
	 * 기능 설명: 통합 매출 내역 객체에 저장된 정보를 파일에 작성되는 형식의 문자열로 구성하여 반환한다.
	 */
	public String toDataTemplate() {
		String str = "";
		str += orderType + "|" + waitNo + "|" + transDt + "|" + orderNo + "|";
		str += totalAmt + "|" + supplyAmt + "|" + vat + "|" + payMethod + "|";
		str += discountId + "|" + cardName + "|" + cardNo + "|" + cardQuota + "|";
		str += authCode + "|" + memberId + "|" + rewardPts + "|" + totalPts;
		
		return str;
	}
	
	/* 메소드명: initialize
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 모든 필드의 값을 초기화한다.
	 */
	public void initialize() {
		orderType = " ";	// 주문 유형 (매장/포장)
		waitNo = " ";		// 대기번호 (숫자 4자리)
		transDt = " ";		// 거래일시 (YYYYMMDDHHMIDD)
		orderNo = " ";		// 주문번호 (YYYYMMDD + '-' + 대기번호)
		totalAmt = 0;		// 총 거래 금액
		supplyAmt = 0;		// 공급가액 (거래금액의 90%)
		vat = 0;			// 부가세 (거래금액의 10%)
		payMethod = " ";	// 결제 수단 (포인트/쿠폰/신용카드/간편결제)
		discountId = " ";	// 할인 ID (포인트 전액 결제 시 회원 ID/쿠폰 전액 결제 시 쿠폰 ID/할인 없을 경우 null)
		cardName = " ";		// 카드사명 (비씨/국민/신한/현대/롯데/씨티/농협/우리/하나)
		cardNo = " ";		// 카드번호 (7~12 자리는 '*'로 마스킹 처리)
		cardQuota = " ";	// 할부개월 (00: 일시불, 02: 2개월, …)
		authCode = " ";		// 승인번호 (숫자 6자리)
		memberId = " ";		// 적립 ID (포인트를 적립한 경우 회원 ID/미적립 시 null)
		rewardPts = 0;		// 적립 포인트 금액
		totalPts = 0;		// 누적 포인트 금액
		
		fileName = "sales_total";	// 파일명
	}
}
