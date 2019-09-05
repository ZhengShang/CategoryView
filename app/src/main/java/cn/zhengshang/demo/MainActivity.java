package cn.zhengshang.demo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.zhengshang.categoryview.CategoryView;

/**
 * Created by shangzheng on 2019-08-30.
 *            🐳🐳🐳🍒           18:34 🥥
 */
public class MainActivity extends AppCompatActivity {

    private CategoryView<TestModel> mCategoryView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategoryView = findViewById(R.id.category);
        mCategoryView.setListener((item, position) -> Toast.makeText(MainActivity.this, "click item " + position, Toast.LENGTH_SHORT).show());
        mCategoryView.setList(generateRandomList());
    }

    private List<TestModel> generateRandomList() {
        int max = (int) (Math.random() * 30);
        List<TestModel> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            list.add(new TestModel());
        }
        return list;
    }
}
