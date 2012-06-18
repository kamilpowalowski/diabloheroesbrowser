package com.hydrasoftworks.diablo;

import java.util.Collections;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hydrasoftworks.diablo.adapters.HeroesAdapter;
import com.hydrasoftworks.diablo.model.CareerProfile;
import com.hydrasoftworks.diablo.model.Hero;

public class HeroesFragment extends SherlockFragment {
	@SuppressWarnings("unused")
	private static final String TAG = HeroesFragment.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.heroes_fragment, container, false);
		List<Hero> heroes = CareerProfile.getActiveProfile().getHeroes();
		Collections.sort(heroes, Collections.reverseOrder());
		ListView listview = (ListView) view.findViewById(R.id.heroes_listview);
		listview.setAdapter(new HeroesAdapter(getActivity(),
				R.layout.heroes_row, heroes.toArray(new Hero[heroes.size()])));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Hero hero = (Hero) parent.getAdapter().getItem(position);
				new HeroDataDownload().execute(hero);

			}
		});
		return view;
	}

	class HeroDataDownload extends AsyncTask<Hero, Void, Hero> {

		@Override
		protected Hero doInBackground(Hero... params) {
			Hero hero = params[0];
			CareerProfile profil = CareerProfile.getActiveProfile();
			if (profil.hasDownloadedHero(hero.getId())) {
				return profil.getDownloadedHero(hero.getId());
			}

			// StringBuilder sb = new StringBuilder();
			// try {
			// URL url = hero.createUrl(profil.getBattleTag());
			// BufferedReader in = new BufferedReader(new InputStreamReader(
			// url.openStream()));
			// String inputLine;
			//
			// while ((inputLine = in.readLine()) != null)
			// sb.append(inputLine);
			//
			// in.close();
			// } catch (MalformedURLException e) {
			// Log.e(TAG, e.getMessage());
			// InfoDialogFragment.newInstance(R.string.wrong_url).show(
			// getActivity().getSupportFragmentManager(),
			// "dialogWrongUrl");
			// return null;
			// } catch (IOException e) {
			// Log.e(TAG, e.getMessage());
			// InfoDialogFragment.newInstance(R.string.no_hero).show(
			// getActivity().getSupportFragmentManager(),
			// "dialogNoHero");
			// return null;
			// }
			// String result = sb.toString();

			String result = DiabloHeroesBrowserActivity
					.getTextFromInputStream(getResources().openRawResource(
							R.raw.hero_v2));

			Gson gson = new GsonBuilder().setFieldNamingPolicy(
					FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
			Hero fullHero = gson.fromJson(result, Hero.class);
			profil.addToDownloadedHeroes(fullHero);
			return hero;
		}

		@Override
		protected void onPostExecute(Hero result) {
			super.onPostExecute(result);
			if (result != null) {
				Intent intent = getActivity().getIntent().setClass(
						getActivity(), HeroFragmentActivity.class);
				intent.putExtra(Hero.HERO_ID, result.getId());
				intent.putExtra(CareerProfileFragmentActivity.ACTIVE_TAB,
						getSherlockActivity().getSupportActionBar()
								.getSelectedNavigationIndex());
				startActivity(intent);
			}
		}

	}

	public static class InfoDialogFragment extends DialogFragment {

		public static InfoDialogFragment newInstance(int title) {
			InfoDialogFragment frag = new InfoDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			int title = getArguments().getInt("title");

			Context mContext = getActivity();
			final Dialog dialog = new Dialog(mContext);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_diablo_dialog);

			((TextView) dialog.findViewById(R.id.text)).setText(title);
			Button button = (Button) dialog.findViewById(R.id.button);
			button.setText(R.string.alert_dialog_ok);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();

				}
			});

			return dialog;
		}
	}

}
