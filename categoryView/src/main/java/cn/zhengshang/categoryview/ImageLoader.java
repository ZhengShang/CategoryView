package cn.zhengshang.categoryview;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by shangzheng on 2019-06-25.
 * 🐳🐳🐳🍒           15:21 🥥
 */
public class ImageLoader {

    /**
     * 加载圆形图片
     *
     * @param imageView 显示图片的目标View
     * @param path      图片的网络url
     */
    public static void loadCircleImage(ImageView imageView, String path) {
        Glide.with(imageView.getContext())
                .load(path)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imageView 显示图片的目标View
     * @param path      图片的网络url
     */
    public static void loadImage(ImageView imageView, String path) {
        Glide.with(imageView.getContext())
                .load(path)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(imageView);
    }
}
