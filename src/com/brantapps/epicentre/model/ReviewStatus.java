package com.brantapps.epicentre.model;

/**
 * The report review status.
 *
 * @author David C Branton
 */
public enum ReviewStatus {
  AUTOMATIC("AUTOMATIC"),
  PUBLISHED("PUBLISHED"),
  REVIEWED("REVIEWED");

  private String status;


  ReviewStatus(final String status) {
    this.status = status;
  }


  /**
   * @return the review status.
   */
  public String getLevel() {
    return status;
  }
}
