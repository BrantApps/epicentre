package com.brantapps.epicentre.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;

import android.content.ContentValues;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.brantapps.epicentre.model.GeoFeature;
import com.brantapps.epicentre.model.GeoJsonType;
import com.brantapps.epicentre.model.PagerAlertLevel;

/**
 * Access to stored geographic feature.
 *
 * @author David C Branton
 */
public class GeoFeatureService implements ServiceInterface<GeoFeature> {
  private static final String FEATURE_TABLE = "GeoFeature";
  private final SQLiteDatabase database;

  @Inject GeoFeatureService(final SQLiteDatabase database) {
    this.database = database;
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#load(long, java.lang.Double[])
   */
  @Override
  public GeoFeature load(final long index, final Double... preferences) {
    return null;
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#list(java.lang.Long[])
   */
  @Override
  public List<GeoFeature> list(final Long... longs) {
    final SQLiteCursor featureCursor =
        (SQLiteCursor) database.query(
          FEATURE_TABLE,
          null,
          "collectionId = ?",
          new String[]{Long.toString(longs[0])},
          null,
          null,
          "time DESC");
    return buildGeoFeatures(featureCursor);
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#save(java.lang.Object, java.lang.Long[])
   */
  @Override
  public long save(final GeoFeature persistedModel, final Long... longs) {
    return database.insert(FEATURE_TABLE, null, buildColumnMappings(persistedModel, longs[0]));
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#delete(java.lang.Long[])
   */
  @Override
  public void delete(final Long... longs) {
    database.delete(FEATURE_TABLE,
                    "collectionId = ?",
                    new String[]{Long.toString(longs[0])});
  }


  /**
   * Delete all previous data.
   */
  public void deleteAll(final Long... longs) {
    database.delete(FEATURE_TABLE, null, null);
  }


  /**
   * Count the number of geo feature events that exist.
   */
  public long count() {
    final SQLiteStatement statement = database.compileStatement("SELECT COUNT(*) FROM " + FEATURE_TABLE);
    return (int) statement.simpleQueryForLong();
  }


  private ContentValues buildColumnMappings(final GeoFeature geoFeature, final Long collectionId) {
    final ContentValues values = new ContentValues();
    values.put("code", geoFeature.getCode());
    values.put("collectionId", collectionId);
    values.put("geoJsonType", geoFeature.getType() == null ? null : geoFeature.getType().getType());
    values.put("time", geoFeature.getTime().toDate().getTime());
    values.put("location", geoFeature.getLocation());
    values.put("latitude", geoFeature.getLatitude());
    values.put("longitude", geoFeature.getLongitude());
    values.put("magnitude", geoFeature.getMagnitude());
    values.put("magnitudeType", geoFeature.getMagnitudeType());
    values.put("alertLevel", geoFeature.getAlertLevel() == null ? null : geoFeature.getAlertLevel().getLevel());
    values.put("generatingTsunami", geoFeature.isGeneratingTsunami());
    return values;
  }


  private List<GeoFeature> buildGeoFeatures(final SQLiteCursor cursor) {
    final List<GeoFeature> geoFeatures = new ArrayList<GeoFeature>();

    while (cursor.moveToNext()) {
      geoFeatures.add(buildGeoFeature(cursor));
    }

    return geoFeatures;
  }

  private GeoFeature buildGeoFeature(final SQLiteCursor cursor) {
    if (cursor.isBeforeFirst()) {
      cursor.moveToNext();
    }

    final GeoFeature geoFeature =
        new GeoFeature(GeoJsonType.safeValueOf(cursor.getString(2)),
                                               cursor.getDouble(5),
                                               cursor.getDouble(6));
    geoFeature.setCode(cursor.getString(0));
    geoFeature.setTime(new LocalDateTime(cursor.getLong(3)));
    geoFeature.setLocation(cursor.getString(4));
    geoFeature.setMagnitude(cursor.getDouble(7));
    geoFeature.setMagnitudeType(cursor.getString(8));
    geoFeature.setAlertLevel(PagerAlertLevel.safeValueOf(cursor.getString(9)));
    if (cursor.getInt(10) == 1) {
      geoFeature.setGeneratingTsunami(true);
    } else if (cursor.getInt(10) == 0) {
      geoFeature.setGeneratingTsunami(false);
    }

    return geoFeature;
  }
}
