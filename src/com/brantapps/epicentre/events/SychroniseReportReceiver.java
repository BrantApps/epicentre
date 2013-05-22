package com.brantapps.epicentre.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Receives a message when a report has been been successfully retrieved
 * and informs any listening components that the fetch has been successful.
 *
 * @author David C Branton
 */
public class SychroniseReportReceiver extends BroadcastReceiver {
  public static final String REPORT_RETRIEVED = "com.brantapps.epicentre.events.SychroniseReportReceiver.REPORT_SYNCHRONISED";
  public static final String BROADCAST_REPORT_RETRIEVED = "com.brantapps.epicentre.events.SychroniseReportReceiver.BROADCAST_REPORT_RETRIEVED";

  /**
   * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
   */
  @Override
  public void onReceive(final Context context, final Intent intent) {
    if (intent.getAction().equals(REPORT_RETRIEVED)) {
      // Refresh the map overlay.
      final Intent mapReadyForRefresh = new Intent();
      mapReadyForRefresh.setAction(BROADCAST_REPORT_RETRIEVED);
      mapReadyForRefresh.putExtras(intent.getExtras());
      LocalBroadcastManager.getInstance(context).sendBroadcast(mapReadyForRefresh);
    }
  }
}
