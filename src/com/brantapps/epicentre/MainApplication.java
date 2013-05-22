package com.brantapps.epicentre;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.content.LocalBroadcastManager;

import com.brantapps.epicentre.events.CreatedDatabaseReceiver;

import dagger.ObjectGraph;

/**
 * The application.
 *
 * @author David C Branton
 */
public class MainApplication extends Application {
  private static SQLiteDatabase database;
  private static ObjectGraph objectGraph;
  private transient OpenDatabaseReceiver openDatabaseReceiver;

  /**
   * Called when the application is starting, before any other
   * application objects have been created. Implementations
   * should be as quick as possible...
   */
  @Override
  public void onCreate() {
    super.onCreate();
    objectGraph = ObjectGraph.create(new EpicentreModule(this));
    try {
      database = SQLiteDatabase.openDatabase("/data/data/com.brantapps.epicentre/databases/epicentre.db", null, SQLiteDatabase.OPEN_READWRITE);
    } catch (SQLiteException e) {
      // DB creation will occur.
      openDatabaseReceiver = new OpenDatabaseReceiver();
      LocalBroadcastManager.getInstance(this).registerReceiver(openDatabaseReceiver, new IntentFilter(CreatedDatabaseReceiver.DATABASE_PREPARED_FOR_OPENING));
    }
  }


  /**
   * Called when the application is stopping. There are no more
   * application objects running and the process will exit.
   * <p>
   * Note: never depend on this method being called; in many
   * cases an unneeded application process will simply be killed
   * by the kernel without executing any application code...
   * <p>
   */
  @Override
  public void onTerminate() {
    if (database != null && database.isOpen()) {
      database.close();
    }
    super.onTerminate();
  }


  /**
   * Unless you are an aspect or a crutch bit of
   * functionality soon to be replaced then inject
   * the {@link SQLiteDatabase} into your class.
   *
   * @return an open database.
   */
  public static SQLiteDatabase getOpenDatabase() {
    return database;
  }


  /**
   * Inject a components dependencies.
   *
   * @param instance  The component to inject.
   */
  public static <T> void inject(final T instance) {
    objectGraph.inject(instance);
  }


  /**
   * Listener waiting for the application to finish
   * creating the database.
   * <p>
   * Once this has been completed the database is ready for I/O.
   * </p>
   *
   * @author David C Branton
   */
  public class OpenDatabaseReceiver extends BroadcastReceiver {
    public static final String BROADCAST_DATABASE_READY = "com.brantapps.epicentre.MainApplication$OpenDatabaseReceiver.BROADCAST_DATABASE_READY";

    /**
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
      database = SQLiteDatabase.openDatabase("/data/data/com.brantapps.epicentre/databases/epicentre.db", null, SQLiteDatabase.OPEN_READWRITE);
      LocalBroadcastManager.getInstance(context).unregisterReceiver(openDatabaseReceiver);
      // Broadcast event indicating that the creation process has completed.
      final Intent databaseReady = new Intent();
      databaseReady.setAction(BROADCAST_DATABASE_READY);
      LocalBroadcastManager.getInstance(context).sendBroadcast(databaseReady);
    }
  }
}