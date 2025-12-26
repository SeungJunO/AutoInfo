package kr.ac.dankook.autoinfo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.network.ShopifyCheckoutClient;

public class OrdersFragment extends Fragment {

    public OrdersFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        EditText etVariant = root.findViewById(R.id.et_variant_id);
        EditText etQty = root.findViewById(R.id.et_quantity);
        EditText etName = root.findViewById(R.id.et_customer_name);
        EditText etEmail = root.findViewById(R.id.et_customer_email);
        Button btnPay = root.findViewById(R.id.btn_pay_now);
        TextView tvStatus = root.findViewById(R.id.tv_status);

        btnPay.setOnClickListener(v -> {
            String variantId = etVariant.getText().toString().trim();
            String qtyStr = etQty.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (variantId.isEmpty() || qtyStr.isEmpty()) {
                tvStatus.setText("variantId, 수량은 필수입니다.");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyStr);
            } catch (Exception e) {
                tvStatus.setText("수량이 숫자가 아닙니다.");
                return;
            }

            if (qty <= 0) {
                tvStatus.setText("수량은 1 이상이어야 합니다.");
                return;
            }

            tvStatus.setText("Shopify checkoutUrl 생성 중...");

            ShopifyCheckoutClient.createCheckout(
                    variantId,
                    qty,
                    email.isEmpty() ? null : email,
                    name.isEmpty() ? null : name,
                    new ShopifyCheckoutClient.ResultCallback() {
                        @Override
                        public void onSuccess(@NonNull String checkoutUrl) {
                            requireActivity().runOnUiThread(() -> {
                                tvStatus.setText("결제 페이지로 이동합니다.");
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(checkoutUrl));
                                startActivity(intent);
                            });
                        }

                        @Override
                        public void onError(@NonNull String message) {
                            requireActivity().runOnUiThread(() -> tvStatus.setText(message));
                        }
                    }
            );
        });

        return root;
    }
}
