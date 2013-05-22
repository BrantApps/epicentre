package com.brantapps.epicentre;

import static com.brantapps.epicentre.MainApplication.getOpenDatabase;

import javax.inject.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.brantapps.epicentre.activity.QuakeMapActivity;
import com.brantapps.epicentre.task.CreateDatabaseService;
import com.brantapps.epicentre.task.SynchroniseReportService;

import dagger.Module;
import dagger.Provides;

/**
 * The injection container providing information on
 * how to construct the application components
 * alongside with module specific overrides.
 *
 * @author David C Branton
 */
@Module(entryPoints = {QuakeMapActivity.class, CreateDatabaseService.class, SynchroniseReportService.class},
           complete = true)
public class EpicentreModule {
  private Context context = null;

  /**
   * The application module.
   *
   * @param context The context.
   */
  public EpicentreModule(final Context context) {
    this.context = context.getApplicationContext();
  }


  /**
   * Provides context.
   *
   * @return the application context.
   */
  @Provides @Singleton Context provideContext() {
    return context;
  }


  /**
   * Provide the database.
   *
   * @return the application database.
   */
  @Provides SQLiteDatabase provideDatabase() {
    return getOpenDatabase();
  }


  /**
   * Provide the local broadcast manager.
   *
   * @param context application context.
   * @return the broadcast manager.
   */
  @Provides LocalBroadcastManager provideLocalBroadcastManager() {
    return LocalBroadcastManager.getInstance(context);
  }

}
