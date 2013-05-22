package com.brantapps.epicentre.activity;

import static com.brantapps.epicentre.MainApplication.inject;

import java.util.List;

import javax.inject.Inject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.brantapps.epicentre.R;
import com.brantapps.epicentre.R.id;
import com.brantapps.epicentre.R.layout;
import com.brantapps.epicentre.R.string;
import com.brantapps.epicentre.activity.fragment.DatabaseCreationDialogFragment;
import com.brantapps.epicentre.events.SychroniseReportReceiver;
import com.brantapps.epicentre.model.GeoFeature;
import com.brantapps.epicentre.model.GeoFeatureCollection;
import com.brantapps.epicentre.service.GeoFeatureCollectionService;
import com.brantapps.epicentre.service.GeoFeatureService;
import com.brantapps.epicentre.task.SynchroniseReportService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Wrapper activity for the Quake listing.
 *
 * @author David C Branton
 */
public class QuakeMapActivity extends SherlockFragmentActivity {
  private RefreshMapWithNewReportReceiver refreshMapWithNewReportReceiver;
  private SychroniseReportReceiver synchroniseReportReciever;
  private boolean hasStartedSync;
  private GoogleMap mapFragment;
  @Inject SQLiteDatabase database;
  @Inject GeoFeatureCollectionService geoFeatureCollectionService;
  @Inject GeoFeatureService geoFeatureService;

  /**
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layout.quake_map);
    inject(this);
    refreshMapWithNewReportReceiver = new RefreshMapWithNewReportReceiver();
    synchroniseReportReciever = new SychroniseReportReceiver();
    if (database == null) {
      final SherlockDialogFragment dbCreationFragment =
          (SherlockDialogFragment) SherlockDialogFragment.instantiate(this, DatabaseCreationDialogFragment.class.getName());
      dbCreationFragment.setCancelable(false);
      getSupportFragmentManager()
      .beginTransaction()
      .add(dbCreationFragment, DatabaseCreationDialogFragment.DATABASE_CREATION_FRAGMENT_TAG)
      .commit();
    } else {
      mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
      final List<GeoFeatureCollection> collections = geoFeatureCollectionService.list();
      if (!collections.isEmpty()) {
        refreshMap(collections.get(0).getIdentifier());
      }
    }
  }


  /**
   * @see android.support.v4.app.FragmentActivity#onResume()
   */
  @Override
  protected void onResume() {
    super.onResume();
    LocalBroadcastManager.getInstance(this).registerReceiver(synchroniseReportReciever,
      new IntentFilter(SychroniseReportReceiver.REPORT_RETRIEVED));
    LocalBroadcastManager.getInstance(this).registerReceiver(refreshMapWithNewReportReceiver,
      new IntentFilter(SychroniseReportReceiver.BROADCAST_REPORT_RETRIEVED));
  }


  /**
   * @see com.actionbarsherlock.app.SherlockFragmentActivity#onPause()
   */
  @Override
  protected void onPause() {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(refreshMapWithNewReportReceiver);
  }


  /**
   * @see com.actionbarsherlock.app.SherlockFragmentActivity#onCreateOptionsMenu(com.actionbarsherlock.view.Menu)
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater inflater = getSupportMenuInflater();
    inflater.inflate(R.menu.quake_map_menu, menu);
    return true;
  }


  /**
   * @see com.actionbarsherlock.app.SherlockFragmentActivity#onPrepareOptionsMenu(com.actionbarsherlock.view.Menu)
   */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    final MenuItem refreshItem = menu.findItem(id.sync);
    if (isSynchronisationServiceRunning() || hasStartedSync) {
      refreshItem.setEnabled(false);
      if (Build.VERSION.SDK_INT > 10) {
        // Replace the refresh icon with an indeterminate spinner for > 10 API.
        final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        refreshItem.setActionView(inflater.inflate(layout.actionbar_indeterminate_progress, null));
      }
    } else {
      refreshItem.setEnabled(true);
      refreshItem.setActionView(null);
    }

    return true;
  }


  /**
   * @see com.actionbarsherlock.app.SherlockFragmentActivity#onOptionsItemSelected(com.actionbarsherlock.view.MenuItem)
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);
    if (item.getItemId() == id.sync) {
      final Intent fetchReport = new Intent(this, SynchroniseReportService.class);
      startService(fetchReport);
      hasStartedSync = true;
      invalidateOptionsMenu();
    }
    Crouton.makeText(this, string.retrieving_latest_report_message, Style.INFO).show();
    return true;
  }

  private void refreshMap(final long collectionId) {
    if (collectionId > -1) {
      mapFragment.clear();
      Crouton.makeText(this, string.refreshing_map_message, Style.INFO).show();
      final BitmapDescriptor redBitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
      final BitmapDescriptor amberBitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
      final BitmapDescriptor blueBitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
      final BitmapDescriptor violetBitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
      final GeoFeatureCollection collection = geoFeatureCollectionService.load(collectionId);

      getSupportActionBar()
      .setSubtitle(String.format(getString(string.displayed_report_title,
                   collection.getTitle(),
                   collection.getGenerated().toString())));
      for (GeoFeature feature : geoFeatureService.list(collectionId)) {
        final BitmapDescriptor markerColour;
        if (feature.getMagnitude() < 4) {
          markerColour = blueBitmapDescriptor;
        } else if (feature.getMagnitude() < 5.5) {
          markerColour = amberBitmapDescriptor;
        } else if (feature.getMagnitude() < 7) {
          markerColour = redBitmapDescriptor;
        } else {
          markerColour = violetBitmapDescriptor;
        }
        mapFragment.addMarker(new MarkerOptions()
        .position(new LatLng(feature.getLatitude(), feature.getLongitude()))
        .title(String.format(getString(string.map_balloon_title), feature.getMagnitude(), feature.getLocation()))
        .snippet(String.format(getString(string.map_snippet_title), feature.getTime().toString("yyyy-MM-dd HH:mm")))
        .icon(markerColour));
      }
    } else {
      Crouton.makeText(this, string.will_not_refresh_map_message, Style.ALERT).show();
    }
  }

  private boolean isSynchronisationServiceRunning() {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (SynchroniseReportService.class.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }


  /**
   * Receiver to signal to the activity to refresh the map view.
   *
   * @author David C Branton
   */
  private class RefreshMapWithNewReportReceiver extends BroadcastReceiver {

    /**
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
      hasStartedSync = false;
      refreshMap(intent.getLongExtra("collectionId", -1));
      invalidateOptionsMenu();
    }
  }
}
