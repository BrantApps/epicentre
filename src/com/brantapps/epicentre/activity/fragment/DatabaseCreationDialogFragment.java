package com.brantapps.epicentre.activity.fragment;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.brantapps.epicentre.events.CreatedDatabaseReceiver;
import com.brantapps.epicentre.task.CreateDatabaseService;

/**
 * Fragment to manage database creations.
 * <p>
 * This fragment should not know anything about the type of activity it is running under,
 * just that it is running under 'an' activity. The fragment is only responsible for starting
 * the asynchronous task responsible for creating the database and displaying progress.
 * </p>
 * <p>
 * Communications with the parent activity, providing progress and result codes etc. is
 * completed via events with application level intent filter tags.
 * </p>
 *
 * @author David C Branton
 */
public class DatabaseCreationDialogFragment extends SherlockDialogFragment implements ServiceConnection {
  public static final String DATABASE_CREATION_FRAGMENT_TAG = "databaseCreationFragment";
  boolean isServiceConnected = false;
  private transient CreatedDatabaseReceiver createdDatabaseReceiver;


  /**
   * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createdDatabaseReceiver = new CreatedDatabaseReceiver();
    if (!isServiceRunning(CreateDatabaseService.class.getName())) {
      final Intent intent = new Intent(getSherlockActivity(), CreateDatabaseService.class);
      getSherlockActivity().startService(intent);
    }
  }


  /**
   * @see android.support.v4.app.Fragment#onResume()
   */
  @Override
  public void onResume() {
    super.onResume();
    LocalBroadcastManager.getInstance(getSherlockActivity()).registerReceiver(createdDatabaseReceiver, new IntentFilter(CreatedDatabaseReceiver.DATABASE_CREATED));
    bindCreateDatabaseService();
  }


  /**
   * @see android.support.v4.app.Fragment#onPause()
   */
  @Override
  public void onPause() {
    LocalBroadcastManager.getInstance(getSherlockActivity()).unregisterReceiver(createdDatabaseReceiver);
    unbindCreateDatabaseService();
    super.onPause();
  }


  /**
   * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
   */
  @Override
  public Dialog onCreateDialog(final Bundle savedInstanceState) {
    super.onCreateDialog(savedInstanceState);
    final ProgressDialog progressDialog = new ProgressDialog(getSherlockActivity());
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.setMessage("Preparing to rumble...");
    progressDialog.show();
    return progressDialog;
  }


  /**
   * @see android.content.ServiceConnection#onServiceConnected(android.content.ComponentName, android.os.IBinder)
   */
  @Override
  public void onServiceConnected(final ComponentName name, final IBinder iBinder) {
    isServiceConnected = true;
  }


  /**
   * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
   */
  @Override
  public void onServiceDisconnected(final ComponentName name) {
    if (isServiceConnected) {
      dismiss();
    }
  }


  /**
   * Attempt to bind to the child {@link CreateDatabaseService}.
   *
   * @return <code>true</code> if the service binds OK.
   */
  protected void bindCreateDatabaseService() {
    Intent intent = new Intent(getSherlockActivity(), CreateDatabaseService.class);
    isServiceConnected = getSherlockActivity().bindService(intent, this, 0);
  }


  /**
   * Un-bind from the {@link CreateDatabaseService}.
   */
  protected void unbindCreateDatabaseService() {
    if (isServiceConnected) {
      try {
        getSherlockActivity().unbindService(this);
      } catch (IllegalArgumentException e) {
        // The underlying service has been stopped.
      }
      dismiss();
    }
  }


  /**
   * Determine whether or not a {@link Service} is running.
   *
   * @param serviceName The name of the service to check for.
   * @return <code>true</code> if the {@link CreateDatabaseService} is started.
   */
  private boolean isServiceRunning(final String serviceName) {
    ActivityManager manager = (ActivityManager) getSherlockActivity().getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceName.equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
}