package com.brantapps.epicentre.task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentCaptor;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.test.InstrumentationTestCase;

import com.brantapps.epicentre.events.CreatedDatabaseReceiver;

/**
 * Test database creation mechanism.
 *
 * @author David C Branton
 */
public class TestCreateDatabaseTask extends InstrumentationTestCase {

  /**
   * Test call path to {@link DatabaseHelper}.
   */
  public void testLinkToDatabaseHelper() {
    // Given.
    final DatabaseHelper dummyDatabaseHelper = mock(DatabaseHelper.class);

    // When.
    new CreateDatabaseTask(dummyDatabaseHelper, mock(LocalBroadcastManager.class)).doInBackground();

    // Then.
    verify(dummyDatabaseHelper).getWritableDatabase();
  }


  /**
   * Test correct event is fired post creation attempt.
   */
  public void testEventIsFiredOnCompletion() {
    // Given.
    final LocalBroadcastManager localBroadcastManager = mock(LocalBroadcastManager.class);
    final DatabaseHelper dummyDatabaseHelper = mock(DatabaseHelper.class);

    // When.
    new CreateDatabaseTask(dummyDatabaseHelper, localBroadcastManager).onPostExecute(null);

    // Then.
    ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
    verify(localBroadcastManager).sendBroadcast(intentCaptor.capture());
    assertEquals("Database created event was not fired.", CreatedDatabaseReceiver.DATABASE_CREATED, intentCaptor.getValue().getAction());
  }
}
