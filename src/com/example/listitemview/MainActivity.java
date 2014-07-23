package com.example.listitemview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listitemview.ImageTextView.OnImageTagListener;
import com.example.listitemview.MyAdapter.MyHolder;

public class MainActivity extends Activity {

	private ImageTextView view;
	private ImageTextView view2;
	private ImageTextView view3;
	private ListView lv;
	private MyAdapter adapter;
	private Button button;
	private Button addButton;
	
	TextPaint paint;
	private String text = "随手记卡牛";
    private Rect bRect = new Rect();

    
    private BGText text2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		text2 = new BGText(MainActivity.this, null);
		setContentView(text2);
		
//		paint = new TextPaint();
//		paint.getTextBounds(text, 0, text.length()-1, bRect);
//		
//		Log.i("tag","the textSize is:"+paint.measureText(text));
//		Log.i("tag","the Bounds is:"+(bRect.bottom-bRect.top));
//		Log.i("tag","the height is:"+(bRect.bottom-bRect.top)+" the width is:"+Math.abs((bRect.left-bRect.right)));

/*		this.lv = (ListView) this.findViewById(R.id.myList);

		view = new ImageTextView(MainActivity.this);
		view2 = new ImageTextView(MainActivity.this);
		view3 = new ImageTextView(MainActivity.this);

		// view = (ListItemView) this.findViewById(R.id.view);
		// view.addImageTagById(R.drawable.tag0, ImageTextView.INSERT_FIRST);
		// view.addImageTagById(R.drawable.tag1, ImageTextView.INSERT_FIRST);
		// view.addImageTagById(R.drawable.tag2, ImageTextView.INSERT_FIRST);
		// view.setText("【理财经】菜鸟理财学堂|影响家庭财物健康的纠结问题之（一）");
		// view.setOnTouchListener(new OnImageTagListener() {
		//
		// @Override
		// public void OnTouchEvent(int img_id) {
		// // TODO Auto-generated method stub
		// view.removeImageTagById(img_id);
		// }
		// });

		// view2 = (ListItemView)this.findViewById(R.id.view2);
		view2.addImageTagById(R.drawable.tag2, 0);
		view2.addImageTagById(R.drawable.tag0, 0);
		view2.setText("【杂谈】现在大学生生活消费这么高？？");

		// view3 = (ListItemView)this.findViewById(R.id.view3);
		view3.addImageTagById(R.drawable.tag4, 0);
		view3.setText("如何才能理智消费");

		adapter = new MyAdapter(MainActivity.this, null);
		adapter.addItem(view);
		adapter.addItem(view2);
		adapter.addItem(view3);

		lv.setAdapter(adapter);
		// button = (Button)this.findViewById(R.id.btn);
		// button.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// view.removeImageTagById(R.drawable.tag1);
		// }
		// });
		// addButton = (Button)this.findViewById(R.id.add);
		// addButton.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// view.addImageTagById(R.drawable.sui, ListItemView.INSERT_LAST);
		// }
		// });
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				MyHolder holder = (MyHolder)view.getTag();
				final ImageTextView itv = holder.view;
				itv.setOnChangeListener(new OnImageTagListener() {

					@Override
					public void OnTouchEvent() {
						itv.addImageTagById(R.drawable.tag4, 0);
					}
				});
			}
		});*/
	}
}

class MyAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ImageTextView> list;

	public MyAdapter(Context context, List<ImageTextView> list) {
		this.context = context;
		this.list = list;
		if (this.list == null) {
			this.list = new ArrayList<ImageTextView>();
		}
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	public void addItem(ImageTextView view) {
		this.list.add(view);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			holder.view = (ImageTextView) convertView.findViewById(R.id.cl);
			holder.tv1 = (TextView) convertView.findViewById(R.id.user);
			holder.tv2 = (TextView) convertView.findViewById(R.id.count);

		} else {
			holder = (MyHolder) convertView.getTag();
		}
		holder.view
				.addImageTagById(R.drawable.tag0, ImageTextView.INSERT_FIRST);
		holder.view
				.addImageTagById(R.drawable.tag1, ImageTextView.INSERT_FIRST);
		holder.view
				.addImageTagById(R.drawable.tag2, ImageTextView.INSERT_FIRST);
		holder.view.setText("【理财经】菜鸟理财学堂|影响家庭财物健康的纠结问题之（一）");
		convertView.setTag(holder);
		// holder.view.setOnTouchListener(new OnImageTagListener() {
		//
		// @Override
		// public void OnTouchEvent(int img_id) {
		// // TODO Auto-generated method stub
		// holder.view.removeImageTagById(img_id);
		// }
		// });
		return convertView;
	}

	class MyHolder {
		ImageTextView view;
		TextView tv1;
		TextView tv2;
	}
}