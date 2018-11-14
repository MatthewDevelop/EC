package cn.fxn.svm.ui.wdget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import cn.fxn.svm.core.delegates.EcDelegate;
import cn.fxn.svm.core.util.log.EcLogger;
import cn.fxn.svm.ui.R;

/**
 * @author:Matthew
 * @date:2018/11/5
 * @email:guocheng0816@163.com
 * @func:自定义相册选择器
 */
public class AutoPhotoLayout extends LinearLayoutCompat {
    private static final String TAG = "AutoPhotoLayout";

    private static final String ICON_TEXT = "{fa-plus}";
    /**
     * 所有的行
     */
    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    /**
     * 记录每一行的高度
     */
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();
    /**
     * 图片加载的OPTION
     */
    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);
    /**
     * 当前有几个Item
     */
    private int mCurrentNum = 0;
    /**
     * 最大图片数目
     */
    private int mMaxNum = 0;
    /**
     * 每行的图片数
     */
    private int mImageCountPerLine = 0;
    /**
     * 加号
     */
    private IconTextView mIconAdd = null;
    /**
     * 每张图片的参数
     */
    private LayoutParams mParams = null;
    /**
     * 要删除的图片id
     */
    private int mDeleteId = 0;
    /**
     * 加载图片的imageView
     */
    private AppCompatImageView mTargetImage = null;
    /**
     * 图片边距
     */
    private int mImageMargin = 0;
    private EcDelegate mDelegate = null;
    /**
     * 防止多次测量和layout
     */
    private boolean mIsOnceInitOnMeasure = false;
    private boolean mHasInitOnLayout = false;
    /**
     * 一行的图片
     */
    private List<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private float mIconSize = 0;


    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("CustomViewStyleable") final TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 1);
        mImageCountPerLine = typedArray.getInt(R.styleable.camera_flow_layout_image_per_line, 3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        EcLogger.e(TAG, mMaxNum + "-" + mImageCountPerLine + "-" + mImageMargin + "-" + mIconSize);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }

    /**
     * 初始化添加按钮
     */
    private void initAddIcon() {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setBackgroundResource(R.drawable.border_text);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.startCaremaWithCheck();
            }
        });
        this.addView(mIconAdd);
    }

    public void setDelegate(EcDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        int width = 0;
        int height = 0;
        //每一行的宽高
        int lineWidth = 0;
        int lineHeight = 0;
        //元素内部元素个数
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            //测量子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            final MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽度
            final int childWidth = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            //子view占据的高度
            final int childHeight = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //未换行
                //叠加行宽
                lineWidth += childWidth;
                //当前最大高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一个子控件
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                //判断是否超过最大拍照限制
                height += lineHeight;
            }

        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingRight() + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
        //设置一行所有图片的宽度
        final int imageSideLen = (sizeWidth - getPaddingLeft() - getPaddingRight() - 2 * mImageCountPerLine * mImageMargin)
                / mImageCountPerLine;
        /**
         * 只初始化一次
         */
        if (!mIsOnceInitOnMeasure) {
            mParams = new LayoutParams(imageSideLen, imageSideLen);
            mParams.leftMargin = mImageMargin;
            mParams.rightMargin = mImageMargin;
            mParams.topMargin = mImageMargin;
            mParams.bottomMargin = mImageMargin;
            mIsOnceInitOnMeasure = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();
        //获取房前ViewGroup的宽度
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        if (!mHasInitOnLayout) {
            mLineViews = new ArrayList<>();
            mHasInitOnLayout = true;
        }
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            //需要换行
            if (childWidth + lineWidth + mlp.leftMargin + mlp.rightMargin
                    > width - getPaddingLeft() - getPaddingRight()) {
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight);
                //记录当前一行的views
                ALL_VIEWS.add(mLineViews);
                //重置宽高
                lineWidth = 0;
                lineHeight = childHeight + mlp.topMargin + mlp.bottomMargin;
                //重置集合
                mLineViews=new ArrayList<>();
                //此处不能使用clear方法，clear会导致最后一张图片无法绘制
//                mLineViews.clear();

            }
            lineWidth += childWidth + mlp.leftMargin + mlp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + mlp.topMargin + mlp.bottomMargin);
            mLineViews.add(child);
        }
        //处理最后一行
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);
//        EcLogger.d("ALL_VIEW size-"+ALL_VIEWS.size()+"");
//        for (List<View> list : ALL_VIEWS) {
//            EcLogger.d("size-"+list.size()+"");
//        }
        //设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        final int lineNum = ALL_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行所有的view
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for (int j = 0; j < size; j++) {
                final View child = mLineViews.get(j);
                //到达上限后最后的加号不绘制
                if (child.getVisibility()==GONE){
                    continue;
                }
                final MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
                //设置子View的边距
                final int lc = left + mlp.leftMargin;
                final int tc = top + mlp.topMargin;
                final int rc = lc + child.getMeasuredWidth();
                final int bc = tc + child.getMeasuredHeight();
                //为View进行布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mParams);
        mHasInitOnLayout = false;
    }

    public final void onCropTarget(Uri uri) {
        createNewImageView();
        Glide.with(mDelegate)
                .load(uri)
                .apply(OPTIONS)
                .into(mTargetImage);
    }

    private void createNewImageView() {
        mTargetImage = new AppCompatImageView(getContext());
        mTargetImage.setId(mCurrentNum);
        mTargetImage.setLayoutParams(mParams);
        mTargetImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取要删除的图片的id
                mDeleteId = v.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_panel_up_from_buttom);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = 0.5f;
                    window.setAttributes(params);
                    window.findViewById(R.id.dialog_image_clicked_btn_delete)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //得到要删除的图片
                                    final AppCompatImageView deleteImageView = findViewById(mDeleteId);
                                    //设置删除动画
                                    final AlphaAnimation animation = new AlphaAnimation(1, 0);
                                    animation.setDuration(500);
                                    animation.setRepeatCount(0);
                                    animation.setFillAfter(true);
                                    animation.setStartOffset(0);
                                    deleteImageView.setAnimation(animation);
                                    animation.start();
                                    AutoPhotoLayout.this.removeView(deleteImageView);
                                    mCurrentNum -= 1;
                                    //当数目达到上线隐藏，不足就要显示
                                    if (mCurrentNum < mMaxNum) {
                                        mIconAdd.setVisibility(VISIBLE);
                                    }
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_cancel).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTargetDialog.cancel();
                        }
                    });
                    window.findViewById(R.id.dialog_image_clicked_btn_undetermined).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTargetDialog.cancel();
                        }
                    });
                }
            }
        });
        //添加子View的时候传入位置
        this.addView(mTargetImage, mCurrentNum);
        mCurrentNum++;
        //当添加数目大于MaxNum时，隐藏添加键
        if (mCurrentNum >= mMaxNum) {
            EcLogger.e(TAG, "隐藏");
            mIconAdd.setVisibility(GONE);
        }
    }
}
