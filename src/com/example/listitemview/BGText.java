package com.example.listitemview;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
		tp.setColor(Color.BLUE);
		tp.measureText(text);
		tp.getTextBounds(text, 0, text.length()-1, bRect);
		Log.i("tag", "the left is:"+bRect.left+" the top is:"+bRect.top+" the right is:"+bRect.right+" the bottom is:"+bRect.bottom);
		canvas.drawRect(bRect, tp);
		canvas.drawRect(50, 50, 50+bRect.right, 50+bRect.bottom, tp);
		
		canvas.drawText(text, 100, 100, tp);
	}
	// public void measureTextSize() {
	// Paint pFont = new Paint();
	// Rect rect = new Rect();
	// pFont.getTextBounds("豆", 0, 1, rect);
	// System.out.println("height:" + rect.height() + "width:" + rect.width());
	// }
}