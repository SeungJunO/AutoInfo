// ⬇⬇⬇ 여기부터 전체 복붙 (FirebaseAuthManager.java 파일 통째로 교체)

package kr.ac.dankook.autoinfo.firebase;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FirebaseAuthManager {

    private static FirebaseAuthManager instance;
    private FirebaseAuth auth;

    private FirebaseAuthManager() {
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthManager getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthManager();
        }
        return instance;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    // 로그아웃
    public void signOut() {
        auth.signOut();
    }

    // 이메일/비밀번호 로그인
    public void signInWithEmail(String email, String pass, Activity activity, Runnable onSuccess) {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(activity, "로그인 성공!", Toast.LENGTH_SHORT).show();
                            if (onSuccess != null) onSuccess.run();
                        } else {
                            // 로그인 실패
                            String msg = "로그인 실패";
                            if (task.getException() != null) {
                                msg += ": " + task.getException().getMessage();
                            }
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 이메일/비밀번호 회원가입
    public void signUpWithEmail(String email, String pass, Activity activity, Runnable onSuccess) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(activity, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                            if (onSuccess != null) onSuccess.run();
                        } else {
                            // 회원가입 실패
                            String msg = "회원가입 실패";
                            if (task.getException() != null) {
                                msg += ": " + task.getException().getMessage();
                            }
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
