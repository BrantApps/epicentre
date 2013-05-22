package com.brantapps.epicentre.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import android.content.ContentValues;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.brantapps.epicentre.model.GeoFeature;
import com.brantapps.epicentre.model.GeoFeatureCollection;

/**
 * Access the geographic feature reports.
 *
 * @author David C Branton
 */
public class GeoFeatureCollectionService implements ServiceInterface<GeoFeatureCollection> {
  private static final String COLLECTION_TABLE = "GeoFeatureCollection";
  private final SQLiteDatabase database;
  private final GeoFeatureService geoFeatureService;


  @Inject GeoFeatureCollectionService(final SQLiteDatabase database,
                                      final GeoFeatureService geoFeatureService) {
    this.database = database;
    this.geoFeatureService = geoFeatureService;
  }


  /**
   * @throws ParseException When a date cannot be parsed.
   * @throws MalformedURLException  When a URL is constructed incorrectly.
   *
   * @see com.brantapps.epicentre.service.ServiceInterface#load(long, java.lang.Double[])
   */
  @Override
  public GeoFeatureCollection load(final long index, final Double... preferences) {
    GeoFeatureCollection collection = null;

    // Collection.
    final SQLiteCursor collectionCursor =
        (SQLiteCursor) database.query(
          COLLECTION_TABLE,
          null,
          "id = ?",
          new String[]{Long.toString(index)},
          null,
          null,
          "id desc");
    try {
      collection = buildGeoCollection(collectionCursor);
      collection.addAll(geoFeatureService.list(index));
    } catch (MalformedURLException e) {
      Log.e(this.getClass().getSimpleName(), "Incorrect URL constructed for report");
    }

    collectionCursor.close();
    return collection;
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#list(java.lang.Long[])
   */
  @Override
  public List<GeoFeatureCollection> list(final Long... longs) {
    final SQLiteCursor collectionCursor =
        (SQLiteCursor) database.query(
          COLLECTION_TABLE,
          null,
          null,
          null,
          null,
          null,
          "generated DESC");
    return buildGeoCollections(collectionCursor);
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#save(java.lang.Object, java.lang.Long[])
   */
  @Override
  public long save(final GeoFeatureCollection persistedModel, final Long... longs) {
    final long featureCollectionId = database.insert(COLLECTION_TABLE, null, buildColumnMappings(persistedModel));
    for (GeoFeature feature : persistedModel) {
      geoFeatureService.save(feature, featureCollectionId);
    }
    return featureCollectionId;
  }


  /**
   * @see com.brantapps.epicentre.service.ServiceInterface#delete(java.lang.Long[])
   */
  @Override
  public void delete(final Long... longs) {
    database.delete(COLLECTION_TABLE,
                    "identifier = ?",
                    new String[]{longs[0].toString()});
  }


  /**
   * Delete all previous data.
   */
  public void deleteAll(final Long... longs) {
    database.delete(COLLECTION_TABLE, null, null);
    geoFeatureService.deleteAll();
  }


  private List<GeoFeatureCollection> buildGeoCollections(final SQLiteCursor cursor) {
    final List<GeoFeatureCollection> geoFeatureCollections = new ArrayList<GeoFeatureCollection>();

    while (cursor.moveToNext()) {
      try {
        geoFeatureCollections.add(buildGeoCollection(cursor));
      } catch (MalformedURLException e) {
        Log.e(this.getClass().getSimpleName(), "Incorrect URL constructed for report");
      }
    }

    return geoFeatureCollections;
  }


  private GeoFeatureCollection buildGeoCollection(final SQLiteCursor cursor) throws MalformedURLException {
    final GeoFeatureCollection geoFeatureCollection = new GeoFeatureCollection();
    if (cursor.isBeforeFirst()) {
      cursor.moveToNext();
    }
    geoFeatureCollection.setIdentifier(cursor.getLong(0));
    geoFeatureCollection.setGenerated(new LocalDateTime(cursor.getLong(1)));
    geoFeatureCollection.setUrl(new URL(cursor.getString(2)));
    geoFeatureCollection.setTitle(cursor.getString(3));
    geoFeatureCollection.setSubTitle(cursor.getString(4));
    geoFeatureCollection.setCacheMaxAge(Period.parse(cursor.getString(5)));
    return geoFeatureCollection;
  }


  /**
   * Maps the {@link GeoFeatureCollection} to the appropriate database table
   * columns.
   *
   * @param geoFeatureCollection  The collection to save.
   * @return  A set of key value pairs of database column name to value.
   */
  private ContentValues buildColumnMappings(final GeoFeatureCollection geoFeatureCollection) {
    final ContentValues values = new ContentValues();
    values.put("subTitle", geoFeatureCollection.getSubTitle());
    values.put("title", geoFeatureCollection.getTitle());
    values.put("generated", geoFeatureCollection.getGenerated().toDate().getTime());
    values.put("cacheMaxAge", geoFeatureCollection.getCacheMaxAge().toPeriod().toString());
    values.put("url", geoFeatureCollection.getUrl().toExternalForm());
    return values;
  }
}
