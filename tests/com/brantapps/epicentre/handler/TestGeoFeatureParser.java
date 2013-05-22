package com.brantapps.epicentre.handler;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import com.brantapps.epicentre.model.GeoFeature;
import com.brantapps.epicentre.model.GeoFeatureCollection;
import com.brantapps.epicentre.model.GeoJsonType;
import com.brantapps.epicentre.model.ReviewStatus;


/**
 * Test the correct operation of the USGS
 * {@link GeoFeature} parser.
 *
 * @author David C Branton
 */
public class TestGeoFeatureParser extends TestCase {

  /**
   * Test to ensure the response parsing
   * for the feature collection item is correct.
   *
   * @throws IOException When there is a problem streaming the file.
   */
  public void testParsingFeatureCollection() throws IOException {
    // Given.
    final InputStream dummyUsgsResponse = new FileInputStream(new File("assets/test/usgs/v0.1/test_past_30_days.json"));
    final GeoFeatureCollection metaData = new GeoFeatureCollection();
    metaData.setTitle("USGS Magnitude 2.5+ Earthquakes, Past Month");
    metaData.setSubTitle("Real-time, worldwide earthquake list for the past month");
    metaData.setCacheMaxAge(Period.hours(9));
    metaData.setUrl(new URL("http://earthquake.usgs.gov/earthquakes/feed/v0.1/summary/2.5_month.geojson"));

    // When.
    final GeoFeatureCollection featureCollection = new GeoFeatureCollection();
    new GeoFeatureParser().parseResponse(dummyUsgsResponse, featureCollection);

    // Then.
    assertEquals("Incorrect feed title mapped.", metaData.getTitle(), featureCollection.getTitle());
    assertEquals("Incorrect sub title mapped.", metaData.getSubTitle(), featureCollection.getSubTitle());
    assertEquals("Incorrect cache max period mapped.", metaData.getCacheMaxAge().getHours(), featureCollection.getCacheMaxAge().getHours());
    assertEquals("Incorrect URL mapped.", metaData.getUrl().toExternalForm(), featureCollection.getUrl().toExternalForm());
    assertThat(featureCollection.size(), equalTo(1120));
  }


  /**
   * Test to ensure the response parsing
   * for the feature collection's geo-feature item is correct.
   *
   * @throws IOException
   */
  public void testParsingGeoFeature() throws IOException {
    // Given.
    final InputStream dummyUsgsResponse = new FileInputStream(new File("assets/test/usgs/v0.1/test_past_30_days.json"));
    final GeoFeature geoFeature = new GeoFeature(GeoJsonType.POINT, -18.6467, -177.8835);
    geoFeature.setMagnitude(5);
    geoFeature.setMagnitudeType("mb");
    geoFeature.setLocation("237km NNE of Ndoi Island, Fiji");
    final DateTimeZone timezone = DateTimeZone.forOffsetHours(-720/60);
    geoFeature.setTime(new LocalDateTime(1367660685870L, timezone));
    geoFeature.setUpdatedTime(new LocalDateTime(1367662171267L, timezone));
    geoFeature.setEventPageUrl(new URL("http://AU/earthquakes/eventpage/usc000gney"));
    geoFeature.setNoOfEyeWitnessReports(0);
    geoFeature.setMaximumReportedIntensity(1d);
    geoFeature.setMaximumInstrumentedIntensity(null);
    geoFeature.setReviewStatus(ReviewStatus.REVIEWED);
    geoFeature.setGeneratingTsunami(null);
    geoFeature.setSignificance(385);
    geoFeature.setContributorId("us");
    geoFeature.setCode("c000gney");
    geoFeature.setContributors(new String[]{"us"});
    geoFeature.setNoOfStationsReportingEvent(337);
    geoFeature.setMinDistFromEpicentreToStation(10.45);
    geoFeature.setDepth(568.06);

    // When.
    final GeoFeatureCollection featureCollection = new GeoFeatureCollection();
    new GeoFeatureParser().parseResponse(dummyUsgsResponse, featureCollection);

    // Then.
    assertThat(featureCollection, hasItem(withExpected(geoFeature)));
  }

  private Matcher<GeoFeature> withExpected(final GeoFeature geoFeatureToCompare) {
    return new TypeSafeMatcher<GeoFeature>() {

      /**
       * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
       */
      @Override
      public void describeTo(final Description description) {
        description.appendText("with geo feature collection");
      }


      /**
       * @see org.hamcrest.TypeSafeMatcher#matchesSafely(java.lang.Object)
       */
      @Override
      protected boolean matchesSafely(final GeoFeature geoFeature) {
        return geoFeature.getLatitude() == geoFeatureToCompare.getLatitude() &&
               geoFeature.getLongitude() == geoFeatureToCompare.getLongitude() &&
               geoFeature.getType() == geoFeatureToCompare.getType() &&
               geoFeature.getDepth() == geoFeatureToCompare.getDepth() &&
               geoFeature.getAlertLevel() == geoFeatureToCompare.getAlertLevel() &&
               geoFeature.getCode().equals(geoFeatureToCompare.getCode()) &&
               geoFeature.getContributorId().equals(geoFeatureToCompare.getContributorId()) &&
               geoFeature.getContributors().length == geoFeatureToCompare.getContributors().length &&
               geoFeature.getEventPageUrl().toExternalForm().equals(geoFeatureToCompare.getEventPageUrl().toExternalForm()) &&
               geoFeature.getLocation().equals(geoFeatureToCompare.getLocation()) &&
               geoFeature.getMagnitude() == geoFeatureToCompare.getMagnitude() &&
               geoFeature.getMagnitudeType().equals(geoFeatureToCompare.getMagnitudeType()) &&
               geoFeature.getMaximumInstrumentedIntensity() == geoFeatureToCompare.getMaximumInstrumentedIntensity() &&
               geoFeature.getMaximumReportedIntensity().doubleValue() == geoFeatureToCompare.getMaximumReportedIntensity().doubleValue() &&
               geoFeature.getMinDistFromEpicentreToStation() == geoFeatureToCompare.getMinDistFromEpicentreToStation() &&
               geoFeature.getNoOfEyeWitnessReports() == geoFeatureToCompare.getNoOfEyeWitnessReports() &&
               geoFeature.getNoOfStationsReportingEvent() == geoFeatureToCompare.getNoOfStationsReportingEvent() &&
               geoFeature.getReviewStatus() == geoFeatureToCompare.getReviewStatus() &&
               geoFeature.getSignificance() == geoFeatureToCompare.getSignificance() &&
               geoFeature.getTime().toDate().getTime() == geoFeatureToCompare.getTime().toDate().getTime() &&
               geoFeature.getUpdatedTime().toDate().getTime() == geoFeatureToCompare.getUpdatedTime().toDate().getTime();
      }
    };
  }
}
