package com.brantapps.epicentre.model;


/**
 * The statements used
 * to create the application database for the first time.
 *
 * @see /assets/javadoc/db.png
 * @author David C Branton
 */
public class DatabaseCreationSQL {

  public static final String CREATE_GEO_FEATURE_COLLECTION_TABLE =
      "CREATE TABLE GeoFeatureCollection " +
          "(id INTEGER PRIMARY KEY, " +
          "generated INTEGER," +
          "url TEXT," +
          "title TEXT," +
          "subTitle TEXT, " +
          "cacheMaxAge INTEGER); ";

  public static final String CREATE_GEO_FEATURE_TABLE =
      "CREATE TABLE GeoFeature " +
          "(code TEXT NOT NULL, " +
          "collectionId INTEGER NOT NULL," +
          "geoJsonType STRING," +
          "time INTEGER," +
          "location TEXT," +
          "latitude REAL, " +
          "longitude REAL," +
          "magnitude REAL," +
          "magnitudeType TEXT," +
          "alertLevel TEXT," +
          "generatingTsunami INTEGER," +
          "PRIMARY KEY(code, collectionId)); ";

  public static final String CREATE_GEO_FEATURE_TECHNICAL_TABLE =
      "CREATE TABLE GeoFeatureTechnical " +
          "(id INTEGER PRIMARY KEY, " +
          "code TEXT," +
          "updatedTime INTEGER," +
          "depth REAL," +
          "eventPageUrl TEXT," +
          "eyeWitnessReports INTEGER," +
          "maxReportedIntensity REAL," +
          "maxInstrumentedIntensity REAL," +
          "reviewStatus TEXT," +
          "significance INTEGER," +
          "contributor STRING," +
          "contributors STRING," +
          "stationsReportingEvent INTEGER," +
          "stationDistanceToEpicentre REAL); ";

  public static final String CREATE_UPGRADE_AUDIT_TABLE_SQL =
      "CREATE TABLE UpgradeAudit " +
          "(upgradeUUID TEXT PRIMARY KEY, " +
          "description TEXT, " +
          "upgradeSequence INTEGER); ";
}
