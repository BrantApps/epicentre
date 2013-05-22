package com.brantapps.epicentre.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Receives a message when the the database has been created and
 * broadcasts a different message to any other listeners awaiting the outcome of the db creation.
 *
 * @author David C Branton
 */
public class CreatedDatabaseReceiver extends BroadcastReceiver {
  public static final String DATABASE_CREATED = "com.brantapps.epicentre.events.CreatedDatabaseReceiver.DATABASE_CREATED";
  public static final String DATABASE_PREPARED_FOR_OPENING = "com.brantapps.epicentre.events.CreatedDatabaseReceiver.DATABASE_PREPARED_FOR_OPENING";

  /**
   * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
   */
  @Override
  public void onReceive(final Context context, final Intent intent) {
    if (intent.getAction().equals(DATABASE_CREATED)) {
      // Prepare the database for I/O.
      final Intent databaseReady = new Intent();
      databaseReady.setAction(DATABASE_PREPARED_FOR_OPENING);
      LocalBroadcastManager.getInstance(context).sendBroadcast(databaseReady);
    }
  }
}