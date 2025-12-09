// ==== FirebasePostManager.java (수정본 전체 복붙) ====
package kr.ac.dankook.autoinfo.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import kr.ac.dankook.autoinfo.models.Post;

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
    public void createPost(@NonNull Post post,
                           @NonNull OnPostResultListener listener) {

        // id 필드는 Firestore 문서 ID로 관리 → 저장할 땐 빼도 됨
        // (그냥 객체 전체를 넣어도 되지만 깔끔하게 하려면 Map으로 빼는 방법도 있음)
        postsRef.add(post)
                .addOnSuccessListener(docRef -> {
                    // 생성된 문서 ID를 Post 객체에도 넣고 싶으면:
                    String generatedId = docRef.getId();
                    post.setId(generatedId);
                    // Firestore 필드에도 id 넣고 싶으면 이 줄 추가:
                    // docRef.update("id", generatedId);

                    listener.onSuccess();
                })
                .addOnFailureListener(listener::onError);
    }

    // -------------------------------
    // 2) 마켓에서 쓸 전체 인포 목록 조회 (예: 가격순 정렬)
    // -------------------------------
    public void getAllPosts(@NonNull OnPostsResultListener listener) {
        postsRef
                .orderBy("price", Query.Direction.DESCENDING) // 예시로 가격 내림차순
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Post> result = new ArrayList<>();

                    // ⚠ 안드로이드는 var 못 씀 → DocumentSnapshot으로 명시
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Post post = doc.toObject(Post.class);
                        if (post != null) {
                            // Firestore 문서 ID를 객체의 id 필드에 넣어줌
                            post.setId(doc.getId());
                            result.add(post);
                        }
                    }

                    listener.onSuccess(result);
                })
                .addOnFailureListener(listener::onError);
    }

    // -------------------------------
    // 3) 특정 판매자(authorId 기준)의 인포만 조회
    //    (지금 Post에 authorId 있음)
    // -------------------------------
    public void getPostsByAuthorId(@NonNull String authorId,
                                   @NonNull OnPostsResultListener listener) {
        postsRef
                .whereEqualTo("authorId", authorId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Post> result = new ArrayList<>();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Post post = doc.toObject(Post.class);
                        if (post != null) {
                            post.setId(doc.getId());
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
        void onSuccess(List<Post> posts);
        void onError(Exception e);
    }
}
// ==== 여기까지 복붙 ====
