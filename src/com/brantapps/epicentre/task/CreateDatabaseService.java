package com.brantapps.epicentre.task;

import static com.brantapps.epicentre.MainApplication.inject;

import javax.inject.Inject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.brantapps.epicentre.events.CreatedDatabaseReceiver;

/**
 * Service hosting the {@link CreateDatabaseTask}.
 *
 * @author David C Branton
 */
public class CreateDatabaseService extends Service {
  private StopCreateDatabaseServiceReceiver stopCreateDatabaseServiceReceiver;
  private final LocalBinder binder = new LocalBinder();
  @Inject CreateDatabaseTask createDatabaseTask;


  /**
   * @see android.app.Service#onCreate()
   */
  @Override
  public void onCreate() {
    super.onCreate();
    inject(this);
    stopCreateDatabaseServiceReceiver = new StopCreateDatabaseServiceReceiver();
    LocalBroadcastManager.getInstance(this).registerReceiver(stopCreateDatabaseServiceReceiver,
      new IntentFilter(CreatedDatabaseReceiver.DATABASE_CREATED)
    );
  }


  /**
   * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
   */
  @Override
  public int onStartCommand(final Intent intent, final int flags, final int startId) {
    super.onStartCommand(intent, flags, startId);
    Log.i(CreateDatabaseService.class.getSimpleName(), "Creating database...");
    createDatabaseTask.execute();
    return START_STICKY;
  }


  /**
   * @see android.app.Service#onBind(android.content.Intent)
   */
  @Override
  public IBinder onBind(final Intent arg0) {
    return binder;
  }


  /**
   * @see android.app.Service#onDestroy()
   */
  @Override
  public void onDestroy() {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(stopCreateDatabaseServiceReceiver);
    super.onDestroy();
  }


  /**
   * Interprocess binder for communications for all
   * components wishing to bind to this service.
   *
   * @author David C Branton
   */
  public class LocalBinder extends Binder {
    private boolean outstandingWork = true;

    /**
     * Indicates there is work outstanding.
     *
     * @param outstandingWork <code>true</code> when there is work ongoing.
     */
    protected void setOutstandingWork(final boolean outstandingWork) {
      this.outstandingWork = outstandingWork;
    }


    /**
     * @return <code>true</code> when there is work outstanding.
     */
    public boolean hasOutsandingWork() {
      return outstandingWork;
    }
  }


  /**
   * Receiver to stop the parent service.
   *
   * @author David C Branton
   */
  private class StopCreateDatabaseServiceReceiver extends BroadcastReceiver {

    /**
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context arg0, Intent arg1) {
      stopSelf();
    }
  }
}
