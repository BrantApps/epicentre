package com.brantapps.epicentre.task;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brantapps.epicentre.model.DatabaseCreationSQL;

/**
 * Creates a database helper.
 *
 * @author David C Branton
 */
public class DatabaseHelper extends SQLiteOpenHelper {

  /**
   * Constructs a database helper.
   *
   * @param context The activity context.
   * @throws NameNotFoundException When the com.brantapps.epicentre package is not found.
   */
  @Inject
  DatabaseHelper(final Context context) throws NameNotFoundException {
    super(context, "epicentre.db", null, context.getPackageManager().getPackageInfo("com.brantapps.epicentre", 0).versionCode);
  }


  /**
   * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
   */
  @Override
  public void onCreate(final SQLiteDatabase database) {
    database.execSQL(DatabaseCreationSQL.CREATE_GEO_FEATURE_COLLECTION_TABLE);
    database.execSQL(DatabaseCreationSQL.CREATE_GEO_FEATURE_TABLE);
    database.execSQL(DatabaseCreationSQL.CREATE_GEO_FEATURE_TECHNICAL_TABLE);
    database.execSQL(DatabaseCreationSQL.CREATE_UPGRADE_AUDIT_TABLE_SQL);
  }


  /**
   * Upgrade the database to the latest version.
   * <p>
   * This 'clever' code iterates over the upgrades that have <strong>not</strong> been applied
   * before furthering the upgrade.
   * </p>
   *
   * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
   */
  @Override
  public void onUpgrade(
      final SQLiteDatabase database,
      final int oldVersion,
      final int newVersion) {
    Set<String> failedUpgrades = new HashSet<String>();
    executeUpgrades(database, oldVersion, newVersion, failedUpgrades);
  }


  /**
   * Perform a database upgrade.
   *
   * @param database  The database to upgrade
   * @param oldVersion  The database version from the previous application instance.
   * @param newVersion  The current database version.
   * @param failedUpgrades  A set of failed upgrades.
   */
  public void executeUpgrades(final SQLiteDatabase database, final int oldVersion, final int newVersion, final Set<String> failedUpgrades) {
    // Upgrades here when we need that.
  }
}
