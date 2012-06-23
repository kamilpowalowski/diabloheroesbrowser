package com.hydrasoftworks.diablo.views;

import com.hydrasoftworks.diablo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PassiveSkillView extends RelativeLayout {
	private TextView skillName;
	private ImageView skillImage;

	public PassiveSkillView(Context context) {
		super(context);
		init(context);
	}

	public PassiveSkillView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PassiveSkillView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) context.getSystemService(service);
		li.inflate(R.layout.passive_skill_button, this, true);

		skillName = (TextView) findViewById(R.id.skill_name);
		skillImage = (ImageView) findViewById(R.id.skill_icon);
	}

	public void setSkillName(String text) {
		skillName.setText(text);
	}

	public void setSkillImage(Drawable drawable) {
		skillImage.setImageDrawable(drawable);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		Button btn = (Button) findViewById(R.id.skill_button);
		btn.setOnClickListener(l);
	}
}
