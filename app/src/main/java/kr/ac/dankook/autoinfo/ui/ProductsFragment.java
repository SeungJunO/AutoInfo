package kr.ac.dankook.autoinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.dankook.autoinfo.NewInfoActivity;
import kr.ac.dankook.autoinfo.PostDetailActivity;
import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.adapters.PostAdapter;
import kr.ac.dankook.autoinfo.firebase.FirebasePostManager;
import kr.ac.dankook.autoinfo.models.Post;

public class ProductsFragment extends Fragment {

    private RecyclerView rv;
    private PostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_products, container, false);

        rv = root.findViewById(R.id.rv_posts);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(new ArrayList<>());
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(post -> {
            Intent intent = new Intent(getContext(), PostDetailActivity.class);
            intent.putExtra("postId", post.getId());
            intent.putExtra("title", post.getTitle());
            intent.putExtra("price", post.getPrice());
            intent.putExtra("variantId", post.getLink());
            startActivity(intent);
        });

        FirebasePostManager.getInstance().getAllPosts(new FirebasePostManager.OnPostsResultListener() {
            @Override
            public void onSuccess(List<Post> posts) {
                adapter.setItems(posts);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(),
                        "상품 불러오기 실패: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 4) 상품 추가 버튼
        Button btnAdd = root.findViewById(R.id.btn_add_product);
        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), NewInfoActivity.class))
        );

        return root;
    }
}
