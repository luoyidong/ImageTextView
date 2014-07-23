package com.example.listitemview;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.listitemview.R.id;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class ImageTextView extends TextView {

	private final static String LOGTEXT = "ListItemView";

	// 插入图片时插入位置的索引值
	public final static int INSERT_FIRST = 0;
	public final static int INSERT_LAST = -1;

	private List<ImageItem> tags;
	private String descript = "";
	private String mtitle = "";

	// 根据字体大小调整图片尺寸
	private int textSize;

	private OnImageTagListener imageTagListener;

	public ImageTextView(Context context) {
		super(context);
		init();
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		this.mtitle = (String) (getText() != null ? getText() : "");
	}

	public void addImageTagById(int res_id, int index) {
		if (this.tags == null) {
			this.tags = new ArrayList<ImageItem>();
		}
		switch (index) {
		case INSERT_FIRST:
			tags.add(0, generateImageItem(res_id));
			break;
		case INSERT_LAST:
			tags.add(generateImageItem(res_id));
		default:
			if (index == 0) {
				tags.add(0, generateImageItem(res_id));
			} else if (index > 0 && index < tags.size()) {
				tags.add(index, generateImageItem(res_id));
			}
			break;
		}
		setText("");
	}

	public void addImageTagByName(String name, int index) {
		addImageTagById(getResourceId(name), index);
	}

	public void removeImageTagById(int res_id) {
		if (tags != null && tags.size() > 0) {
			Iterator<ImageItem> it = tags.iterator();
			while (it.hasNext()) {
				ImageItem item = it.next();
				if (item.getmId() == res_id) {
					tags.remove(item);
					break;
				}
			}
		}
		setText("");
	}

	public void removeImageTagByIndex(int index) {
		if (tags != null && tags.size() > 0) {
			int res_id = ((ImageItem) tags.get(index)).getmId();
			removeImageTagById(res_id);
		}
	}

	// 判断当前是否存在某一个图片
	private boolean isDoubleImage(int res_id) {
		Iterator<ImageItem> iterator = tags.iterator();
		while (iterator.hasNext()) {
			ImageItem ii = iterator.next();
			if (ii.getmId() == res_id) {
				return true;
			}
		}
		return false;
	}

	private boolean isDoubleImage(String name) {
		return isDoubleImage(getResourceId(name));
	}

	// 根据字体大小生成图片大小相似的ImageItem
	private ImageItem generateImageItem(int res_id) {
		
		textSize = (int) getTextSize();
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res_id);
		int mW = bitmap.getWidth();
		int mH = bitmap.getHeight();
		
		return new ImageItem(getContext(), res_id, mW, mH);
	}

	public void setText(String con) {
		if (tags != null && tags.size() > 0) {
			Iterator<ImageItem> items = tags.iterator();
			descript = "";
			while (items.hasNext()) {
				ImageItem ii = items.next();
				descript += ii.generateHtml() != null ? ii.generateHtml() : "";
			}
		}
		if (con != null && !con.equals("")) {
			descript += con;
			mtitle = con;
		} else {
			descript += mtitle;
		}
		
		setText(getCharSequence(descript));
	}

	private CharSequence getCharSequence(String str) {
		return Html.fromHtml(str, new ImageGetter() {

			@Override
			public Drawable getDrawable(String source) {
				int curId = getResourceId(source);
				ImageItem item = null;
				try {
					item = findById(curId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Drawable drawable = getResources().getDrawable(curId);
				// Log.i("drawableSize:the width is:",
				// drawable.getIntrinsicWidth() + " the height is:"
				// + drawable.getIntrinsicHeight());
				// float scale_degree = (float)(textSize+3)/item.getmHeight();
				// drawable.setBounds(new Rect(0, 0,
				// (int)(item.getmWidth()*scale_degree), (int) ((int)(item
				// .getmHeight())*scale_degree)));
				// return drawable;
				return generateBitmapDrawable(curId, item.getmWidth(),
						item.getmHeight());
				// return zoomDrawable(getResources().getDrawable(curId), (int)
				// (item.getmWidth()*((float)textSize/item.getmHeight())),
				// textSize);
			}
		}, null);
	}

	// 将一个Drawable对象转化为Bitmap对象
	private Bitmap convertDrawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	// 缩放处理
	private Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = convertDrawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(null, newbmp);
	}

	public Drawable generateBitmapDrawable(int res_id, int mWidth, int mHeight) {
		Bitmap BitmapOrg = BitmapFactory.decodeResource(getResources(), res_id);

		// 使图片大小的缩放高度为字体大小加2
		int mSize = textSize + 2;

		float newWidth = ((float) mSize / mHeight) * mWidth;
		float newHeight = mSize;

		float scaleWidth = ((float) newWidth) / mWidth;
		float scaleHeight = ((float) newHeight) / mHeight;
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, mWidth,
				mHeight, matrix, true);

		Drawable drawable = new BitmapDrawable(null, resizedBitmap);
		drawable.setBounds(0, 0, resizedBitmap.getWidth(),
				resizedBitmap.getHeight());
		return drawable;
	}

	// 根据Id获取ImageItem对象
	private ImageItem findById(int res_id) throws Exception {
		if (tags != null && tags.size() > 0) {
			Iterator<ImageItem> it = tags.iterator();
			while (it.hasNext()) {
				ImageItem item = it.next();
				if (item != null) {
					if (item.getmId() != 0 && item.getmId() == res_id) {
						return item;
					}
				}
			}
		}
		return null;
	}
	
	public static int getResourceId(String filename) {
		try {
			Field field = R.drawable.class.getField(filename);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static String getResourceName(Context context, int res_id) {
		return context.getResources().getResourceName(res_id);
	}

	public void OutSideEvent(){
		if(imageTagListener!=null){
			imageTagListener.OnTouchEvent();
		}
	}
	
	public void setOnChangeListener(OnImageTagListener imageTagListener) {
		this.imageTagListener = imageTagListener;
		OutSideEvent();
	}

	public interface OnImageTagListener {
		public void OnTouchEvent();
	}
}

class ImageItem {

	private String mName;
	private int mId = 0;
	private int mWidth;
	private int mHeight;

	public ImageItem(Context context, String mName, int mWidth, int mHeight) {
		this.mName = mName;
		mId = ImageTextView.getResourceId(this.mName);
		this.mWidth = mWidth;
		this.mHeight = mHeight;
	}

	public ImageItem(Context context, int mId, int mWidth, int mHeight) {
		this.mId = mId;
		mName = ImageTextView.getResourceName(context, this.mId);
		this.mWidth = mWidth;
		this.mHeight = mHeight;
	}

	// 生成图片描述语言
	public String generateHtml() {
		return "<img src='"
				+ mName.substring(mName.lastIndexOf("/") + 1, mName.length())
				+ "'/>&nbsp;";
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}

	public int getmWidth() {
		return this.mWidth;
	}

	public int getmHeight() {
		return this.mHeight;
	}
}
