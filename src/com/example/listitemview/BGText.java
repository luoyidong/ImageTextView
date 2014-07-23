package com.example.listitemview;

import android.R.color;
import android.annotation.SuppressLint;
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
	private int BgColor = Color.BLUE;
	private int mTextColor = Color.WHITE;
	private int textSize;

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
		text = "";
		textSize = 12;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTextSize(int size) {
		tp.setTextSize(textSize);
	}

	public void setTextColor(int color) {
		mTextColor = color;
	}

	public void setBackgroundColor(int color) {
		BgColor = color;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		tp.setColor(Color.GRAY);

		if (text != null && !text.trim().equals("")) {
			canvas.drawRoundRect(new RectF(0, 0, 0 + tp.measureText(text) + 4,
					0 + tp.getTextSize() + 5), 2, 2, tp);
			tp.setColor(Color.RED);
			tp.setTextAlign(Paint.Align.LEFT);
			canvas.drawText(text, 2, tp.getTextSize(), tp);
		}
	}
}