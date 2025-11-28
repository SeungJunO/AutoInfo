// app/src/main/java/kr/ac/dankook/autoinfo/firebase/FirebaseAuthManager.java
package kr.ac.dankook.autoinfo.firebase;

import android.app.Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthManager {
    private static FirebaseAuthManager instance;
    private FirebaseAuth auth;
    private FirebaseAuthManager(){
        auth = FirebaseAuth.getInstance();
    }
    public static FirebaseAuthManager getInstance(){
        if(instance==null) instance = new FirebaseAuthManager();
        return instance;
    }
    public FirebaseUser getCurrentUser(){ return auth.getCurrentUser(); }
    public void signOut(){ auth.signOut(); }
    public void signInWithEmail(String email, String pass, Activity a, Runnable onSuccess){}
    public void signUpWithEmail(String email, String pass, Activity a, Runnable onSuccess){}
}