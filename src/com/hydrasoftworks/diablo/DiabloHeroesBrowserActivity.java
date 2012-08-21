package com.hydrasoftworks.diablo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.widget.IcsSpinner;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hydrasoftworks.diablo.model.BattleTag;
import com.hydrasoftworks.diablo.model.CareerProfile;

public class DiabloHeroesBrowserActivity extends SherlockFragmentActivity {
	private static final String TAG = DiabloHeroesBrowserActivity.class
			.getSimpleName();

	private static final String PREFS_NAME = "DiabloBrowserPreferences";
	private static final String NUMBER_OF_USES = "number_of_uses";
	private BattleTagsDataSource dataSource;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataSource = new BattleTagsDataSource(this);
		dataSource.open();

		setContentView(R.layout.diablo_heroes_browser_activity);

		List<BattleTag> tags = dataSource.getAllBattleTag();
		BattleTag[] elements = tags.toArray(new BattleTag[tags.size()]);
		final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.battletag_textview);
		textView.setAdapter(new ArrayAdapter<BattleTag>(this,
				android.R.layout.simple_dropdown_item_1line, elements));

		listView = (ListView) findViewById(R.id.battletags_listview);
		final BattleTagAdapter adapter = new BattleTagAdapter(this,
				R.layout.battletag_one_result_details_row, elements);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BattleTag tag = (BattleTag) parent.getAdapter().getItem(
						position);
				downloadBattleTagData(tag);

			}

		});

		final IcsSpinner spinner = (IcsSpinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.servers_name,
						R.layout.sherlock_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdapter);

		((Button) findViewById(R.id.battletag_find_button))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String tagText = textView.getText().toString();
						if (tagText.matches("[^ $&#!%\t]{3,12}#[0-9]{4}")) {
							BattleTag tag = dataSource.createOrGetBattleTag(
									tagText,
									getResources().getStringArray(
											R.array.servers_code)[spinner
											.getSelectedItemPosition()]);
							downloadBattleTagData(tag);
						} else {
							WarningDialogFragment.newInstance(
									R.string.battletag_wrong).show(
									getSupportFragmentManager(),
									"dialogWrongBattleTag");
						}

					}
				});

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		int numberOfUses = settings.getInt(NUMBER_OF_USES, 0) + 1;
		if (numberOfUses == 2) {
			InfoDialogFragment.newInstance(R.string.info).show(
					getSupportFragmentManager(), "dialogInfo");
		}
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(NUMBER_OF_USES, numberOfUses);
		editor.commit();
	}

	@Override
	protected void onResume() {
		dataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		dataSource.close();
		super.onPause();
	}

	private void downloadBattleTagData(BattleTag tag) {
		if (!testConnection()) {
			WarningDialogFragment
					.newInstance(R.string.internt_connection_error).show(
							getSupportFragmentManager(), "dialogNoInternet");
		} else {
			new CareerProfileDataDownload().execute(tag);
		}

	}

	private boolean testConnection() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connMgr.getActiveNetworkInfo();

		return (info != null && info.isConnected());
	}

	class CareerProfileDataDownload extends
			AsyncTask<BattleTag, Integer, CareerProfile> {

		private DialogFragment dialog;

		@Override
		protected void onPreExecute() {
			List<BattleTag> tags = dataSource.getAllBattleTag();
			BattleTag[] elements = tags.toArray(new BattleTag[tags.size()]);
			final BattleTagAdapter adapter = new BattleTagAdapter(
					getApplicationContext(),
					R.layout.battletag_one_result_details_row, elements);
			listView.setAdapter(adapter);
			dialog = DownloadDialogFragment
					.newInstance(R.string.downloading_data);
			dialog.show(getSupportFragmentManager(), "download");
			super.onPreExecute();
		}

		@Override
		protected CareerProfile doInBackground(BattleTag... params) {
			BattleTag tag = params[0];
			if (CareerProfile.hasDownloadedProfile(tag)) {
				return CareerProfile.getDownloadedProfile(tag);
			}

			StringBuilder sb = new StringBuilder();
			try {
				URL url = CareerProfile.createUrl(tag);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null)
					sb.append(inputLine);

				in.close();
			} catch (MalformedURLException e) {
				Log.e(TAG, e.getMessage());
				WarningDialogFragment.newInstance(R.string.wrong_url).show(
						getSupportFragmentManager(), "dialogWrongUrl");
				return null;
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
				WarningDialogFragment.newInstance(R.string.no_career_profile)
						.show(getSupportFragmentManager(), "dialogNoProfile");
				return null;
			}
			String result = sb.toString();

			Gson gson = new GsonBuilder().setFieldNamingPolicy(
					FieldNamingPolicy.IDENTITY).create();
			CareerProfile profil = gson.fromJson(result, CareerProfile.class);
			if (profil.getHeroes() == null) {
				WarningDialogFragment.newInstance(R.string.no_career_profile)
						.show(getSupportFragmentManager(), "dialogNoProfile");
				return null;
			}
			profil.addToDownloadedProfiles(tag);
			return profil;
		}

		@Override
		protected void onPostExecute(CareerProfile result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null) {
				Intent intent = new Intent(getApplicationContext(),
						CareerProfileFragmentActivity.class);
				intent.putExtra(BattleTag.BATTLETAG, result.getBattleTag()
						.getBattleTagText());
				intent.putExtra(BattleTag.SERVER, result.getBattleTag()
						.getServer());

				startActivity(intent);
			}
		}

	}

	class BattleTagAdapter extends ArrayAdapter<BattleTag> {

		public BattleTagAdapter(Context context, int textViewResourceId,
				BattleTag[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View row;
			if (convertView == null) {
				row = getLayoutInflater().inflate(
						R.layout.battletag_one_result_details_row, parent,
						false);
			} else {
				row = convertView;
			}

			final BattleTag bt = getItem(position);
			TextView textView = (TextView) row
					.findViewById(R.id.battletag_text);
			textView.setText(Html.fromHtml(bt.getBattleTagText()
					+ "&nbsp;<font color='#a79775'><small>(" + bt.getServer()
					+ ")</small></font>"));
			Button deleteButton = (Button) row
					.findViewById(R.id.delete_entry_button);

			deleteButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					dataSource.deleteBattleTag(bt);
					List<BattleTag> tags = dataSource.getAllBattleTag();
					BattleTag[] elements = tags.toArray(new BattleTag[tags
							.size()]);
					listView.setAdapter(new BattleTagAdapter(v.getContext(),
							R.layout.battletag_one_result_details_row, elements));

				}
			});

			return row;
		}
	}

	// TODO: remove this method when ready
	public static String getTextFromInputStream(InputStream is) {
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}

		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		return writer.toString();
	}

	public static class WarningDialogFragment extends DialogFragment {

		public static WarningDialogFragment newInstance(int title) {
			WarningDialogFragment frag = new WarningDialogFragment();
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
			dialog.setContentView(R.layout.warning_dialog);

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

	public static class DownloadDialogFragment extends DialogFragment {

		public static DownloadDialogFragment newInstance(int title) {
			DownloadDialogFragment frag = new DownloadDialogFragment();
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
			dialog.setContentView(R.layout.download_dialog);

			((TextView) dialog.findViewById(R.id.text)).setText(title);
			return dialog;
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
			dialog.setContentView(R.layout.info_dialog);

			TextView tv = (TextView) dialog.findViewById(R.id.text);
			tv.setText(Html.fromHtml(getString(title)));
			tv.setMovementMethod(LinkMovementMethod.getInstance());
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