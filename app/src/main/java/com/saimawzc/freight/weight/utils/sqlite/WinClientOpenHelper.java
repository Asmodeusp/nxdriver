/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saimawzc.freight.weight.utils.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WinClientOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 4;
	private static WinClientOpenHelper instance;


	private WinClientOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}

	public static WinClientOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new WinClientOpenHelper(context.getApplicationContext());
		}
		return instance;
	}


	private static String getUserDatabaseName() {
		return  "aqcheck.db";
	}




	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		switch (oldVersion){
			case 1://新增

				break;
			case 2://每个表新增字段

				break;
			case 3:
				break;
		}

	}

	public void closeDB() {
		if (instance != null) {
			try {
				SQLiteDatabase db = instance.getWritableDatabase();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}

}
