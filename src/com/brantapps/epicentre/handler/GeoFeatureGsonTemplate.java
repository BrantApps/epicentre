package com.brantapps.epicentre.handler;

import java.net.URL;
import java.util.List;

import com.brantapps.epicentre.model.GeoFeature;
import com.brantapps.epicentre.model.GeoJsonType;
import com.brantapps.epicentre.model.PagerAlertLevel;
import com.brantapps.epicentre.model.ReviewStatus;
import com.google.gson.annotations.SerializedName;

/**
 * Template for {@link GeoFeature}s.
 * <p>
 * <dt>Notes:</dt>
 * <dd>DYFI? system stands for the "Did you feel it?" reporting system.
 * {@link http://earthquake.usgs.gov/research/dyfi/}</dd>
 * </p>
 *
 *
 * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson_detail.php
 * @author David C Branton
 */
public class GeoFeatureGsonTemplate {
  @SerializedName("type") GeoJsonType type;
  @SerializedName("metadata") MetaData metadata;
  @SerializedName("features") List<Feature> features;


  /**
   * File metadata.
   *
   * @author David C Branton
   */
  public class MetaData {
    @SerializedName("generated") long generated;
    @SerializedName("url") URL url;
    @SerializedName("title") String title;
    @SerializedName("subTitle") String subTitle;
    @SerializedName("cacheMaxAge") int cacheMaxAge;
  }


  /**
   * A {@link GeoFeature}.
   *
   * @author David C Branton
   */
  public class Feature {
    @SuppressWarnings("hiding")
    @SerializedName("type") GeoJsonType type;
    @SerializedName("properties") Properties properties;
    @SerializedName("geometry") Geometry geometry;
    @SerializedName("ids") String ids;


    /**
     * Feature properties.
     *
     * @author David C Branton
     */
    public class Properties {

      /**
       * The magnitude for the event.
       * Typical values: [-1.0, 10.0]
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#mag
       */
      @SerializedName("mag") public double mag;


      /**
       * Textual description of named geographic region near to the event.
       * This may be a city name, or a Flinn-Engdahl Region name.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#place
       */
      @SerializedName("place") public String place;


      /**
       * Time when the event occurred. Times are reported in milliseconds since the epoch.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#time
       */
      @SerializedName("time") public long time;


      /**
       * Time when the event was most recently updated. Times are reported in milliseconds
       * since the epoch.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#udpated
       */
      @SerializedName("updated") public long updated;


      /**
       * Timezone offset from UTC in minutes at the event epicenter.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#tz
       */
      @SerializedName("tz") public int tz;


      /**
       * Link to USGS Event Page for event.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#url
       */
      @SerializedName("url") public URL url;


      /**
       * The total number of felt reports submitted to the DYFI? system.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#felt
       */
      @SerializedName("felt") public int felt;


      /**
       * The maximum reported intensity for the event. Computed by DYFI.
       * While typically reported as a roman numeral, for the purposes of this API,
       * intensity is expected as the decimal equivalent of the roman numeral.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#cdi
       * @see http://earthquake.usgs.gov/learn/topics/mag_vs_int.php
       */
      @SerializedName("cdi") public Double cdi;


      /**
       * The maximum estimated instrumental intensity for the event.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#mmi
       */
      @SerializedName("mmi") public Double mmi;


      /**
       *
       * The alert level from the PAGER earthquake impact scale
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#alert
       * @see http://earthquake.usgs.gov/research/pager/
       */
      @SerializedName("alert") public PagerAlertLevel alert;


      /**
       * Indicates whether the event has been reviewed by a human.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#status
       */
      @SerializedName("status") public ReviewStatus status;


      /**
       * A boolean flag indicating whether or not the event is currently believed
       * to possibly produce a tsunami. A value of “0” or “null” implies a negative
       * belief while any other value indicates a positive belief.
       * <p>
       * The existence or value of this flag does not indicate if a tsunami
       * actually did or will exist. This merely reflects the current
       * belief based on available scientific data. It may only
       * indicate that NOAA has issued a statement about whether or not this
       * event may trigger a tsunami.
       * </p>
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#tsunami
       */
      @SerializedName("tsunami") public Boolean tsunami;


      /**
       * A number describing how significant the event is.
       * Larger numbers indicate a more significant event.
       * This value is determined on a number of factors,
       * including: magnitude, maximum MMI, felt reports, and estimated impact.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#sig
       */
      @SerializedName("sig") public int sig;


      /**
       * The ID of a data contributor.
       * Identifies the network considered to be the preferred source of information
       * for this event.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#net
       */
      @SerializedName("net") public String net;


      /**
       * An identifying code assigned by - and unique from -
       * the corresponding source for the event.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#code
       */
      @SerializedName("code") public String code;


      /**
       * A comma-separated list of network contributors.
       *
       * @see #net
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#sources
       */
      @SerializedName("sources") public String[] sources;


      /**
       * The total number of Number of seismic stations which reported P-
       * and S-arrival times for this earthquake.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#nst
       */
      @SerializedName("nst") public int nst;


      /**
       * Horizontal distance from the epicenter to the nearest station (in degrees).
       * 1 degree is approximately 111.2 kilometers. In general,
       * the smaller this number, the more reliable is the calculated depth of the earthquake.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#dmin
       */
      @SerializedName("dmin") public double dmin;


      /**
       * The method or algorithm used to calculate the preferred magnitude for the event.
       *
       * @see http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#mb
       */
      @SerializedName("magnitudeType") public String magnitudeType; // Magnitude type.
    }


    /**
     * Embedded geometry information.
     *
     * @author David C Branton
     */
    public class Geometry {
      @SuppressWarnings("hiding")
      @SerializedName("type") GeoJsonType type;
      @SerializedName("coordinates") Double[] coordinates;
    }
  }
}
