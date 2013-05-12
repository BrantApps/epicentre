package com.brantapps.epicentre.model;

import java.net.URL;

import org.joda.time.LocalDate;

/**
 * An earth feature.
 *
 * @author David C Branton
 */
public class GeoFeature {
  private final double latitude;
  private final double longitude;
  private double depth;
  private double magnitude;
  private String location;
  private LocalDate time;
  private LocalDate updatedTime;
  private URL eventPageUrl;
  private int noOfEyeWitnessReports;
  private Double maximumReportedIntensity;
  private Double maximumInstrumentedIntensity;
  private PagerAlertLevel alertLevel;
  private ReviewStatus reviewStatus;
  private Boolean generatingTsunami;
  private int significance;
  private String contributorId;
  private String code;
  private String[] contributors;
  private int noOfStationsReportingEvent;
  private double minDistFromEpicentreToStation;
  private String magnitudeType;

  /**
   * Constructs a {@link GeoFeature} for
   * a set of coordinates.
   *
   * @param latitude  the latitude position.
   * @param longitude the longitude position.
   */
  public GeoFeature(final GeoJsonType type,
                    final double latitude,
                    final double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }


  /**
   * @return the latitude position on the earth.
   */
  public double getLatitude() {
    return latitude;
  }


  /**
   * @return the longitude position on the earth.
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * @return the depth the quake occurred.
   */
  public double getDepth() {
    return depth;
  }


  /**
   * Assign the event depth.
   *
   * @param depth Event depth.
   */
  public void setDepth(final double depth) {
    this.depth = depth;
  }


  /**
   * @return the magnitude of the quake.
   */
  public double getMagnitude() {
    return magnitude;
  }


  /**
   * @param magnitude Assign the earthquake magnitude.
   */
  public void setMagnitude(final double magnitude) {
    this.magnitude = magnitude;
  }


  /**
   * @return the location of the nearest place by name.
   */
  public String getLocation() {
    return location;
  }


  /**
   * @param location Assign the location of the nearest place.
   */
  public void setLocation(final String location) {
    this.location = location;
  }


  /**
   * @return the time the quake occurred.
   */
  public LocalDate getTime() {
    return time;
  }


  /**
   * @param time Assign the date the quake occurred.
   */
  public void setTime(final LocalDate time) {
    this.time = time;
  }


  /**
   * @return the updatedTime of the report.
   */
  public LocalDate getUpdatedTime() {
    return updatedTime;
  }


  /**
   * @param updatedTime Assign the updated time of the report.
   */
  public void setUpdatedTime(final LocalDate updatedTime) {
    this.updatedTime = updatedTime;
  }


  /**
   * @return the eventPageUrl tracking the quake by USGS.
   */
  public URL getEventPageUrl() {
    return eventPageUrl;
  }


  /**
   * @param eventPageUrl Assign the event quake URL page.
   */
  public void setEventPageUrl(final URL eventPageUrl) {
    this.eventPageUrl = eventPageUrl;
  }


  /**
   * @return the noOfEyeWitnessReports.
   */
  public int getNoOfEyeWitnessReports() {
    return noOfEyeWitnessReports;
  }


  /**
   * @param noOfEyeWitnessReports Assign the number of eye witness reports.
   */
  public void setNoOfEyeWitnessReports(final int noOfEyeWitnessReports) {
    this.noOfEyeWitnessReports = noOfEyeWitnessReports;
  }


  /**
   * @return the maximumReportedIntensity of the quake.
   */
  public Double getMaximumReportedIntensity() {
    return maximumReportedIntensity;
  }


  /**
   * @param maximumReportedIntensity Assign the maximum reported intensity of the quake.
   */
  public void setMaximumReportedIntensity(final Double maximumReportedIntensity) {
    this.maximumReportedIntensity = maximumReportedIntensity;
  }


  /**
   * @return the maximumInstrumentedIntensity of the quake.
   */
  public Double getMaximumInstrumentedIntensity() {
    return maximumInstrumentedIntensity;
  }


  /**
   * @param maximumInstrumentedIntensity Assign the maximum instrumented intensity of the quake.
   */
  public void setMaximumInstrumentedIntensity(Double maximumInstrumentedIntensity) {
    this.maximumInstrumentedIntensity = maximumInstrumentedIntensity;
  }


  /**
   * @return the alertLevel designated by PAGER.
   */
  public PagerAlertLevel getAlertLevel() {
    return alertLevel;
  }


  /**
   * @param alertLevel Assign the alert level to the quake.
   */
  public void setAlertLevel(final PagerAlertLevel alertLevel) {
    this.alertLevel = alertLevel;
  }


  /**
   * @return the reviewStatus indicating whether the report had been eye-balled by a human.
   */
  public ReviewStatus getReviewStatus() {
    return reviewStatus;
  }


  /**
   * @param reviewStatus Assign whether the report has been eye-balled by a human.
   */
  public void setReviewStatus(final ReviewStatus reviewStatus) {
    this.reviewStatus = reviewStatus;
  }


  /**
   * @return the generatingTsunami flag indicating whether the quake has started a tsunami.
   */
  public Boolean isGeneratingTsunami() {
    return generatingTsunami;
  }


  /**
   * @param generatingTsunami Assign whether the quake has started a tsunami.
   */
  public void setGeneratingTsunami(final Boolean generatingTsunami) {
    this.generatingTsunami = generatingTsunami;
  }


  /**
   * @return the significance of the quake.
   */
  public int getSignificance() {
    return significance;
  }


  /**
   * @param significance Assign the level of importance levelled at the quake event.
   */
  public void setSignificance(final int significance) {
    this.significance = significance;
  }


  /**
   * @return the contributorId of the source reporter.
   */
  public String getContributorId() {
    return contributorId;
  }


  /**
   * @param contributorId Assign the report contributor.
   */
  public void setContributorId(final String contributorId) {
    this.contributorId = contributorId;
  }


  /**
   * @return the unique code for the report.
   */
  public String getCode() {
    return code;
  }


  /**
   * @param code Assign the unique code for the report.
   */
  public void setCode(String code) {
    this.code = code;
  }


  /**
   * @return the contributors, contributing to this report.
   */
  public String[] getContributors() {
    return contributors;
  }


  /**
   * @param contributors Assign the report contributors.
   */
  public void setContributors(final String[] contributors) {
    this.contributors = contributors;
  }


  /**
   * @return the noOfStationsReportingEvent.
   */
  public int getNoOfStationsReportingEvent() {
    return noOfStationsReportingEvent;
  }


  /**
   * @param noOfStationsReportingEvent Assign the number of stations reporting the event.
   */
  public void setNoOfStationsReportingEvent(final int noOfStationsReportingEvent) {
    this.noOfStationsReportingEvent = noOfStationsReportingEvent;
  }


  /**
   * @return the minDistFromEpicentreToStation that reported the event.
   */
  public double getMinDistFromEpicentreToStation() {
    return minDistFromEpicentreToStation;
  }


  /**
   * @param minDistFromEpicentreToStation Assign the minimum distance between the quake epicentre and the
   *                                      measurement station.
   */
  public void setMinDistFromEpicentreToStation(double minDistFromEpicentreToStation) {
    this.minDistFromEpicentreToStation = minDistFromEpicentreToStation;
  }


  /**
   * @return the magnitudeType of the {@link #magnitude}.
   */
  public String getMagnitudeType() {
    return magnitudeType;
  }


  /**
   * @param magnitudeType Assign the {@link #magnitude} type.
   */
  public void setMagnitudeType(String magnitudeType) {
    this.magnitudeType = magnitudeType;
  }
}
