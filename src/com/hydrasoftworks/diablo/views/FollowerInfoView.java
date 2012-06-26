package com.hydrasoftworks.diablo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hydrasoftworks.diablo.R;

public class FollowerInfoView extends LinearLayout {
	public FollowerInfoView(Context context) {
		super(context);
		init(context, null);
	}

	public FollowerInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public FollowerInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		LayoutInflater li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.follower_info_view, this, true);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.FollowerInfoViewStyleable);
		Drawable drawable = typedArray
				.getDrawable(R.styleable.FollowerInfoViewStyleable_followerBackground);
		((ImageView) findViewById(R.id.follower_image)).setImageDrawable(drawable);
		typedArray.recycle();
	}
	
	public void setItemImage(Drawable drawable, int at) {
		ImageView iv = (ImageView)findViewById(at);
		iv.setImageDrawable(drawable);
	}

	public void setItemBackgroundResource(int background, int at) {
		ImageView iv = (ImageView)findViewById(at);
		iv.setBackgroundResource(background);
		
	}

	public void setItemOnClickListener(int at,
			OnClickListener onClickListener) {
		ImageView iv = (ImageView)findViewById(at);
		iv.setOnClickListener(onClickListener);
		
	}
}
