package cn.zhengshang.categoryview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * Created by shangzheng on 2019-06-25.
 * 🐳🐳🐳🍒           11:04 🥥
 */
public class CategoryView<T extends CategoryDemand> extends FrameLayout {

    private final static int ROW_COUNT = 2;
    private final static int COL_COUNT = 4;
    private final static int INDICATOR_MARGIN = 10;
    private final static int INDICATOR_RADIUS = 10;
    private final static int INDICATOR_SPACE = INDICATOR_RADIUS * 4;
    private final static int ICON_SIZE = 80;

    private ViewPager mViewPager;

    private Paint mPaint = new Paint();

    private int mPageSize;
    private int mHeight;
    private int mWidth;
    private int mSelectedPage;

    private int mRowCount;
    private int mColCount;
    private float mIconSize;
    private float mTextSize;
    private float mIndicatorSize;
    private float mVerticalMargin;
    private int mIndicatorUnSelectColor;
    private int mIndicatorSelectColor;

    private List<T> mList;

    private OnItemClickListener<T> mListener;

    public CategoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CategoryView, 0, 0);
        mRowCount = a.getInt(R.styleable.CategoryView_cgv_row_count, ROW_COUNT);
        mColCount = a.getInt(R.styleable.CategoryView_cgv_col_count, COL_COUNT);
        mIconSize = a.getDimensionPixelSize(R.styleable.CategoryView_cgv_icon_size, ICON_SIZE);
        mTextSize = a.getDimension(R.styleable.CategoryView_cgv_text_size, 12);
        mIndicatorSize = a.getDimensionPixelSize(R.styleable.CategoryView_cgv_indicator_size, 2 * INDICATOR_RADIUS);
        mVerticalMargin = a.getDimensionPixelSize(R.styleable.CategoryView_cgv_vertical_margin, 0);
        mIndicatorUnSelectColor = a.getColor(R.styleable.CategoryView_cgv_indicator_unSelect_color, Color.DKGRAY);
        mIndicatorSelectColor = a.getColor(R.styleable.CategoryView_cgv_indicator_select_color, Color.BLUE);
        a.recycle();
    }

    private void init() {
        mViewPager = new ViewPager(getContext());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSelectedPage = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        addView(mViewPager);
    }

    public void setList(List<T> list) {
        mList = list;

        mPageSize = (int) Math.ceil(list.size() * 1.0f / (ROW_COUNT * COL_COUNT));

        reloadPager();
    }

    public void setListener(OnItemClickListener<T> listener) {
        mListener = listener;
    }

    private void reloadPager() {
        Adapter<T> adapter = new Adapter<>(mPageSize, mList, mListener);
        mViewPager.setAdapter(adapter);
        int offLimit = (int) (mPageSize / 2.5f);
        if (offLimit > 1) {
            mViewPager.setOffscreenPageLimit(offLimit);
        }
    }

    private void drawIndicator(Canvas canvas) {
        float radius = mIndicatorSize / 2;
        int hBaseline = mHeight - getPaddingBottom() - INDICATOR_SPACE / 2;
        for (int i = 1; i <= mPageSize; i++) {
            if (i == mSelectedPage + 1) {
                //Draw main color dot
                mPaint.setColor(mIndicatorSelectColor);
            } else {
                //Draw normal dot
                mPaint.setColor(mIndicatorUnSelectColor);
            }
            float centerX = mWidth / 2f + (i - mPageSize / 2f - 0.5f) * (INDICATOR_MARGIN + radius * 2);
            canvas.drawOval(centerX - radius, hBaseline - radius, centerX + radius, hBaseline + radius, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec + INDICATOR_SPACE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawIndicator(canvas);
        super.dispatchDraw(canvas);
    }

    private static class Adapter<T extends CategoryDemand> extends PagerAdapter {

        private int count;
        private List<T> list;
        private OnItemClickListener<T> listener;

        Adapter(int count, List<T> list, OnItemClickListener<T> listener) {
            this.count = count;
            this.list = list;
            this.listener = listener;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int start = ROW_COUNT * COL_COUNT * position;
            Context context = container.getContext();
            TableLayout tableLayout = new TableLayout(context);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setGravity(Gravity.CENTER);
            for (int i = 0; i < ROW_COUNT; i++) {
                TableRow tableRow = new TableRow(context);
                for (int i1 = 0; i1 < COL_COUNT; i1++) {
                    int index = start + i1 + (i * COL_COUNT);
                    if ((index) < list.size()) {
                        View child = View.inflate(context, R.layout.view_category_child, null);
                        T item = list.get(index);

                        if (listener != null) {
                            child.setOnClickListener(v -> listener.onClick(item, index));
                        }
                        ImageView iconView = child.findViewById(R.id.icon);
                        ImageLoader.loadCircleImage(iconView, item.categoryIconUrl());
                        TextView nameView = child.findViewById(R.id.name);
                        nameView.setText(item.categoryName());

                        tableRow.addView(child);
                    }
                }
                tableLayout.addView(tableRow);
            }
            container.addView(tableLayout);
            return tableLayout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnItemClickListener<T> {
        void onClick(T item, int position);
    }
}
