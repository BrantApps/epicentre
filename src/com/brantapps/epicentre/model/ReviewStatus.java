package com.brantapps.epicentre.model;

/**
 * The report review status.
 *
 * @author David C Branton
 */
public enum ReviewStatus {
  AUTOMATIC("AUTOMATIC"),
  PUBLISHED("PUBLISHED"),
  REVIEWED("REVIEWED"),
  UNRECOGNISED("");

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


  /**
   * Perform a safe comparison of status strings
   * with case insensitivity and a fallback status
   * should a new {@link ReviewStatus} be introduced.
   *
   * @param status The review status string to encode.
   * @return  the review status or {@link #UNRECOGNISED} when no match is found.
   */
  public static ReviewStatus safeValueOf(final String status) {
    ReviewStatus reviewStatus = null;
    if (status != null) {
      for (ReviewStatus reviewStatusToCompare : values()) {
        if (reviewStatusToCompare.getLevel().toLowerCase().equals(status.toLowerCase())) {
          reviewStatus = reviewStatusToCompare;
        }
      }
    }
    if (reviewStatus == null) {
      reviewStatus = UNRECOGNISED;
    }

    return reviewStatus;
  }
}
