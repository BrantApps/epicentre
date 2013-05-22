package com.brantapps.epicentre.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.brantapps.epicentre.events.SychroniseReportReceiver;
import com.brantapps.epicentre.handler.GeoFeatureParser;
import com.brantapps.epicentre.model.GeoFeatureCollection;
import com.brantapps.epicentre.service.GeoFeatureCollectionService;

/**
 * Fetch the latest M2.5+ 30 day report
 * from USGS.
 *
 * @author David C Branton
 */
public class FetchLatestM25Plus30DayReportTask extends AsyncTask<Void, Integer, Void> {
  private static final String M25_PLUS_30_DAY_REPORT = "http://earthquake.usgs.gov/earthquakes/feed/v0.1/summary/2.5_month.geojson";
  private final LocalBroadcastManager localBroadcastManager;
  private final GeoFeatureParser geoFeatureParser;
  private final GeoFeatureCollectionService geoFeatureCollectionService;
  private long collectionId; // ID of the latest report.


  /**
   * Constructs a database creation task.
   *
   *  @param context  The activity wrapping the task.
   */
  @Inject FetchLatestM25Plus30DayReportTask(final LocalBroadcastManager localBroadcastManager,
                                            final GeoFeatureParser geoFeatureParser,
                                            final GeoFeatureCollectionService geoFeatureCollectionService) {
    super();
    this.localBroadcastManager = localBroadcastManager;
    this.geoFeatureParser = geoFeatureParser;
    this.geoFeatureCollectionService = geoFeatureCollectionService;
  }


  /**
   * @see android.os.AsyncTask#doInBackground(Params[])
   */
  @Override
  protected Void doInBackground(final Void... params) {
    geoFeatureCollectionService.deleteAll();
    try {
      final URL geoJsonEarthquakeReport = new URL(M25_PLUS_30_DAY_REPORT);
      final GeoFeatureCollection featureCollection = new GeoFeatureCollection();
      geoFeatureParser.parseResponse(geoJsonEarthquakeReport.openStream(), featureCollection);
      collectionId = geoFeatureCollectionService.save(featureCollection);
    } catch (MalformedURLException e) {
      Log.e(this.getClass().getSimpleName(), "Error creating URL for retrieving 30 day report from USGS.");
    } catch (IOException e) {
      Log.e(this.getClass().getSimpleName(), "Error opening connection to USGS to retrieve report.");
    }
    return null;
  }


  /**
   * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
   */
  @Override
  protected void onPostExecute(final Void result) {
    super.onPostExecute(result);
    final Intent fetchCompleted = new Intent();
    fetchCompleted.setAction(SychroniseReportReceiver.REPORT_RETRIEVED);
    fetchCompleted.putExtra("collectionId", collectionId);
    localBroadcastManager.sendBroadcast(fetchCompleted);
    Log.i(this.getClass().getSimpleName(), "Fetched latest report.");
  }
}
