package cloudist.cc.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cloudist on 2017/10/18.
 */

public class GuidePoint extends View {

    private Paint mPaint;

    private int mRadius;
    private int mColor;
    private int mNumber;
    private int mPadding;
    private int mStrokeWidth;

    private float startX;

    private int centerPositionY;
    private int mCurrentIndex;

    public GuidePoint(Context context) {
        this(context, null);
    }

    public GuidePoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidePoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //从xml文件中获取数据
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.GuidePoint);

        mRadius = mTypedArray.getInt(R.styleable.GuidePoint_radius, 20);
        mColor = mTypedArray.getColor(R.styleable.GuidePoint_circlecolor, 0xff00dddd);
        mNumber = mTypedArray.getInt(R.styleable.GuidePoint_number, 3);
        mPadding = mTypedArray.getInt(R.styleable.GuidePoint_padding, 25);
        mStrokeWidth = mTypedArray.getInt(R.styleable.GuidePoint_strokeWidth, 5);
        startX = mRadius + mStrokeWidth;
        mCurrentIndex = 0;

        mTypedArray.recycle();

        initialize();
    }

    //初始化
    private void initialize() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth;
        int mHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = getPaddingLeft() + (mNumber - 1) * mPadding + 2 * mNumber * (mRadius + mStrokeWidth) + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = getPaddingTop() + 2 * (mRadius + mPadding) + getPaddingBottom();
        }

        centerPositionY = mHeight / 2;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        drawCircle(canvas);

        drawCurrentCircle(canvas, 0);

        super.onDraw(canvas);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth((float) 5);
        //画空心圆  mStrokeWidth是外边缘宽度
        for (int i = 0; i <= mNumber; i++) {
            canvas.drawCircle((mPadding + 2 * (mRadius + mStrokeWidth)) * i + mRadius + mStrokeWidth, centerPositionY, mRadius, mPaint);
        }
    }

    private void drawCurrentCircle(Canvas canvas, int index) {
        //画起始圆
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(startX, centerPositionY, mRadius, mPaint);
    }

    public void scrollIndex(int index) {
        if (index > mNumber - 1 || index < 0) {
            return;
        }
        mCurrentIndex = index;
        startX = (mPadding + 2 * (mRadius + mStrokeWidth)) * index + mRadius + mStrokeWidth;
        postInvalidate();
    }

    public void scrollOffset(float currentPositionOffset) {
        startX = (mPadding + 2 * (mRadius + mStrokeWidth)) * mCurrentIndex + mRadius + mStrokeWidth + currentPositionOffset * mPadding;
        postInvalidate();
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCurrentIndex(int mCurrentIndex) {
        this.mCurrentIndex = mCurrentIndex;
    }
}
