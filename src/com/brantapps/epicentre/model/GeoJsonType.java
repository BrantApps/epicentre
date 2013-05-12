package com.brantapps.epicentre.model;

/**
 * The data types associated with
 * the GeoJSON format.
 *
 * @see http://www.geojson.org/
 *
 * @author David C Branton
 */
public enum GeoJsonType {
  FEATURE_COLLECTION("GeoFeatureCollection"),
  FEATURE("Feature"),
  POINT("Point");

  private String type;


  /**
   * Construct the enum.
   *
   * @param type  The GeoJson type code.
   */
  GeoJsonType(final String type) {
    this.type = type;
  }


  /**
   * @return the {@link GeoJsonType} code.
   */
  public String getType() {
    return type;
  }
}
