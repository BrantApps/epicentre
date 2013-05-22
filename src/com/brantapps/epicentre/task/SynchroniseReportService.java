package com.brantapps.epicentre.task;

import static com.brantapps.epicentre.MainApplication.inject;

import javax.inject.Inject;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.brantapps.epicentre.R.drawable;
import com.brantapps.epicentre.R.string;
import com.brantapps.epicentre.events.SychroniseReportReceiver;
import com.brantapps.epicentre.service.GeoFeatureService;

/**
 * Service hosting the {@link FetchLatestM25Plus30DayReportTask}.
 *
 * @author David C Branton
 */
public class SynchroniseReportService extends Service {
  private StopSynchroniseReportReceiver stopSyncReportReceiver;
  @Inject FetchLatestM25Plus30DayReportTask fetchTask;
  @Inject GeoFeatureService geoFeatureService;

  /**
   * @see android.app.Service#onCreate()
   */
  @Override
  public void onCreate() {
    super.onCreate();
    inject(this);
    stopSyncReportReceiver = new StopSynchroniseReportReceiver();
    LocalBroadcastManager.getInstance(this).registerReceiver(stopSyncReportReceiver,
      new IntentFilter(SychroniseReportReceiver.REPORT_RETRIEVED)
    );
  }

  /**
   * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
   */
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);
    fetchTask.execute();
    addNotification(drawable.ic_menu_refresh,
                    getString(string.report_type_message),
                    getString(string.report_help_message));
    return START_STICKY;
  }


  /**
   * @see android.app.Service#onBind(android.content.Intent)
   */
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }


  /**
   * @see android.app.Service#onDestroy()
   */
  @Override
  public void onDestroy() {
    super.onDestroy();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(stopSyncReportReceiver);
  }


  private void addNotification(final int drawableRes, final String notificationTitle, final String notificationSubtitle) {
    final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
    .setSmallIcon(drawableRes)
    .setWhen(System.currentTimeMillis())
    .setAutoCancel(false)
    .setContentTitle(notificationTitle)
    .setContentText(notificationSubtitle);
    ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.getNotification());
  }


  /**
   * Receiver to stop the parent service.
   *
   * @author David C Branton
   */
  private class StopSynchroniseReportReceiver extends BroadcastReceiver {

    /**
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
      addNotification(drawable.ic_menu_sort_by_size,
                      getString(string.report_retrieved_message),
                      String.format(getString(string.report_earthquakes_found_message), geoFeatureService.count()));
      stopSelf();
    }
  }
}
