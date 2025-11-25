package kr.ac.dankook.autoinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import kr.ac.dankook.autoinfo.ui.MarketFragment;
import kr.ac.dankook.autoinfo.ui.MyInfoFragment;
import kr.ac.dankook.autoinfo.ui.ChatFragment;
import kr.ac.dankook.autoinfo.ui.MyPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 앱 실행 시 첫 화면: 마켓 화면
        replaceFragment(new MarketFragment());

        // 네비게이션 탭 선택 시 화면 변경
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selected = null;

            int id = item.getItemId();

            if (id == R.id.nav_market) {
                selected = new MarketFragment();
            }
            else if (id == R.id.nav_myinfo) {
                selected = new MyInfoFragment();
            }
            else if (id == R.id.nav_chat) {
                selected = new ChatFragment();
            }
            else if (id == R.id.nav_mypage) {
                selected = new MyPageFragment();
            }

            replaceFragment(selected);
            return true;
        });
    }

    // Fragment 교체 함수
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}
