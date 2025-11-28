package kr.ac.dankook.autoinfo.model;

// 인포(정보 상품) 하나를 표현하는 클래스
public class InfoPost {

    private String id;          // 인포 ID (나중에 Firestore 문서 ID 등으로 사용 가능)
    private String title;       // 인포 제목
    private String description; // 인포 설명
    private String category;    // 카테고리 (마케팅, 재테크 등)
    private int price;          // 가격 (원 단위 정수)
    private String link;        // 원본 링크 (상품/정보 페이지 링크)
    private String sellerName;  // 판매자 닉네임 (일단 테스트용으로 고정값 써도 됨)

    public InfoPost() {
        // Firebase 쓸 때를 대비한 기본 생성자
    }

    public InfoPost(String id, String title, String description,
                    String category, int price, String link, String sellerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.link = link;
        this.sellerName = sellerName;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public String getSellerName() {
        return sellerName;
    }
}
// === 여기까지 복붙 ===
