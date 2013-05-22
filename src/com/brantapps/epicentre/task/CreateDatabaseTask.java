package com.brantapps.epicentre.task;

import javax.inject.Inject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.brantapps.epicentre.events.CreatedDatabaseReceiver;

/**
 * This task creates the OceanLife database.
 *
 * @author David C Branton
 */
class CreateDatabaseTask extends AsyncTask<Void, Integer, Void> {
  public static final String DATABASE_NAME = "epicentre.db";
  private final DatabaseHelper databaseHelper;
  private final LocalBroadcastManager localBroadcastManager;


  /**
   * Constructs a database creation task.
   *
   *  @param context  The activity wrapping the task.
   */
  @Inject
  CreateDatabaseTask(final DatabaseHelper databaseHelper,
                     final LocalBroadcastManager localBroadcastManager) {
    super();
    this.databaseHelper = databaseHelper;
    this.localBroadcastManager = localBroadcastManager;
  }


  /**
   * @see android.os.AsyncTask#doInBackground(Params[])
   */
  @Override
  protected Void doInBackground(final Void... params) {
    databaseHelper.getWritableDatabase();
    return null;
  }


  /**
   * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
   */
  @Override
  protected void onPostExecute(final Void result) {
    super.onPostExecute(result);
    final Intent databaseCreated = new Intent();
    databaseCreated.setAction(CreatedDatabaseReceiver.DATABASE_CREATED);
    localBroadcastManager.sendBroadcast(databaseCreated);
    Log.i(this.getClass().getSimpleName(), "Created database.");
  }
}
