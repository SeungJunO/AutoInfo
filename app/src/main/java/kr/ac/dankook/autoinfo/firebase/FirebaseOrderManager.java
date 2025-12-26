package kr.ac.dankook.autoinfo.firebase;

import kr.ac.dankook.autoinfo.models.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;


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

    public void createOrder(Order order) {
        ordersRef.add(order);
    }

    public interface OnOrdersResultListener {
        void onSuccess(List<Order> orders);
        void onError(Exception e);
    }

    public void getOrdersByBuyer(@NonNull String buyerId,
                                 @NonNull OnOrdersResultListener listener) {

        ordersRef.whereEqualTo("buyerId", buyerId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Order> result = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Order order = doc.toObject(Order.class);
                        result.add(order);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(listener::onError);
    }


}
