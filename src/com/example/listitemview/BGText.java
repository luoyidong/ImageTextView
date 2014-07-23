package com.example.listitemview;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BGText extends View {
	private TextPaint tp;
	private String text;
	private Rect bRect = new Rect();

	public BGText(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	public BGText(Context context, AttributeSet attr, int DefStyle) {
		super(context, attr, DefStyle);
		init();
	}

	public void init() {
		tp = new TextPaint();
		text = "随手记";
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		tp.setColor(Color.GRAY);
		tp.setTextSize(48);
		tp.measureText(text);
		tp.getTextBounds(text, 0, text.length(), bRect);
		Log.i("tag", "the left is:" + bRect.left + " the top is:" + bRect.top
				+ " the right is:" + bRect.right + " the bottom is:"
				+ bRect.bottom);

//		canvas.drawRect(50, 50, 50 + tp.measureText(text),
//				50 + (bRect.bottom - bRect.top) + 8, tp);
		canvas.drawRoundRect(new RectF(50, 50, 50 + tp.measureText(text)+4,
				50 + (bRect.bottom - bRect.top)+5), 0, 2, tp);

		tp.setColor(Color.WHITE);
		canvas.drawText(text, 50+2, 63, tp);
	}
	// public void measureTextSize() {
	// Paint pFont = new Paint();
	// Rect rect = new Rect();
	// pFont.getTextBounds("豆", 0, 1, rect);
	// System.out.println("height:" + rect.height() + "width:" + rect.width());
	// }
}