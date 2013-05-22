package com.brantapps.epicentre.model;

/**
 * The alert level as provided by
 * the PAGER system.
 *
 * @see http://earthquake.usgs.gov/research/pager/
 *
 * @author David C Branton
 */
public enum PagerAlertLevel {
  GREEN("green"),
  YELLOW("yellow"),
  ORANGE("orange"),
  RED("red"),
  UNRECOGNISED("");

  private String level;


  PagerAlertLevel(final String level) {
    this.level = level;
  }


  /**
   * @return the level of the PAGER alert.
   */
  public String getLevel() {
    return level;
  }


  /**
   * Perform a safe comparison of alert level strings
   * with case insensitivity and a fallback level
   * should a new {@link PagerAlertLevel} be introduced.
   *
   * @param alertCode The PAGER alert level string to encode.
   * @return  the PAGER alert level or {@link #UNRECOGNISED} when no match is found.
   */
  public static PagerAlertLevel safeValueOf(final String alertCode) {
    PagerAlertLevel alertLevel = null;
    if (alertCode != null) {
      for (PagerAlertLevel alertLevelToCompare : values()) {
        if (alertLevelToCompare.getLevel().toLowerCase().equals(alertCode.toLowerCase())) {
          alertLevel = alertLevelToCompare;
        }
      }
    }
    if (alertLevel == null) {
      alertLevel = UNRECOGNISED;
    }

    return alertLevel;
  }
}
