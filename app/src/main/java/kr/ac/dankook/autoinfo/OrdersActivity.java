package kr.ac.dankook.autoinfo;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import kr.ac.dankook.autoinfo.firebase.FirebaseAuthManager;
import kr.ac.dankook.autoinfo.firebase.FirebaseOrderManager;
import kr.ac.dankook.autoinfo.models.Order;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        TextView tv = findViewById(R.id.tv_orders);

        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
        if (user == null) {
            tv.setText("로그인이 필요합니다.");
            return;
        }

        FirebaseOrderManager.getInstance().getOrdersByBuyer(user.getUid(),
                new FirebaseOrderManager.OnOrdersResultListener() {
                    @Override
                    public void onSuccess(List<Order> orders) {
                        if (orders == null || orders.isEmpty()) {
                            tv.setText("주문 내역이 없습니다.");
                            return;
                        }

                        StringBuilder sb = new StringBuilder();
                        for (Order o : orders) {
                            sb.append("상품ID: ").append(o.getPostId())
                                    .append(" / 금액: ").append(o.getAmount())
                                    .append("\n");
                        }
                        tv.setText(sb.toString());
                    }

                    @Override
                    public void onError(@NonNull Exception e) {
                        tv.setText("주문 조회 실패");
                        Toast.makeText(OrdersActivity.this,
                                "주문 조회 실패: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
