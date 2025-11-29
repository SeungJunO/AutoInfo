package kr.ac.dankook.autoinfo.firebase;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthManager {

    private static FirebaseAuthManager instance;
    private final FirebaseAuth auth;

    private FirebaseAuthManager() {
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthManager getInstance() {
        if (instance == null) instance = new FirebaseAuthManager();
        return instance;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public void signOut() {
        auth.signOut();
    }

    // -----------------------------
    // 로그인
    // -----------------------------
    public void signInWithEmail(String email, String pass, Activity a, Runnable onSuccess) {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(result -> {
                    Toast.makeText(a, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    onSuccess.run();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(a, "로그인 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // -----------------------------
    // 회원가입
    // -----------------------------
    public void signUpWithEmail(String email, String pass, Activity a, Runnable onSuccess) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(result -> {
                    Toast.makeText(a, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    onSuccess.run();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(a, "회원가입 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
