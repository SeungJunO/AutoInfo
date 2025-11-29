package kr.ac.dankook.autoinfo.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import kr.ac.dankook.autoinfo.models.InfoPost;

/**
 * 인포(정보 상품) 관련 Firestore 접근 전담 매니저
 * - posts 컬렉션에 저장 / 조회 / 필터 처리
 */
public class FirebasePostManager {

    private static FirebasePostManager instance;

    private final FirebaseFirestore db;
    private final CollectionReference postsRef;

    // 싱글톤: 외부에서 new 못하게 private 생성자
    private FirebasePostManager() {
        db = FirebaseFirestore.getInstance();
        postsRef = db.collection("posts"); // Firestore 컬렉션 이름: posts
    }

    // 전역에서 하나만 쓰기 위한 getInstance()
    public static FirebasePostManager getInstance() {
        if (instance == null) {
            instance = new FirebasePostManager();
        }
        return instance;
    }

    // -------------------------------
    // 1) 인포 등록 (Create)
    // -------------------------------
    public void createPost(InfoPost post, @NonNull OnPostResultListener listener) {
        // Firestore에 문서 추가 (ID는 Firestore가 자동으로 생성)
        postsRef.add(post)
                .addOnSuccessListener(docRef -> {
                    // 방금 생성된 문서의 ID를 InfoPost에도 반영하고 싶으면:
                    String generatedId = docRef.getId();
                    // 여기서 필요하면 docRef.update("id", generatedId); 같은 것도 가능

                    listener.onSuccess();
                })
                .addOnFailureListener(listener::onError);
    }

    // -------------------------------
    // 2) 마켓에서 쓸 전체 인포 목록 조회 (status 조건 없이 전체)
    //    필요하면 나중에 PUBLIC만, 카테고리별 등으로 확장
    // -------------------------------
    public void getAllPosts(@NonNull OnPostsResultListener listener) {
        postsRef
                .orderBy("price", Query.Direction.DESCENDING) // 예시로 가격순 정렬
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<InfoPost> result = new ArrayList<>();

                    for (var doc : querySnapshot.getDocuments()) {
                        InfoPost post = doc.toObject(InfoPost.class);
                        if (post != null) {
                            // 필요하면 doc.getId()를 post.setId(...)로 넣을 수도 있음
                            result.add(post);
                        }
                    }

                    listener.onSuccess(result);
                })
                .addOnFailureListener(listener::onError);
    }

    // -------------------------------
    // 3) 특정 판매자(닉네임 기준)의 인포만 조회 (내 인포 관리용)
    //    지금 InfoPost에 sellerId가 없어서 sellerName으로 필터
    // -------------------------------
    public void getPostsBySellerName(@NonNull String sellerName,
                                     @NonNull OnPostsResultListener listener) {
        postsRef
                .whereEqualTo("sellerName", sellerName)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<InfoPost> result = new ArrayList<>();

                    for (var doc : querySnapshot.getDocuments()) {
                        InfoPost post = doc.toObject(InfoPost.class);
                        if (post != null) {
                            result.add(post);
                        }
                    }

                    listener.onSuccess(result);
                })
                .addOnFailureListener(listener::onError);
    }

    // ========================================
    // 콜백 인터페이스 정의
    // ========================================

    // 단일 성공/실패 (등록 등)
    public interface OnPostResultListener {
        void onSuccess();
        void onError(Exception e);
    }

    // 리스트 반환 타입 (목록 조회 등)
    public interface OnPostsResultListener {
        void onSuccess(List<InfoPost> posts);
        void onError(Exception e);
    }
}
// === 여기까지 복붙 ===
