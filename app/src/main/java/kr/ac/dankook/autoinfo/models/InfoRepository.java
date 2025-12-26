package kr.ac.dankook.autoinfo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfoRepository {

    private static InfoRepository instance;
    private final List<InfoPost> posts = new ArrayList<>();

    private InfoRepository() {
    }

    public static InfoRepository getInstance() {
        if (instance == null) {
            instance = new InfoRepository();
        }
        return instance;
    }

    public InfoPost addPost(String title, String desc, String category,
                            int price, String link, String sellerName) {
        String id = UUID.randomUUID().toString(); // 랜덤 ID
        InfoPost post = new InfoPost(id, title, desc, category, price, link, sellerName);
        posts.add(post);
        return post;
    }

    public List<InfoPost> getAllPosts() {
        return new ArrayList<>(posts);
    }

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
