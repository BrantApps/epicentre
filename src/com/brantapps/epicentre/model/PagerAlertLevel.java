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
  RED("red");

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
}
