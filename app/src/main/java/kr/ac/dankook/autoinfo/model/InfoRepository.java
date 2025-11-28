// === 여기부터 복붙: InfoRepository.java ===
package kr.ac.dankook.autoinfo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 인포 목록을 관리하는 싱글톤 저장소 (임시 메모리 버전)
public class InfoRepository {

    private static InfoRepository instance;
    private final List<InfoPost> posts = new ArrayList<>();

    private InfoRepository() {
        // 테스트용 더미 데이터 몇 개 미리 넣어도 됨
        // addPost("인스타 리셀링 노하우", "인스타 계정 키우는 방법 총정리", "마케팅", 19000, "https://example.com/1", "테스트판매자");
    }

    public static InfoRepository getInstance() {
        if (instance == null) {
            instance = new InfoRepository();
        }
        return instance;
    }

    // 새 인포 추가
    public InfoPost addPost(String title, String desc, String category,
                            int price, String link, String sellerName) {
        String id = UUID.randomUUID().toString(); // 랜덤 ID
        InfoPost post = new InfoPost(id, title, desc, category, price, link, sellerName);
        posts.add(post);
        return post;
    }

    // 전체 인포 목록 반환
    public List<InfoPost> getAllPosts() {
        return new ArrayList<>(posts); // 원본 보호용 복사본
    }

    // 내가 등록한 인포만 필터 (지금은 sellerName 기준, 나중엔 UID 기준)
    public List<InfoPost> getPostsBySeller(String sellerName) {
        List<InfoPost> result = new ArrayList<>();
        for (InfoPost p : posts) {
            if (p.getSellerName().equals(sellerName)) {
                result.add(p);
            }
        }
        return result;
    }

    public int getPostCountBySeller(String sellerName) {
        int cnt = 0;
        for (InfoPost p : posts) {
            if (p.getSellerName().equals(sellerName)) {
                cnt++;
            }
        }
        return cnt;
    }
}
// === 여기까지 복붙 ===
