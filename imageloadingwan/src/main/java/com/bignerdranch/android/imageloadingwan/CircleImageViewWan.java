package com.bignerdranch.android.imageloadingwan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by LENOVO on 2017/4/23.
 */

public class CircleImageViewWan extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "CircleImageViewWan";
    private int mBorderColor;
    private int mBorderWidth;
    private boolean mChangeSize;

    private Paint mDrawablePaint=new Paint();
    private Paint mBorderPaint=new Paint();

    private RectF mDrawableRect=new RectF();
    private RectF mBorderRect=new RectF();

    private float mBorderRadius;
    private float mDrawableRadius;

    private int mBitmapWidth;
    private int mBitmapHeight;

    private Matrix mMatrix=new Matrix();
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;

    public CircleImageViewWan(Context context) {
        super(context);
    }

    public CircleImageViewWan(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs,R.styleable.CircleImageViewWan,0,0);
        try {
            mBorderColor=typedArray.getColor(R.styleable.CircleImageViewWan_BorderColor, Color.BLACK);
            mBorderWidth=typedArray.getDimensionPixelSize(R.styleable.CircleImageViewWan_BorderWith,0);
            mChangeSize=typedArray.getBoolean(R.styleable.CircleImageViewWan_changeSize,true);
        }finally {
            typedArray.recycle();
        }
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap==null)
            return;

        canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,mDrawablePaint);
        if (mBorderWidth!=0){
            canvas.drawCircle(getWidth()/2,getHeight()/2,mBorderRadius,mBorderPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap=((BitmapDrawable)drawable).getBitmap();
    }

    public void init(){
        super.setScaleType(ScaleType.CENTER_CROP);

        mBitmapShader=new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mDrawablePaint.setAntiAlias(true);
        mDrawablePaint.setShader(mBitmapShader);

        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mBorderRect.set(0,0,getWidth(),getHeight());
        mDrawableRect.set(mBorderRect);
        Log.d(TAG, "init: "+mDrawableRect.height());

        mBitmapHeight=mBitmap.getHeight();
        mBitmapWidth=mBitmap.getWidth();
        Log.d(TAG, "init: "+mBitmapHeight+mBitmapWidth);
        mDrawableRadius=Math.min(mDrawableRect.height()/2,mDrawableRect.width()/2);
        mBorderRadius=Math.min((mBorderRect.height()-mBorderWidth)/2,(mBorderRect.width()-mBorderWidth)/2);

        getMatrixChange();

        invalidate();
    }

    public void getMatrixChange() {
        float scale;
        float dx = 0;
        float dy = 0;
        if (mDrawableRect.height() != 0) {
            if (mChangeSize) {
                if (mBitmapHeight / mDrawableRect.height() < mBitmapWidth / mDrawableRect.width()) {
                    scale = (mDrawableRect.height() / (float) mBitmapHeight);
                    dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = mDrawableRect.width() / (float) mBitmapWidth;
                    dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
                }
                Log.d(TAG, "getMatrixChange: " + mDrawableRect.height() + "、" + mBitmapHeight + "、" + scale);
                mMatrix.setScale(scale, scale);
                mMatrix.postTranslate(dx, dy);


                mBitmapShader.setLocalMatrix(mMatrix);
            }
        }
    }
}