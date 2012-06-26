package com.hydrasoftworks.diablo.views;

import com.hydrasoftworks.diablo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SkillView extends RelativeLayout {
	private TextView skillName;
	private TextView runeName;
	private ImageView skillImage;

	public SkillView(Context context) {
		super(context);
		init(context, null);
	}

	public SkillView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SkillView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.skill_button, this, true);

		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.SkillViewStyleable);
		Drawable drawable = typedArray
				.getDrawable(R.styleable.SkillViewStyleable_positionIcon);
		int value = typedArray.getInt(
				R.styleable.SkillViewStyleable_positionText, 0);
		TextView placeIcon = (TextView) findViewById(R.id.skill_small_icon);
		if (value > 0) {
			placeIcon.setText("" + value);
		} else if (drawable != null) {
			placeIcon.setBackgroundDrawable(drawable);
		}

		typedArray.recycle();

		skillName = (TextView) findViewById(R.id.skill_name);
		runeName = (TextView) findViewById(R.id.skill_rune);
		skillImage = (ImageView) findViewById(R.id.skill_icon);
	}

	public void setSkillName(String text) {
		skillName.setText(text);
	}

	public void setRuneName(String text) {
		runeName.setText(text);
	}

	public void setRuneImage(Drawable drawble) {
		runeName.setCompoundDrawablesWithIntrinsicBounds(drawble, null, null,
				null);
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
