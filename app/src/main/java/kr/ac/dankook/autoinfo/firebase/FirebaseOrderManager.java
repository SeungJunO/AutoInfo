package kr.ac.dankook.autoinfo.firebase;

import kr.ac.dankook.autoinfo.models.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseOrderManager {

    private static FirebaseOrderManager instance;
    private final FirebaseFirestore db;
    private final CollectionReference ordersRef;

    private FirebaseOrderManager() {
        db = FirebaseFirestore.getInstance();
        ordersRef = db.collection("orders");
    }

    public static FirebaseOrderManager getInstance() {
        if (instance == null) {
            instance = new FirebaseOrderManager();
        }
        return instance;
    }

    // 주문 생성
    public void createOrder(Order order) {
        ordersRef.add(order);
    }

    // 내 구매함 불러오기: buyerId 기준으로 쿼리하는 메소드 나중에 추가
}
// === 여기까지 복붙 ===
