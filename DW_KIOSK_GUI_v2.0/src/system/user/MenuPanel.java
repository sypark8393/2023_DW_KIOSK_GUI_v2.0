package system.user;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import system.custom.CustomButton;
import system.custom.CustomColor;
import system.custom.CustomLabel;
import system.data.model.CartedMenu;
import system.data.model.Category;
import system.data.model.Menu;
import system.user.dialog.AddMenuFailAlertDialog;
import system.user.dialog.GoMainConfirmDialog;

public class MenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7971095171273067716L;	// 버전 ID
	
	// 필요한 객체를 좀 더 쉽게 참조하기 위해 사용자 모드 패널에서 생성한 객체를 받아오기
	private static HashMap<String, String> categories = UserModePanel.categories;								// 카테고리 목록, {카테고리 ID : 카테고리명} 형식
	private static ArrayList<Menu> menus = UserModePanel.menus;													// 메뉴 목록
	private static ArrayList<Integer> firstMenuIndexOfCategories = UserModePanel.firstMenuIndexOfCategories;	// 카테고리별 첫번째 메뉴의 인덱스 목록
	private static ArrayList<CartedMenu> cart = UserModePanel.cart;												// 주문 목록
	
	private String backgroundImageSource = "src/img/background.png"; 	// 배경 이미지 경로
	private String homeIconSource = "src/img/icon_main.png";			// 메인 아이콘 경로
	private String imageSource = "src/img/ordering.gif";				// 이미지 경로
	private String menuIconDirectory = "src/img/icon_menu/";			// 메뉴 이미지 아이콘 디렉터리
	
	private int maxOrderableQuantity = 50;	// 최대 주문 가능 수량
	
	private int currentCategoryIndex;		// 현재 가리키고 있는 카테고리의 인덱스
	private int currentCategoryPage;		// 현재 가리키고 있는 카테고리 페이지
	private int currentMenuPage;			// 현재 가리키고 있는 메뉴 페이지
	private int cartViewPointer;			// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스
	
	private CustomButton mainButton;										// 메인 버튼
	private CustomButton prevCategoryPageButton, nextCategoryPageButton;	// 카테고리 페이지 이동 버튼
	private CustomButton[] categoryButtons = new CustomButton[4];			// 카테고리 버튼
	private CustomButton prevMenuPageButton, nextMenuPageButton;			// 메뉴 페이지 이동 버튼
	private CustomButton[] menuButtons = new CustomButton[12];				// 메뉴 버튼
	
	private JPanel cartViewPanel;											// 주문 목록 view 패널
	private CustomLabel[] menuNameLabels = new CustomLabel[5];				// 메뉴명 레이블
	private CustomLabel[] quantityLabels = new CustomLabel[5];				// 수량 레이블
	private CustomLabel[] totalPriceLabels = new CustomLabel[5];			// 금액 레이블
	private CustomButton[] deleteCartItemButtons = new CustomButton[5];		// 주문 목록 아이템 삭제 버튼
	private CustomButton[] decreaseQuantityButtons = new CustomButton[5];	// 수량 감소 버튼
	private CustomButton[] increaseQuantityButtons = new CustomButton[5];	// 수량 증가 버튼
	private JPanel cartViewScrollPanel;										// 주문 목록 view 스크롤 패널
	private CustomButton scrollUpButton, scrollDownButton;					// 주문 목록 view 스크롤 이동 버튼
	
	private CustomLabel imageLabel;				// 이미지 레이블
	private CustomLabel totalQuantityLabel;		// 총 주문 수량 레이블
	private CustomButton clearCartButton;		// 주문 목록 전체 삭제 버튼
	private CustomButton payButton;				// 금액 정보가 함께 표시되는 결제 버튼
	
	/* 생성자: MenuPanel
	 * 파라미터: 없음
	 * 기능 설명: 메뉴를 선택하는 화면에서 보여질 구성요소의 속성을 설정한다.
	 */
	public MenuPanel() {
		this.setLayout(null);				// 패널의 레이아웃 매니저 제거
		this.setBounds(0, 0, 720, 1000);	// 패널의 위치는 (0, 0), 크기는 720px * 1000px 로 설정
			
		// 메인 버튼
		mainButton = new CustomButton(new ImageIcon(homeIconSource));	// 이미지 아이콘을 가지는 버튼 생성
		mainButton.applyMainDesign(0, 0);								// 메인 버튼을 의미하는 디자인 적용
		mainButton.addActionListener(new GoMainListener());				// 메인 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(mainButton);											// 패널에 메인 버튼 추가
		
		// 카테고리 페이지 이동 버튼
		// - 이전 카테고리 페이지
		prevCategoryPageButton = new CustomButton("<");								// 텍스트를 가지는 버튼 생성
		prevCategoryPageButton.applyMoveCategoryPageDesign(60, 85);					// 카테고리 페이지 이동 버튼을 의미하는 디자인 적용
		prevCategoryPageButton.addActionListener(new MoveCategoryPageListener());	// 카테고리 페이지 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(prevCategoryPageButton);											// 패널에 이전 카테고리 페이지로 이동하는 버튼 추가
		
		// - 다음 카테고리 페이지
		nextCategoryPageButton = new CustomButton(">");								// 텍스트를 가지는 버튼 생성
		nextCategoryPageButton.applyMoveCategoryPageDesign(640, 85);				// 카테고리 페이지 이동 버튼을 의미하는 디자인 적용
		nextCategoryPageButton.addActionListener(new MoveCategoryPageListener());	// 카테고리 페이지 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(nextCategoryPageButton);											// 패널에 다음 카테고리 페이지로 이동하는 버튼 추가
		
		// 카테고리 버튼
		for(int i=0; i<categoryButtons.length; i++) {
			int x = 90 + 140 * i;	// 90: 첫번째 버튼의 원점 x좌표, 140: 버튼 폭(120) + 여백(20), i: n번째
			
			categoryButtons[i] = new CustomButton();							// 내용을 가지지 않는 빈 버튼 생성
			categoryButtons[i].applyCategoryDesign(x, 80); 						// (선택되지 않은 상태의) 카테고리 버튼을 의미하는 디자인 적용
			categoryButtons[i].addActionListener(new SelectCategoryListener());	// 카테고리 버튼 클릭 이벤트 처리를 위한 리스너 등록
			this.add(categoryButtons[i]);										// 패널에 카테고리 버튼 추가
		}
		
		// 메뉴 페이지 이동 버튼
		// - 이전 메뉴 페이지
		prevMenuPageButton = new CustomButton("<");							// 텍스트를 가지는 버튼 생성
		prevMenuPageButton.applyMoveMenuPageDesign(20, 400);					// 메뉴 페이지 이동 버튼을 의미하는 디자인 적용
		prevMenuPageButton.addActionListener(new MoveMenuPageListener());	// 메뉴 페이지 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(prevMenuPageButton);										// 패널에 이전 메뉴 페이지로 이동하는 버튼 추가
		
		// - 다음 메뉴 페이지
		nextMenuPageButton = new CustomButton(">");							// 텍스트를 가지는 버튼 생성
		nextMenuPageButton.applyMoveMenuPageDesign(680, 400);					// 메뉴 페이지 다음 버튼을 의미하는 디자인 적용
		nextMenuPageButton.addActionListener(new MoveMenuPageListener());	// 메뉴 페이지 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(nextMenuPageButton);										// 메뉴 페이지 이동 버튼을 의미하는 디자인 적용
		
		// 메뉴 버튼
		for(int i=0; i<menuButtons.length; i++) {
			int x = 70 + 150 * (i % 4);		// 70: 첫번째 버튼의 원점 x좌표, 150: 버튼 폭(130) + 여백(20), i % (한 행에 들어가는 메뉴 버튼 개수): 행 기준 n번째
			int y = 140 + 200 * (i / 4);	// 140: 첫번째 버튼의 원점 y좌표, 200: 버튼 높이(160) + 여백(40), i / (한 행에 들어가는 메뉴 버튼 개수): 열 기준 n번째
			
			menuButtons[i] = new CustomButton();						// 내용을 가지지 않는 빈 버튼 생성
			menuButtons[i].applyMenuDesign(x, y);						// 메뉴 버튼을 의미하는 디자인 적용
			menuButtons[i].addActionListener(new AddMenuListener());	// 메뉴 버튼 클릭 이벤트 처리를 위한 리스너 등록
			this.add(menuButtons[i]);									// 패널에 메뉴 버튼 추가
		}
		
		// 주문 목록 view 패널
		cartViewPanel = new JPanel();					// 주문 목록 view 패널 생성
		cartViewPanel.setBounds(20, 740, 480, 240);		// 주문 목록 view 패널을 (20, 740)에 위치시키고 크기는 480px * 240px로 설정
		cartViewPanel.setBackground(Color.white);		// 주문 목록 view 패널의 배경색을 흰색으로 지정
		cartViewPanel.setLayout(null);					// 주문 목록 view 패널의 레이아웃 매니저 제거
		this.add(cartViewPanel);						// 패널에 주문 목록 view 패널 추가
		
		// 주문 목록 view에 들어갈 아이템
		for(int i = 0; i<5; i++) {
			int y = 20 + 40 * i;	// 20: 첫번째 아이템의 원점 y좌표, 40: 아이템 높이(30) + 여백(10), i: 열 기준 n번째 아이템
			
			// 메뉴명 레이블
			menuNameLabels[i] = new CustomLabel();				// 내용을 가지지 않는 빈 레이블 생성
			menuNameLabels[i].applyMenuNameInMenuDesign(55, y);	// MenuPanel에 있는 메뉴명 레이블을 의미하는 디자인 적용
			cartViewPanel.add(menuNameLabels[i]);				// 주문 목록 view 패널에 메뉴명 레이블 추가
			
			// 수량 레이블
			quantityLabels[i] = new CustomLabel();					// 내용을 가지지 않는 빈 레이블 생성
			quantityLabels[i].applyQuantityInMenuDesign(315, y);	// MenuPanel에 있는 수량 레이블을 의미하는 디자인 적용
			cartViewPanel.add(quantityLabels[i]);					// 주문 목록 view 패널에 수량 레이블 추가
			
			// 금액 레이블
			totalPriceLabels[i] = new CustomLabel();					// 내용을 가지지 않는 빈 레이블 생성
			totalPriceLabels[i].applyTotalPriceInMenuDesign(380, y);	// MenuPanel에 있는 금액 레이블을 의미하는 디자인 적용
			cartViewPanel.add(totalPriceLabels[i]);						// 주문 목록 view 패널에 금액 레이블 추가
			
			// 주문 목록 아이템 삭제 버튼
			deleteCartItemButtons[i] = new CustomButton("X");							// 텍스트를 가지는 버튼 생성
			deleteCartItemButtons[i].applyDeleteCartItemDesign(20, y);					// 주문 목록 아이템 삭제 버튼을 의미하는 디자인 적용
			deleteCartItemButtons[i].addActionListener(new DeleteCartItemListener());	// 주문 목록 아이템 삭제 버튼 클릭 이벤트 처리를 위한 리스너 등록
			cartViewPanel.add(deleteCartItemButtons[i]);								// 주문 목록 view 패널에 주문 목록 아이템 삭제 버튼 추가
			
			// 수량 감소 버튼
			decreaseQuantityButtons[i] = new CustomButton("-");								// 텍스트를 가지는 버튼 생성
			decreaseQuantityButtons[i].applyDecreaseQuantityDesign(285, y);					// 수량 감소 버튼을 의미하는 디자인 적용
			decreaseQuantityButtons[i].addActionListener(new DecreaseQuantityListener());	// 수량 감소 버튼 클릭 이벤트 처리를 위한 리스너 등록
			cartViewPanel.add(decreaseQuantityButtons[i]);									// 주문 목록 view 패널에 수량 감소 버튼 추가
			
			// 수량 증가 버튼
			increaseQuantityButtons[i] = new CustomButton("+");								// 텍스트를 가지는 버튼 생성
			increaseQuantityButtons[i].applyIncreaseQuantityDesign(345, y);					// 수량 증가 버튼을 의미하는 디자인 적용
			increaseQuantityButtons[i].addActionListener(new IncreaseQuantityListener());	// 수량 증가 버튼 클릭 이벤트 처리를 위한 리스너 등록
			cartViewPanel.add(increaseQuantityButtons[i]);									// 주문 목록 view 패널에 수량 증가 버튼 추가
		}
		
		// 주문 목록 view 스크롤 패널
		cartViewScrollPanel = new JPanel();								// 주문 목록 view 스크롤 패널 생성
		cartViewScrollPanel.setBounds(500, 740, 20, 240);				// 주문 목록 view 스크롤 패널을 (500, 740)에 위치시키고 크기는 20px * 240px로 설정
		cartViewScrollPanel.setBackground(CustomColor.KIOSK_GRAY_217);	// 주문 목록 view 스크롤 패널의 배경색을 흰색으로 지정
		cartViewScrollPanel.setLayout(null);							// 주문 목록 view 스크롤 패널의 레이아웃 매니저 제거
		this.add(cartViewScrollPanel);									// 패널에 주문 목록 view 스크롤 패널 추가
		
		// 주문 목록 view 스크롤 이동 버튼
		// - 위로 이동
		scrollUpButton = new CustomButton("▲");									// 텍스트를 가지는 버튼 생성
		scrollUpButton.applyScrollInMenuDesign(0, 0);									// 주문 목록 view 스크롤 이동 버튼을 의미하는 디자인 적용
		scrollUpButton.addActionListener(new MoveCartViewScrollListener());		// 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		cartViewScrollPanel.add(scrollUpButton);								// 주문 목록 view 스크롤 패널에 위로 이동하는 스크롤 버튼 추가
		
		// - 아래 이동
		scrollDownButton = new CustomButton("▼");								// 텍스트를 가지는 버튼 생성
		scrollDownButton.applyScrollInMenuDesign(0, 220);								// 주문 목록 view 스크롤 이동 버튼을 의미하는 디자인 적용
		scrollDownButton.addActionListener(new MoveCartViewScrollListener());	// 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 처리를 위한 리스너 등록
		cartViewScrollPanel.add(scrollDownButton);								// 주문 목록 view 스크롤 패널에 아래로 이동하는 스크롤 버튼 추가
		
		// 이미지 레이블
		imageLabel = new CustomLabel(new ImageIcon(imageSource));	// 이미지 아이콘을 가지는 레이블 생성
		imageLabel.setBounds(540, 740, 80, 100);					// 레이블을 (540, 740)에 위치시키고 크기는 80px * 100px로 설정 (아무 기능도 없는 임시 컴포넌트이기 때문에 setBounds() 메소드 이용)
		this.add(imageLabel);										// 패널에 이미지 레이블 추가
		
		// 주문 목록 전체 삭제 버튼
		String clearCartButtonText = "<html><div style=\"text-align:center\">전체<br>삭제</div></html>";	// 주문 목록 전체 삭제 버튼에 들어갈 텍스트
		clearCartButton = new CustomButton(clearCartButtonText);	// 텍스트를 가지는 레이블 생성
		clearCartButton.applyClearCartDesign(620, 740);				// 주문 목록 전체 삭제 버튼을 의미하는 디자인 적용
		clearCartButton.addActionListener(new ClearCartListener());	// 주문 목록 전체 삭제 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(clearCartButton);									// 패널에 주문 목록 전체 삭제 버튼 추가
		
		// 총 주문 수량 레이블
		totalQuantityLabel = new CustomLabel();						// 내용을 가지지 않는 빈 레이블 생성
		totalQuantityLabel.applyTotalQuantityDesign(540, 850);		// 총 주문 수량 레이블을 의미하는 디자인 적용
		this.add(totalQuantityLabel);								// 패널에 총 주문 수량 레이블 추가
		
		// 금액 정보가 함께 표시되는 결제 버튼
		payButton = new CustomButton();						// 내용을 가지지 않는 빈 레이블 생성
		payButton.applyPayWithAmountInfoDesign(540, 920);					// 금액 정보가 함께 표시되는 결제 버튼을 의미하는 디자인 적용
		payButton.addActionListener(new GoPayListener());	// 금액 정보가 함께 표시되는 결제 버튼 클릭 이벤트 처리를 위한 리스너 등록
		this.add(payButton);								// 패널에 금액 정보가 함께 표시되는 결제 버튼 추가
	}
	
	/* 메소드명: paintComponent
	 * 파라미터: Graphics g (컴포넌트의 그래픽 객체, 별도 선언은 필요하지 않음)
	 * 반환값: 없음
	 * 기능 설명: 컴포넌트가 그려져야 하는 시점(컴포넌트의 크기가 변경되거나 컴포넌트가 가려졌다가 다시 나타날 때)에 자동으로 컴포넌트를 다시 그린다.
	 * 		   (자동으로 호출되기 때문에 코드 상에서 직접 호출할 필요가 없다.)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);													// 부모 클래스가 그려야하는 사항을 그릴 수 있도록 부모 클래스의 paintComponent 메소드 호출
		Image backgroundImage = new ImageIcon(backgroundImageSource).getImage();	// 배경 이미지를 Image 객체로 받아옴
		g.drawImage(backgroundImage, 0, 0, this); 									// 패널의 (0, 0)을 원점으로 하여 배경 이미지를 그림
	}
	
	/* 메소드명: initialize
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 새로운 주문 진행을 위해 메뉴 패널에서 사용하는 변수와 구성 요소의 내용 및 속성을 초기화한다.
	 */
	public void initialize() {
		loadCategories();	// 카테고리 목록 읽어오기
		loadMenus();		// 메뉴 목록 읽어오기
		
		currentCategoryIndex = 0;		// 현재 가리키고 있는 카테고리의 인덱스를 0으로 초기화
		currentCategoryPage = 0;		// 현재 가리키고 있는 카테고리 페이지를 0으로 초기화
		currentMenuPage = 0;			// 현재 가리키고 있는 메뉴 페이지를 0으로 초기화
		cartViewPointer = 0;			// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스를 0으로 초기화
		
		setPropertyOfComponentRelatedToCategory();	// 카테고리와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		updateCartView();							// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		updateUtilityArea();						// 다용도 영역에 있는 컴포넌트의 속성을 변경하는 메소드 호출
	}
	
	/* 메소드명: loadCategories
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: category.txt에서 데이터를 읽어와 categories에 저장한다.
	 */
	private void loadCategories() {
		BufferedReader in;	// 입력 스트림
		
		try {
			System.out.println("MenuPanel >> loadCategories 메소드에서 카테고리 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Category.FILE_NAME));	// 파일 내용을 읽어오기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("카테고리 파일이 존재하지 않습니다.");
			return;
		}
		
		String s;		// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;	// 구분자를 기준으로 분리된 데이터가 저장될 배열
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				// 활성화 상태의 카테고리만 저장
				if(tmp[Category.INDEX_ENABLE].equals("Y")) {				// 카테고리가 활성화 상태인 경우
					String categoryId = tmp[Category.INDEX_CATEGORY_ID];	// 카테고리 ID
					String name = tmp[Category.INDEX_NAME];					// 카테고리명
					categories.put(categoryId, name);						// 카테고리 목록에 추가
				}
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			
		} catch (IOException e) {	// 입출력 예외 발생한 경우
			System.out.println("데이터 읽기에 실패하였습니다.");
		}
	}

	/* 메소드명: loadMenus
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: menu.txt에서 데이터를 읽어와 MenuDTO 객체를 생성하여 menus에 저장하고, 각 카테고리의 첫번째 메뉴의 인덱스는 firstMenuIndexOfCategories에 저장한다.
	 */
	private void loadMenus() {
		BufferedReader in;	// 입력 스트림
		
		try {
			System.out.println("MenuPanel >> loadMenuList 메소드에서 메뉴 목록을 읽어옵니다.");
			in = new BufferedReader(new FileReader(Menu.FILE_NAME));		// 파일 내용을 읽어오기 위한 스트림
			
		} catch(FileNotFoundException e) {	// 파일이 존재하지 않는 경우
			System.out.println("메뉴 파일이 존재하지 않습니다.");
			return;
		}
		
		String s;					// 파일에서 읽어온 한 줄의 데이터가 저장될 변수
		String[] tmp;				// 구분자를 기준으로 분리된 데이터가 저장될 배열
		String prevCategoryId = "";	// 이전 카테고리 ID
		int index = 0;				// 새로운 카테고리 ID가 등장할 때의 인덱스값
		
		try {
			while((s = in.readLine()) != null) {	// 읽어온 데이터가 있는 경우
				tmp = s.split("\\|");				// 구분자(|)를 기준으로 읽어온 문자열 분리
				
				String categoryId = tmp[Menu.INDEX_CATEGORY_ID];		// 카테고리 ID
				String menuNo = tmp[Menu.INDEX_MENU_NO];				// 메뉴 순번
				String name = tmp[Menu.INDEX_NAME];					// 메뉴명
				int price = Integer.parseInt(tmp[Menu.INDEX_PRICE]);	// 가격
				int stock = Integer.parseInt(tmp[Menu.INDEX_STOCK]);	// 재고
				String enabled = tmp[Menu.INDEX_ENABLE];				// 활성화 여부
				
				// 활성화 카테고리에 속한 메뉴 중에서 활성화 상태인 메뉴만 메뉴 리스트에 추가
				if(categories.get(categoryId) != null && enabled.equals("Y")) {					// 활성화 카테고리 목록에 카테고리가 있고 메뉴가 활성화 상태인 경우
					Menu menuDto = new Menu(categoryId, menuNo, name, price, stock, enabled);	// MenuDTO 객체 생성
					menus.add(menuDto);															// 메뉴 목록에 생성한 MenuDTO 객체 추가
					
					if(!categoryId.equals(prevCategoryId)) {	// 읽어온 카테고리 ID가 이전 카테고리 ID와 다르다면
						firstMenuIndexOfCategories.add(index);	// 카테고리별 첫번째 인덱스 목록에 인덱스 번호 추가
						prevCategoryId = categoryId;			// 카테고리 ID 저장
					}
					
					index++;	// 인덱스 번호 1 증가
				}
			}
			
			in.close();		// 데이터 읽기가 끝나면 스트림 닫기
			
		} catch (IOException e) {	// 입출력 예외 발생한 경우
			System.out.println("데이터 읽기에 실패하였습니다.");
		}
	}
	
	/* 메소드명: setPropertyOfComponentRelatedToCategory
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 카테고리와 관련된 컴포넌트의 속성(내용, 표시 여부, 디자인 등)을 적절하게 변경한다.
	 */
	private void setPropertyOfComponentRelatedToCategory() {
		int length = categoryButtons.length;	// 한 화면에서 보여지는 카테고리 버튼의 개수
		
		// 카테고리 페이지 이동 버튼
		// - 이전 카테고리 페이지
		if(currentCategoryPage == 0) { 					// 현재 가리키고 있는 카테고리 페이지가 0번째 페이지라면
			prevCategoryPageButton.setVisible(false);	// 이전 카테고리 페이지로 이동하는 버튼 감추기
		
		} else { 										// 현재 가리키고 있는 카테고리 페이지가 0번째 페이지가 아니라면
			prevCategoryPageButton.setVisible(true);	// 이전 카테고리 페이지로 이동하는 버튼 보여주기
		}

		// - 다음 카테고리 페이지
		if((currentCategoryPage + 1) * length >= firstMenuIndexOfCategories.size()) {	// 현재 가리키고 있는 페이지의 다음 페이지에서 보여줄 카테고리가 없는 경우
			nextCategoryPageButton.setVisible(false);									// 다음 카테고리 페이지로 이동하는 버튼 감추기
			
		} else { 										// 현재 가리키고 있는 카테고리 페이지의 다음 페이지에서 보여줄 카테고리가 있는 경우
			nextCategoryPageButton.setVisible(true);	// 다음 카테고리 페이지로 이동하는 버튼 보여주기
		}
		
		// 카테고리 버튼
		for(int i=0; i<length; i++) {
			if(currentCategoryPage * length + i < firstMenuIndexOfCategories.size()) { 								// 보여줄 카테고리가 있는 경우
				int firstMenuIndexOfCategory = firstMenuIndexOfCategories.get(currentCategoryPage * length + i);	// 카테고리별 첫번째 메뉴의 인덱스
				
				String categoryId = menus.get(firstMenuIndexOfCategory).getCategoryId();	// 카테고리 ID
				String categoryName = categories.get(categoryId);							// 카테고리명
				
				categoryButtons[i].setText(categoryName);	// 카테고리 버튼의 내용으로 카테고리명 설정
				categoryButtons[i].setVisible(true);		// 카테고리 버튼 보여주기
				
				if(currentCategoryPage * length + i == currentCategoryIndex) {	// 현재 가리키고 있는 카테고리인 경우
					categoryButtons[i].changeSelectedCategoryDesign();			// 선택된 카테고리 버튼을 의미하는 디자인 적용
					
				} else {	// 현재 가리키고 있는 카테고리가 아닌 경우
					categoryButtons[i].changeUnselectedCategoryDesign();	// 선택되지 않은 카테고리 버튼을 의미하는 디자인 적용
				}
			
			} else { 									// 보여줄 카테고리가 없는 경우
				categoryButtons[i].setVisible(false);	// 카테고리 버튼 감추기
			
			}
		}
	}
	
	/* 메소드명: setPropertyOfComponentRelatedToMenu
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 메뉴와 관련된 컴포넌트의 속성(내용, 표시 여부, 디자인 등)을 적절하게 변경한다.
	 */
	private void setPropertyOfComponentRelatedToMenu() {
		int length = menuButtons.length;	// 한 화면에서 보여지는 메뉴 버튼의 개수
		
		int startIndex = firstMenuIndexOfCategories.get(currentCategoryIndex) + currentMenuPage * length;	// 현재 메뉴 페이지에서 보여줄 첫번째 메뉴의 인덱스
		
		int menuCount;														// 현재 가리키고 있는 메뉴 페이지와 이후의 메뉴 페이지에서 보여줄 메뉴 개수
		if(currentCategoryIndex + 1 == firstMenuIndexOfCategories.size()) {	// 현재 가리키고 있는 카테고리가 마지막 카테고리라면
			menuCount = menus.size();										// 전체 메뉴 개수 저장
			
		} else {																	// 현재 가리키고 있는 카테고리가 마지막 카테고리가 아니라면
			menuCount = firstMenuIndexOfCategories.get(currentCategoryIndex + 1);	// 다음 카테고리의 첫번째 메뉴의 인덱스 저장
		}
		menuCount -= startIndex;	// 현재 카테고리의 마지막 메뉴의 인덱스 + 1 - 현재 메뉴 페이지에서 보여줄 첫번째 메뉴의 인덱스

		// 메뉴 페이지 이동 버튼
		// - 이전 메뉴 페이지
		if(currentMenuPage == 0) {					// 현재 가리키고 있는 메뉴 페이지가 0번째 페이지라면
			prevMenuPageButton.setVisible(false);	// 이전 메뉴 페이지로 이동하는 버튼 감추기
			
		} else {									// 현재 가리키고 있는 메뉴 페이지가 0번째 페이지가 아니라면
			prevMenuPageButton.setVisible(true);	// 이전 메뉴 페이지로 이동하는 버튼 보여주기
		}
		
		// - 다음 메뉴 페이지
		if(menuCount <= length) {					// 다음 메뉴 페이지에서 보여줄 메뉴가 없는 경우	
			nextMenuPageButton.setVisible(false);	// 다음 메뉴 페이지로 이동하는 버튼 감추기
			
		} else {									// 다음 메뉴 페이지에서 보여줄 메뉴가 있는 경우
			nextMenuPageButton.setVisible(true);	// 다음 메뉴 페이지로 이동하는 버튼 보여주기
		}
		
		// 메뉴 버튼
		for(int i=0; i<length; i++) {
			if(i < menuCount) {		// 화면에 표시할 메뉴가 있는 경우
				String menuButtonText = "<html><div style=\"text-align:center\">";	// 메뉴 버튼에 들어갈 텍스트에 여는 태그 추가
				
				int index = startIndex + i;					// 표시할 메뉴의 인덱스
				String name = menus.get(index).getName();	// 메뉴명
				int price = menus.get(index).getPrice();	// 가격
				int stock = menus.get(index).getStock();	// 재고
				
				menuButtonText += name + "<br>";		// 메뉴명과 개행 추가
				
				if(stock == 0) {						// 재고가 없는 메뉴인 경우
					menuButtonText += "품절";				// "품절" 문구 추가
					menuButtons[i].setEnabled(false);	// 메뉴 버튼 비활성화
					
				} else {											// 재고가 있는 메뉴인 경우
					menuButtonText += "<font color=#046240>";		// 가격을 나타내는 전경색 지정
					menuButtonText += String.format("%,d", price);	// 가격 추가
					menuButtonText += "원</font>";					// "원" 문구 추가
					
					menuButtons[i].setEnabled(true);				// 메뉴 버튼 활성화
				}
				
				menuButtonText += "</div></html>";			// 메뉴 버튼에 들어갈 텍스트에 닫는 태그 추가
				menuButtons[i].setText(menuButtonText);		// 메뉴 버튼의 텍스트 설정
				
				String menuIconSource = menuIconDirectory + menus.get(index).getCategoryId() + menus.get(index).getMenuNo() + ".jpg";	// 메뉴 이미지 아이콘 경로
				if(!new File(menuIconSource).exists()) menuIconSource = menuIconDirectory + "default.png";								// 해당되는 메뉴 이미지 아이콘이 없는 경우 기본 이미지로 지정
				
				menuButtons[i].setIcon(new ImageIcon(menuIconSource));			// 버튼의 아이콘 지정
				menuButtons[i].setDisabledIcon(new ImageIcon(menuIconSource));	// 비활성화 상태의 버튼의 아이콘 지정
				
				menuButtons[i].setVisible(true);	// 메뉴 버튼 보여주기
			
			} else {								// 화면에 표시할 메뉴가 없는 경우
				menuButtons[i].setVisible(false);	// 메뉴 버튼 감추기
			}
		}
	}

	/* 메소드명: updateCartView
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 주문 목록 view와 관련된 컴포넌트의 속성(내용, 표시 여부, 디자인 등)을 적절하게 변경한다.
	 */
	private void updateCartView() {
		for(int i=0; i<5; i++) {
			if(cartViewPointer + i < cart.size()) {		// 주문 목록 view에서 보여줄 아이템이 있는 경우
				// 메뉴명 레이블
				menuNameLabels[i].setText(cart.get(cartViewPointer + i).getMenuName());		// 메뉴명 레이블 텍스트 변경
				menuNameLabels[i].setVisible(true);											// 메뉴명 레이블 보여주기
				
				// 수량 레이블
				quantityLabels[i].setText(cart.get(cartViewPointer + i).getQuantity() + "");	// 수량 레이블 텍스트 변경
				quantityLabels[i].setVisible(true);												// 수량 레이블 보여주기
				
				// 금액 레이블
				totalPriceLabels[i].setText(String.format("%,d원", cart.get(cartViewPointer + i).getTotalPrice()));	// 금액 레이블 텍스트 변경
				totalPriceLabels[i].setVisible(true);																// 금액 레이블 보여주기
				
				deleteCartItemButtons[i].setVisible(true);		// 주문 목록 아이템 삭제 버튼 보여주기
				decreaseQuantityButtons[i].setVisible(true);	// 수량 감소 버튼 보여주기
				increaseQuantityButtons[i].setVisible(true);	// 수량 증가 버튼 보여주기
				
				int menuIndex = cart.get(cartViewPointer + i).getMenuIndex();	// 주문 목록에 담긴 아이템의 메뉴 인덱스
				
				if(menus.get(menuIndex).getStock() == 0) {			// 추가 주문 가능한 수량이 없는 경우
					increaseQuantityButtons[i].setEnabled(false);	// 수량 증가 버튼 비활성화
					
				} else {											// 추가 주문 가능한 수량이 있는 경우
					increaseQuantityButtons[i].setEnabled(true);	// 수량 증가 버튼 활성화
				}
				increaseQuantityButtons[i].changeIncreaseQuantityDesignByEnabledState();	// 수량 증가 버튼의 활성화 여부에 따라 디자인 변경
				
				
			} else {											// 주문 목록 view에서 보여줄 아이템이 없는 경우
				menuNameLabels[i].setVisible(false);			// 메뉴명 레이블 감추기
				quantityLabels[i].setVisible(false);			// 수량 레이블 감추기
				totalPriceLabels[i].setVisible(false);			// 금액 레이블 감추기
				deleteCartItemButtons[i].setVisible(false);		// 주문 목록 아이템 삭제 버튼 감추기
				decreaseQuantityButtons[i].setVisible(false);	// 수량 감소 버튼 감추기
				increaseQuantityButtons[i].setVisible(false);	// 수량 증가 버튼 감추기
			}
			
		}
		
		// 주문 목록 view 스크롤 이동 버튼
		// - 위로 이동
		if(cartViewPointer == 0) {				// 주문 목록 view에서 보여지는 첫번째 아이템 이전에 아이템이 없는 경우
			scrollUpButton.setEnabled(false);	// 주문 목록 view 스크롤 패널에 위로 이동하는 스크롤 버튼 비활성화
			
		} else {								// 주문 목록 view에서 보여지는 첫번째 아이템 이전에도 아이템이 더 있는 경우
			scrollUpButton.setEnabled(true);	// 주문 목록 view 스크롤 패널에 위로 이동하는 스크롤 버튼 활성화
		}
		
		// - 아래로 이동
		if(cartViewPointer + 5 < cart.size()) {		// 주문 목록 view에서 보여지는 마지막 아이템 이후에도 아이템이 더 있는 경우
			scrollDownButton.setEnabled(true);		// 주문 목록 view 스크롤 패널에 아래로 이동하는 스크롤 버튼 비활성화
		
		} else {								// 주문 목록 view에서 보여지는 마지막 아이템 이후에 아이템이 없는 경우
			scrollDownButton.setEnabled(false);	// 주문 목록 view 스크롤 패널에 아래로 이동하는 스크롤 버튼 활성화
		}
	}
	
	/* 메소드명: updateUtilityArea
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 기능 설명: 다용도 영역에 있는 컴포넌트(주문 목록 전체 삭제 버튼, 총 주문 수량 레이블, 금액 정보가 함께 표시되는 결제 버튼)의 속성(내용, 표시 여부, 디자인 등)을 적절하게 변경한다.
	 */
	private void updateUtilityArea() {
		totalQuantityLabel.setText("주문 수량: " + CartedMenu.getTOTAL_QUANTITY());	// 총 주문 수량 레이블 텍스트 변경
		
		String payButtonText = "<html><div style=\"text-align:center\">";		// 금액 정보가 함께 표시되는 결제 버튼에 들어갈 텍스트에 여는 태그 추가
		payButtonText += String.format("%,d원", CartedMenu.getTOTAL_AMT());		// 총 금액 추가
		payButtonText += "<br>결제하기</div></html>";								// 금액 정보가 함께 표시되는 결제 버튼에 들어갈 텍스트에 개행 및 결제하기 문구, 닫는 태그 추가
		payButton.setText(payButtonText);										// 금액 정보가 함께 표시되는 결제 버튼 텍스트 변경
		
		if(cart.size() == 0) {						// 주문 목록에 담긴 메뉴의 개수가 0이면
			clearCartButton.setEnabled(false);		// 주문 목록 전체 삭제 버튼 비활성화
			payButton.setEnabled(false);			// 금액 정보가 함께 표시되는 결제 버튼 비활성화
			
			
		} else {								// 주문 목록에 담긴 메뉴의 개수가 0이 아니라면
			clearCartButton.setEnabled(true);	// 주문 목록 전체 삭제 버튼 활성화
			payButton.setEnabled(true);			// 금액 정보가 함께 표시되는 결제 버튼 활성화
		}
		
		clearCartButton.changeClearCartDesignByEnabledState();	// 주문 목록 전체 삭제 버튼의 활성화 여부에 따라 디자인 변경
		payButton.changePayDesignByEnabledState();				// 금액 정보가 함께 표시되는 결제 버튼의 활성화 여부에 따라 디자인 변경
		
	}
	
	/* 클래스명: GoMainListener
	 * 설명: 메인 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class GoMainListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 메인 버튼 클릭 이벤트 발생 시 사용자 모드에서 진행한 모든 내용을 초기화(삭제)하고 메인을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(cart.size() == 0) {		// 주문 목록이 비어 있다면
				System.out.println("=======================================");
				System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(메뉴 패널) :: 메인으로 돌아갑니다.");
				
				UserModePanel.initialize();		// 사용자 모드 패널의 initialize() 메소드 호출
				
			} else {							// 주문 목록이 비어있지 않다면
				new GoMainConfirmDialog();		// 장바구니 초기화 안내를 위한 다이얼로그 호출
			}

		}
		
	}
	
	/* 클래스명: MoveCategoryPageListener
	 * 설명: 카테고리 페이지 이동 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class MoveCategoryPageListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 카테고리 페이지 이동 버튼 클릭 이벤트 발생 시 이동한 카테고리 페이지에 맞는 카테고리를 보여준다.
		 * 			(카테고리 페이지를 이동할 경우, 가장 마지막에 선택된 카테고리에 포커스가 유지된다.)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			if(button == prevCategoryPageButton) {	// 이전 카테고리 페이지로 이동 버튼 클릭 시
				currentCategoryPage --;				// 현재 가리키고 있는 카테고리 페이지에서 -1

			} else { 					// 다음 카테고리 페이지로 이동 버튼 클릭 시
				currentCategoryPage++;	// 현재 가리키고 있는 카테고리 페이지에서 +1
			}
			
			setPropertyOfComponentRelatedToCategory();	// 카테고리와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}

	/* 클래스명: SelectCategoryListener
	 * 설명: 카테고리 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class SelectCategoryListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 카테고리 버튼 클릭 이벤트 발생 시 선택된 카테고리에 맞는 메뉴 목록을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int length = categoryButtons.length;					// 한 화면에서 보여지는 카테고리 버튼의 개수
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			currentMenuPage = 0;	// 현재 가리키고 있는 메뉴 페이지를 0으로 초기화
			
			for(int i=0; i<length; i++) {
				if(button == categoryButtons[i]) {								// 클릭 이벤트가 발생한 카테고리 버튼이라면
					categoryButtons[i].changeSelectedCategoryDesign();			// 선택된 카테고리 버튼을 의미하는 디자인 적용
					currentCategoryIndex = currentCategoryPage * length + i;	// 현재 가리키고 있는 카테고리의 인덱스 업데이트 
					
				} else { 														// 클릭 이벤트가 발생하지 않는 카테고리 버튼이라면
					categoryButtons[i].changeUnselectedCategoryDesign();		// 선택되지 않은 카테고리 버튼을 의미하는 디자인 적용
				}
				
			}
			
			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: MoveMenuPageListener
	 * 설명: 메뉴 페이지 이동 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class MoveMenuPageListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 메뉴 페이지 이동 버튼 클릭 이벤트 발생 시 이동한 메뉴 페이지에 맞는 메뉴 목록을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			if(button == prevMenuPageButton) {	// 이전 메뉴 페이지로 이동 버튼 클릭 시
				currentMenuPage--;				// 현재 가리키고 있는 메뉴 페이지에서 -1
				
			} else { 				// 다음 메뉴 페이지로 이동 버튼 클릭 시
				currentMenuPage++;	// 현재 가리키고 있는 메뉴 페이지에서 +1
			}
			
			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}

	/* 클래스명: AddMenuListener
	 * 설명: 메뉴 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class AddMenuListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 메뉴 버튼 클릭 이벤트 발생 시 주문 목록에 메뉴를 추가한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int length = menuButtons.length; 						// 한 화면에서 보여지는 메뉴 버튼의 개수
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			if(CartedMenu.getTOTAL_QUANTITY() == maxOrderableQuantity) {	// 현재 총 주문 수량이 최대 주문 가능한 수량이라면
				new AddMenuFailAlertDialog();								// 메뉴 추가 실패 안내를 위한 다이얼로그 호출
				return;														// 메소드 종료
			}
			
			int index = -1;		// 메뉴 인덱스 (*0번째 메뉴와의 혼선 여지가 있어 -1로 초기화)
			
			for(int i=0; i<length; i++) {
				if(button == menuButtons[i]) {	// 클릭 이벤트가 발생한 메뉴 버튼이라면
					index = firstMenuIndexOfCategories.get(currentCategoryIndex) + currentMenuPage * menuButtons.length + i;	// 메뉴 인덱스 계산
					
					menus.get(index).setStock(menus.get(index).getStock() - 1);				// 선택된 메뉴의 재고 1 차감

					break;		// 반복문 종료 (*2개 이상의 메뉴 버튼이 동시에 클릭될 수 없기 때문)
				}
			}

			boolean isCartedMenu = false;		// 이미 주문 목록에 추가되어 있는 메뉴인지를 나타내는 변수 (true: 추가되어 있음, false: 추가되어 있지 않음)

			for(CartedMenu cartedMenuDto : cart) {
				String addMenuId = menus.get(index).getCategoryId() + menus.get(index).getMenuNo();	// 추가될 메뉴
				String cartedMenuId = cartedMenuDto.getCategoryId() + cartedMenuDto.getMenuNo();	// 주문 목록에 있는 메뉴
				
				if(addMenuId.equals(cartedMenuId)) {	// 동일한 메뉴가 추가된 경우라면
					isCartedMenu = true;				// 이미 주문 목록에 추가되어 있는 메뉴임을 나타내도록 변경
					cartedMenuDto.updateQuantity(1);	// 이미 주문 목록에 추가된 메뉴의 주문 수량 1 증가
					
					break;		// 반복문 종료 (*2개 이상의 메뉴 버튼이 동시에 클릭될 수 없기 때문)
				}
			}
			
			if(!isCartedMenu) {											// 새로 추가된 메뉴라면
				cart.add(new CartedMenu(index, menus.get(index), 1));	// 주문 목록에 메뉴 추가
				
				if(cart.size() > 5) {					// 주문 목록에 담긴 메뉴 개수가 5개를 넘는 경우
					cartViewPointer = cart.size() - 5;	// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스가 뒤에서 5번째를 가리키도록 함
				}
			}

			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateCartView();							// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateUtilityArea();						// 다용도 영역에 있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: DeleteCartItemListener
	 * 설명: 주문 목록 아이템 삭제 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class DeleteCartItemListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 주문 목록 아이템 삭제 버튼 클릭 이벤트 발생 시 주문 목록에서 메뉴를 삭제한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			for(int i=0; i<5; i++) {
				if(button == deleteCartItemButtons[i]) {						// 클릭 이벤트가 발생한 주문 목록 아이템 삭제 버튼이라면
					int cartIndex = cartViewPointer + i;						// 주문 목록 아이템 인덱스
					int quantity = cart.get(cartViewPointer + i).getQuantity();	// 주문 수량
					
					menus.get(cart.get(cartIndex).getMenuIndex()).setStock(menus.get(cart.get(cartIndex).getMenuIndex()).getStock() + quantity);	// 메뉴 재고 업데이트
					
					cart.get(cartIndex).updateQuantity(-quantity);	// 수량 업데이트
					cart.remove(cartIndex);							// 주문 목록에서 메뉴 삭제

					break;		// 반복문 종료 (*2개의 주문 목록 아이템 삭제 버튼이 동시에 클릭될 수 없기 때문)
				}
			}
			
			if(cart.size() <= 5) {		// 주문 목록에 담긴 메뉴 개수가 5개 이하면
				cartViewPointer = 0;	// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스는 0으로 초기화
				
			} else if(cartViewPointer + 5 > cart.size()){	// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스 +5의 결과값이 주문 목록에 담긴 메뉴 개수보다 크다면
				cartViewPointer--;							// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스 1 감소
			}
			
			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateCartView();							// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateUtilityArea();						// 다용도 영역에 있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: DecreaseQuantityListener
	 * 설명: 수량 감소 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class DecreaseQuantityListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 수량 감소 버튼 클릭 이벤트 발생 시 주문 목록에 담긴 메뉴의 수량을 감소한다.
		 * 			감소된 수량이 0이 되면 주문 목록에서 메뉴를 삭제한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			for(int i=0; i<5; i++) {
				if(button == decreaseQuantityButtons[i]) {						// 클릭 이벤트가 발생한 수량 감소 버튼이라면
					int cartIndex = cartViewPointer + i;						// 주문 목록 아이템 인덱스

					menus.get(cart.get(cartIndex).getMenuIndex()).setStock(menus.get(cart.get(cartIndex).getMenuIndex()).getStock() + 1);	// 메뉴 재고 1 증가
					
					cart.get(cartIndex).updateQuantity(-1);			// 수량 1 감소

					if(cart.get(cartIndex).getQuantity() == 0) {	// 수량이 0이 된 경우
						cart.remove(cartIndex);						// 주문 목록에서 메뉴 삭제
					}
					
					break;		// 반복문 종료 (*2개의 주문 목록 아이템 삭제 버튼이 동시에 클릭될 수 없기 때문)
				}
			}
			
			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateCartView();							// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateUtilityArea();						// 다용도 영역에 있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: IncreaseQuantityListener
	 * 설명: 수량 증가 버튼이 클릭 이벤트 처리를 위한 리스너
	 */
	private class IncreaseQuantityListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 수량 증가 버튼 클릭 이벤트 발생 시 주문 목록에 담긴 메뉴의 수량을 증가한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			for(int i=0; i<5; i++) {
				if(button == increaseQuantityButtons[i]) {						// 클릭 이벤트가 발생한 수량 증가 버튼이라면
					int cartIndex = cartViewPointer + i;						// 주문 목록 아이템 인덱스
					
					if(CartedMenu.getTOTAL_QUANTITY() == maxOrderableQuantity) {	// 현재 총 주문 수량이 최대 주문 가능한 수량이라면
						new AddMenuFailAlertDialog();								// 메뉴 추가 실패 안내를 위한 다이얼로그 호출
						return;														// 메소드 종료
					}
					
					menus.get(cart.get(cartIndex).getMenuIndex()).setStock(menus.get(cart.get(cartIndex).getMenuIndex()).getStock() - 1);	// 메뉴 재고 1 감소
					
					cart.get(cartIndex).updateQuantity(1);			// 수량 1 증가
					
					break;		// 반복문 종료 (*2개의 주문 목록 아이템 삭제 버튼이 동시에 클릭될 수 없기 때문)
				}
			}
			
			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateCartView();							// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateUtilityArea();						// 다용도 영역에 있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: ClearCartListener
	 * 설명: 주문 목록 전체 삭제 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class ClearCartListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 주문 목록 전체 삭제 버튼 클릭 이벤트 발생 시 주문 목록에 담긴 메뉴를 모두 삭제한다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			for(CartedMenu cartedMenuDto : cart) {
				int quantity = cartedMenuDto.getQuantity();		// 주문 수량
				
				menus.get(cartedMenuDto.getMenuIndex()).setStock(menus.get(cartedMenuDto.getMenuIndex()).getStock() + quantity);	// 주문 수량만큼 메뉴 재고 증가
			}
			
			cart.clear();					// 주문 목록 초기화
			CartedMenu.initialize();		// CartedMenuDTO의 static 변수 초기화
			cartViewPointer = 0;			// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스를 0으로 초기화
			
			setPropertyOfComponentRelatedToMenu();		// 메뉴와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateCartView();							// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
			updateUtilityArea();						// 다용도 영역에 있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
	}
	
	/* 클래스명: MoveCartViewScrollListener
	 * 설명: 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class MoveCartViewScrollListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 주문 목록 view 스크롤 이동 버튼 클릭 이벤트 발생 시 이동된 위치에 맞는 주문 목록을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CustomButton button = (CustomButton)e.getSource();		// 액션 이벤트가 발생한 객체를 CustomButton 타입으로 형변환
			
			if(button == scrollUpButton) {	// 위로 이동하는 스크롤 버튼 클릭 시
				cartViewPointer--;			// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스 1 감소
				
			} else {					// 아래로 이동하는 스크롤 버튼 클릭 시
				cartViewPointer++;		// 주문 목록 view에서 가장 처음에 있는 아이템의 인덱스 1 증가
			}
			
			updateCartView();	// 주문 목록 view와 관련있는 컴포넌트의 속성을 변경하는 메소드 호출
		}
		
	}
	
	/* 클래스명: GoPayListener
	 * 설명: 금액 정보가 함께 표시되는 결제 버튼 클릭 이벤트 처리를 위한 리스너
	 */
	private class GoPayListener implements ActionListener {
		/* 메소드명: actionPerformed
		 * 파라미터: ActionEvent e (액션 이벤트가 발생한 객체)
		 * 반환값: 없음
		 * 기능 설명: 금액 정보가 함께 표시되는 결제 버튼 클릭 이벤트 발생 시 이동된 위치에 맞는 포인트/쿠폰 사용 여부 선택 패널을 보여준다.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(Calendar.getInstance().getTime().toString() + " :: 사용자 모드(메뉴 패널) :: 포인트/쿠폰 사용 여부 선택 패널로 넘어갑니다.");
			
			setVisible(false);												// 메뉴 패널 감추기
			UserModePanel.selectUsageOfPointOrCouponPanel.setVisible(true);	// 포인트/쿠폰 선택 패널 보여주기
		}
		
	}
}
