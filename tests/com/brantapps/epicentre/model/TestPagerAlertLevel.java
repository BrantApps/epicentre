package com.brantapps.epicentre.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import com.brantapps.epicentre.model.PagerAlertLevel;

/**
 * Test to ensure the correct operation of the
 * {@link PagerAlertLevel} related functionality.
 *
 * @author David C Branton
 */
public class TestPagerAlertLevel extends TestCase {

  /**
   * Tests the error handling when an unrecognised
   * string value is returned from the API.
   */
  public void testSafeValueOfCommand() {
    final PagerAlertLevel decodedLevel = PagerAlertLevel.safeValueOf("PURPLE");
    assertThat(decodedLevel, equalTo(PagerAlertLevel.UNRECOGNISED));
  }


  /**
   * Tests the encoding logic is correct when considering
   * an expected pager alert level.
   */
  public void testCorrectEncodingOfPagerAlertLevel() {
    final PagerAlertLevel decodedLevel = PagerAlertLevel.safeValueOf("red");
    assertThat(decodedLevel, equalTo(PagerAlertLevel.RED));
  }


  /**
   * Tests the encoding logic is correct when considering
   * an expected alert level in the 'wrong' case.
   */
  public void testCaseSensitiveOfPagerAlertLevelEncoding() {
    final PagerAlertLevel decodedLevel = PagerAlertLevel.safeValueOf("RED");
    assertThat(decodedLevel, equalTo(PagerAlertLevel.RED));
  }


  /**
   * Tests the error handling logic when considering a
   * <code>null</code> alert level.
   */
  public void testNullHandlingWhenPagerAlertLevelIsLeftUnset() {
    final PagerAlertLevel decodedLevel = PagerAlertLevel.safeValueOf(null);
    assertThat(decodedLevel, equalTo(PagerAlertLevel.UNRECOGNISED));
  }
}
