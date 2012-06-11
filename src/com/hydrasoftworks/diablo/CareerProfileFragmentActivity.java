package com.hydrasoftworks.diablo;

import com.hydrasoftworks.diablo.model.BattleTag;
import com.hydrasoftworks.diablo.model.CareerProfile;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CareerProfileFragmentActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle bundle) {
		CareerProfile profile = CareerProfile.getElement(getIntent()
				.getExtras().getString(BattleTag.BATTLETAG));
		findViewById(R.layout.career_profile_fragment_activity);
		super.onCreate(bundle);
	}
}
