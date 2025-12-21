package kr.ac.dankook.autoinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import kr.ac.dankook.autoinfo.LoginActivity;
import kr.ac.dankook.autoinfo.OrdersActivity;
import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.firebase.FirebaseAuthManager;
import kr.ac.dankook.autoinfo.firebase.FirebaseOrderManager;
import kr.ac.dankook.autoinfo.firebase.FirebasePostManager;
import kr.ac.dankook.autoinfo.models.Order;
import kr.ac.dankook.autoinfo.models.Post;

public class MyPageFragment extends Fragment {

    private TextView tvNickname, tvEmail;
    private TextView tvMyInfoCount, tvPurchaseCount;

    private Button btnGoMyInfo;
    private Button btnOrders;
    private Button btnAlert;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
        if (user == null) {
            tvNickname.setText("게스트");
            tvEmail.setText("로그인이 필요합니다.");
            tvMyInfoCount.setText("0");
            tvPurchaseCount.setText("0");

            disableButtonsForGuest();
            return;
        }

        showProfile(user);

        loadMyInfoCount(user.getUid());
        loadMyPurchaseCount(user.getUid());

        setupButtons(user);
    }

    private void bindViews(@NonNull View view) {
        tvNickname = view.findViewById(R.id.tv_profile_nickname);
        tvEmail = view.findViewById(R.id.tv_profile_email);

        tvMyInfoCount = view.findViewById(R.id.tv_stat_myinfo_count);
        tvPurchaseCount = view.findViewById(R.id.tv_stat_purchase_count);

        btnGoMyInfo = view.findViewById(R.id.btn_go_myinfo);

        // ⚠️ 너 XML에서 주문내역 버튼 id가 tv_my_orders로 되어있음 (버튼인데 tv_ prefix)
        btnOrders = view.findViewById(R.id.btn_my_orders);

        btnAlert = view.findViewById(R.id.btn_alert_settings);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    private void disableButtonsForGuest() {
        btnGoMyInfo.setEnabled(false);
        btnOrders.setEnabled(false);
        btnAlert.setEnabled(false);

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });
    }

    private void showProfile(@NonNull FirebaseUser user) {
        String nickname;

        if (user.getDisplayName() != null && !user.getDisplayName().trim().isEmpty()) {
            nickname = user.getDisplayName().trim();
        } else if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            String email = user.getEmail().trim();
            int at = email.indexOf("@");
            nickname = (at > 0) ? email.substring(0, at) : email;
        } else {
            String uid = user.getUid();
            nickname = "user-" + (uid.length() >= 6 ? uid.substring(0, 6) : uid);
        }

        tvNickname.setText(nickname);
        tvEmail.setText(user.getEmail() == null ? "" : user.getEmail());
    }

    private void setupButtons(@NonNull FirebaseUser user) {

        btnGoMyInfo.setOnClickListener(v -> {
            Toast.makeText(getContext(), "내 상품 관리 기능은 연결 예정(또는 Products 탭에서 확인)", Toast.LENGTH_SHORT).show();
        });

        btnOrders.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), OrdersActivity.class));
        });

        btnAlert.setOnClickListener(v -> {
            Toast.makeText(getContext(), "알림/키워드 설정은 추후 구현 예정입니다.", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuthManager.getInstance().signOut();
            Toast.makeText(getContext(), "로그아웃 완료", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private void loadMyInfoCount(@NonNull String myUid) {
        FirebasePostManager.getInstance().getAllPosts(new FirebasePostManager.OnPostsResultListener() {
            @Override
            public void onSuccess(List<Post> posts) {
                int count = 0;
                if (posts != null) {
                    for (Post p : posts) {
                        if (p != null && myUid.equals(p.getAuthorId())) count++;
                    }
                }
                tvMyInfoCount.setText(String.valueOf(count));
            }

            @Override
            public void onError(Exception e) {
                tvMyInfoCount.setText("0");
            }
        });
    }

    private void loadMyPurchaseCount(@NonNull String myUid) {
        FirebaseOrderManager.getInstance().getOrdersByBuyer(myUid, new FirebaseOrderManager.OnOrdersResultListener() {
            @Override
            public void onSuccess(List<Order> orders) {
                int count = (orders == null) ? 0 : orders.size();
                tvPurchaseCount.setText(String.valueOf(count));
            }

            @Override
            public void onError(Exception e) {
                tvPurchaseCount.setText("0");
            }
        });
    }
}
