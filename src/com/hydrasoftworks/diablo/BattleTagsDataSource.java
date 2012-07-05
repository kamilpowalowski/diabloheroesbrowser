package com.hydrasoftworks.diablo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hydrasoftworks.diablo.model.BattleTag;

public class BattleTagsDataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	private String[] allColumns = { BattleTag.BATTLETAG_ID,
			BattleTag.BATTLETAG, BattleTag.VALUE, BattleTag.SERVER };

	public BattleTagsDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public BattleTag createOrGetBattleTag(String text, String server) {
		BattleTag tag = findBattleTag(text, server);
		if (tag == null)
			tag = createBattleTag(text, server);
		else
			updateBattleTag(tag);
		return tag;
	}

	private BattleTag createBattleTag(String text, String server) {

		ContentValues values = new ContentValues();
		values.put(BattleTag.BATTLETAG, text);
		values.put(BattleTag.VALUE, 1);
		values.put(BattleTag.SERVER, server);
		long insertId = database.insert(BattleTag.TABLE_NAME, null, values);
		Cursor cursor = database.query(BattleTag.TABLE_NAME, allColumns,
				BattleTag.BATTLETAG_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		BattleTag tag = cursorToBattleTag(cursor);
		cursor.close();
		return tag;

	}

	private void updateBattleTag(BattleTag tag) {
		ContentValues values = new ContentValues();
		values.put(BattleTag.BATTLETAG_ID, tag.getId());
		values.put(BattleTag.BATTLETAG, tag.getBattleTagText());
		values.put(BattleTag.VALUE, tag.getValue() + 1);
		values.put(BattleTag.SERVER, tag.getServer());
		database.update(BattleTag.TABLE_NAME, values, BattleTag.BATTLETAG_ID
				+ " = " + tag.getId(), null);
	}

	private BattleTag cursorToBattleTag(Cursor cursor) {
		BattleTag tag = new BattleTag();
		tag.setId(cursor.getLong(0));
		tag.setBattleTagText(cursor.getString(1));
		tag.setValue(cursor.getInt(2));
		tag.setServer(cursor.getString(3));
		return tag;
	}

	public void deleteBattleTag(BattleTag tag) {
		long id = tag.getId();
		database.delete(BattleTag.TABLE_NAME, BattleTag.BATTLETAG_ID + " = "
				+ id, null);
	}

	private BattleTag findBattleTag(String name, String server) {
		Cursor cursor = database.query(BattleTag.TABLE_NAME, allColumns,
				BattleTag.BATTLETAG + " = '" + name + "' AND " + BattleTag.SERVER
						+ " = '" + server + "'", null, null, null, null);
		BattleTag tag = null;
		cursor.moveToFirst();
		if (cursor.getCount() > 0)
			tag = cursorToBattleTag(cursor);
		cursor.close();
		return tag;
	}

	public List<BattleTag> getAllBattleTag() {
		List<BattleTag> tags = new ArrayList<BattleTag>();

		Cursor cursor = database.query(BattleTag.TABLE_NAME, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BattleTag tag = cursorToBattleTag(cursor);
			tags.add(tag);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		Collections.sort(tags, Collections.reverseOrder());
		return tags;
	}
}
