// app/src/main/java/kr/ac/dankook/autoinfo/firebase/FirebaseOrderManager.java
package kr.ac.dankook.autoinfo.firebase;

import kr.ac.dankook.autoinfo.models.Order;

public class FirebaseOrderManager {
    private static FirebaseOrderManager instance;
    private FirebaseOrderManager(){}
    public static FirebaseOrderManager getInstance(){
        if(instance==null) instance = new FirebaseOrderManager();
        return instance;
    }
    public void createOrder(Order o, Runnable onComplete){}
}