package kr.ac.dankook.autoinfo.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import kr.ac.dankook.autoinfo.models.Post;


public class FirebasePostManager {

    private static FirebasePostManager instance;

    private final FirebaseFirestore db;
    private final CollectionReference postsRef;

    private FirebasePostManager() {
        db = FirebaseFirestore.getInstance();
        postsRef = db.collection("posts");
    }

    public static FirebasePostManager getInstance() {
        if (instance == null) {
            instance = new FirebasePostManager();
        }
        return instance;
    }

    public void createPost(@NonNull Post post,
                           @NonNull OnPostResultListener listener) {

        postsRef.add(post)
                .addOnSuccessListener(docRef -> {
                    String generatedId = docRef.getId();
                    post.setId(generatedId);

                    listener.onSuccess();
                })
                .addOnFailureListener(listener::onError);
    }


    public void getAllPosts(@NonNull OnPostsResultListener listener) {
        postsRef
                .orderBy("price", Query.Direction.DESCENDING) // 예시로 가격 내림차순
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


    public interface OnPostResultListener {
        void onSuccess();
        void onError(Exception e);
    }

    public interface OnPostsResultListener {
        void onSuccess(List<Post> posts);
        void onError(Exception e);
    }
}