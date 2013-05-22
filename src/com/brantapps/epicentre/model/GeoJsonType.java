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
  POINT("Point"),
  UNRECOGNISED("");

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


  /**
   * Perform a safe comparison of type strings
   * with case insensitivity and a fallback status
   * should a new {@link GeoJsonType} be introduced.
   *
   * @param type The type string to encode.
   * @return  the {@link GeoJsonType} or {@link #UNRECOGNISED} when no match is found.
   */
  public static GeoJsonType safeValueOf(final String type) {
    GeoJsonType geoJsonType = null;
    if (type != null) {
      for (GeoJsonType geoJsonTypeToCompare : values()) {
        if (geoJsonTypeToCompare.getType().toLowerCase().equals(type.toLowerCase())) {
          geoJsonType = geoJsonTypeToCompare;
        }
      }
    }
    if (geoJsonType == null) {
      geoJsonType = UNRECOGNISED;
    }

    return geoJsonType;
  }
}
