package com.mgr.parallaxlistitem.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import com.mgr.parallaxlistitem.interfaces.OnScrollListener;

public class ParallaxListItemView extends FrameLayout implements OnScrollListener {
	private final Rect fullBitmapRect = new Rect();
	private final Rect bitmapAreaRect = new Rect();
	private final RectF viewRect = new RectF();
	private final Paint paint = new Paint();
	private int listHeight;
	private float top;
	private float bottom;
	private Bitmap bitmap;
	private float bitmapDifference;
	public boolean isFirstStart = true;
	public static int viewHeight;

	public ParallaxListItemView(Context context) {
		super(context);
	}

	public ParallaxListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ParallaxListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	public ParallaxListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		viewRect.set(0, 0, w, h);
		paint.setShader(new LinearGradient(0, 0, 0, h / 2, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
		bitmapAreaRect.set(0, 0, w, h);
		viewHeight = h;
		invalidate();
	}


	@Override
	public void onScroll(int dY) {
		if (bitmap == null) {
			return;
		}
		if (top > listHeight) {
			top = listHeight;
		} else if (bottom < 0) {
			bottom = 0;
		} else {
			if(listHeight != 0) {
				top -= dY;
				bottom -= dY;
				calculatePositions();
			}
		}
		invalidate();
	}

	public void setBitmap(Bitmap b, int top) {
		bitmap = b;
		if (b != null) {
			this.top = top;
			bottom = top + viewHeight;
			fullBitmapRect.set(0, 0, b.getWidth(), b.getHeight());
			bitmapDifference = b.getHeight() - viewHeight;

			calculatePositions();
			invalidate();
		}
	}

	public void setListHeight(int height) {
		listHeight = height;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, bitmapAreaRect, viewRect, paint);
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		}
	}

	public void setInitialTopPosition(int top) {
		this.top = top;
		bottom = top + viewHeight;
		if(bitmap == null){
			return;
		}
		bitmapDifference = bitmap.getHeight() - viewHeight;

		calculatePositions();

		invalidate();
	}

	private void calculatePositions(){
		float percent = top / (float) listHeight;
		float scrollAmount = bitmapDifference * percent;

		int bTop = (int) scrollAmount;
		int bBottom = bTop + bitmapAreaRect.height();
		if (bBottom < fullBitmapRect.bottom & bTop > -bitmapDifference) {
			bitmapAreaRect.set(0, bTop, getWidth(), bBottom);
		}
	}
}
