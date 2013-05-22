package com.brantapps.epicentre.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import com.brantapps.epicentre.model.ReviewStatus;

/**
 * Test to ensure the correct operation of the
 * {@link ReviewStatus} related functionality.
 *
 * @author David C Branton
 */
public class TestReviewStatus extends TestCase {

  /**
   * Tests the error handling when an unrecognised
   * string value is returned from the API.
   */
  public void testSafeValueOfCommand() {
    final ReviewStatus decodedStatus = ReviewStatus.safeValueOf("unconfirmed");
    assertThat(decodedStatus, equalTo(ReviewStatus.UNRECOGNISED));
  }


  /**
   * Tests the encoding logic is correct when considering
   * an expected review status.
   */
  public void testCorrectEncodingOfReviewStatus() {
    final ReviewStatus decodedStatus = ReviewStatus.safeValueOf("AUTOMATIC");
    assertThat(decodedStatus, equalTo(ReviewStatus.AUTOMATIC));
  }


  /**
   * Tests the encoding logic is correct when considering
   * an expected review status.
   */
  public void testCaseSensitiveOfReviewStatusEncoding() {
    final ReviewStatus decodedStatus = ReviewStatus.safeValueOf("reviewed");
    assertThat(decodedStatus, equalTo(ReviewStatus.REVIEWED));
  }


  /**
   * Tests the encoding logic is correct when considering
   * an expected review status.
   */
  public void testNullHandlingWhenReviewStatusIsLeftUnset() {
    final ReviewStatus decodedStatus = ReviewStatus.safeValueOf(null);
    assertThat(decodedStatus, equalTo(ReviewStatus.UNRECOGNISED));
  }
}
