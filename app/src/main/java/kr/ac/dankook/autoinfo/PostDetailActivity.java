package kr.ac.dankook.autoinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import kr.ac.dankook.autoinfo.firebase.FirebaseAuthManager;
import kr.ac.dankook.autoinfo.firebase.FirebaseOrderManager;
import kr.ac.dankook.autoinfo.models.Order;
import kr.ac.dankook.autoinfo.network.ShopifyCheckoutClient;

public class PostDetailActivity extends AppCompatActivity {

    private String postId;
    private String title;
    private long price;
    private String variantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postId = getIntent().getStringExtra("postId");
        title = getIntent().getStringExtra("title");
        price = getIntent().getLongExtra("price", 0L);
        variantId = getIntent().getStringExtra("variantId");

        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvPrice = findViewById(R.id.tv_detail_price);

        tvTitle.setText(title == null ? "" : title);
        tvPrice.setText(String.valueOf(price));

        Button btnBuy = findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(v -> startCheckout());
    }

    private void startCheckout() {
        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "로그인 후 구매 가능합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (variantId == null || variantId.trim().isEmpty()) {
            Toast.makeText(this, "Variant ID가 없습니다. (상품 등록 시 입력 필요)", Toast.LENGTH_LONG).show();
            return;
        }

        String email = user.getEmail() == null ? "" : user.getEmail();
        String name = user.getDisplayName() == null ? "" : user.getDisplayName();

        ShopifyCheckoutClient.createCheckout(
                variantId,
                1,
                email,
                name,
                new ShopifyCheckoutClient.ResultCallback() {
                    @Override
                    public void onSuccess(@NonNull String checkoutUrl) {
                        runOnUiThread(() -> {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(checkoutUrl)));

                            Order order = new Order();
                            order.setPostId(postId);
                            order.setBuyerId(user.getUid());
                            order.setAmount(price);

                            FirebaseOrderManager.getInstance().createOrder(order);

                            Toast.makeText(PostDetailActivity.this, "결제 링크 생성 + 주문 저장 완료", Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(@NonNull String message) {
                        runOnUiThread(() ->
                                Toast.makeText(PostDetailActivity.this, "결제 오류: " + message, Toast.LENGTH_SHORT).show()
                        );
                    }
                }
        );
    }
}
