package cn.zhengshang.demo;

import java.util.Random;

import cn.zhengshang.categoryview.CategoryDemand;

/**
 * Created by shangzheng on 2019-09-02.
 *            🐳🐳🐳🍒           11:48 🥥
 */
public class TestModel implements CategoryDemand {
    private String title;
    private String image;

    public TestModel() {
        int i = new Random().nextInt(1000);
        this.title = String.valueOf(i);
        this.image = String.format("https://picsum.photos/id/%d/200/200", i);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String categoryIconUrl() {
        return this.image;
    }

    @Override
    public String categoryName() {
        return this.title;
    }
}
