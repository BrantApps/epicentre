package com.brantapps.epicentre.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import android.util.Log;

import com.brantapps.epicentre.handler.GeoFeatureGsonTemplate.Feature;
import com.brantapps.epicentre.model.GeoFeature;
import com.brantapps.epicentre.model.GeoFeatureCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

/**
 * USGS earthquake event (a 'GeoFeature')
 * parser.
 *
 * @author David C Branton
 */
public class GeoFeatureParser {


  /**
   * Parse the GeoJSON response from USGS.
   *
   * @param stream  The input stream.
   * @param featureCollection The {@link GeoFeature} list to populate.
   * @throws IOException  When the stream cannot be read.
   */
  public void parseResponse(final InputStream stream, final GeoFeatureCollection featureCollection) throws IOException {
    final JsonReader reader = new JsonReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
    final Gson gsonBuilder = new GsonBuilder()
      .registerTypeAdapter(URL.class, new UrlDeserializer())
      .registerTypeAdapter(String[].class, new ContributorsDeserializer())
      .create();
    final GeoFeatureGsonTemplate message = gsonBuilder.fromJson(reader, GeoFeatureGsonTemplate.class);
    reader.close();

    featureCollection.setGenerated(new LocalDate(message.metadata.generated));
    featureCollection.setTitle(message.metadata.title);
    featureCollection.setSubTitle(message.metadata.subTitle);
    featureCollection.setUrl(message.metadata.url);
    featureCollection.setCacheMaxAge(Period.hours(message.metadata.cacheMaxAge/100));

    for (Feature feature : message.features) {
      final GeoFeature geoFeature =
          new GeoFeature(feature.geometry.type,
                         feature.geometry.coordinates[0],
                         feature.geometry.coordinates[1]);
      geoFeature.setDepth(feature.geometry.coordinates[2]);
      geoFeature.setMagnitude(feature.properties.mag);
      geoFeature.setLocation(feature.properties.place);
      final DateTimeZone timezone = DateTimeZone.forOffsetHours(feature.properties.tz/60);
      geoFeature.setTime(new LocalDate(feature.properties.time, timezone));
      geoFeature.setUpdatedTime(new LocalDate(feature.properties.updated, timezone));
      geoFeature.setEventPageUrl(feature.properties.url);
      geoFeature.setNoOfEyeWitnessReports(feature.properties.felt);
      geoFeature.setMaximumReportedIntensity(feature.properties.cdi);
      geoFeature.setMaximumInstrumentedIntensity(feature.properties.mmi);
      geoFeature.setReviewStatus(feature.properties.status);
      geoFeature.setGeneratingTsunami(feature.properties.tsunami);
      geoFeature.setSignificance(feature.properties.sig);
      geoFeature.setContributorId(feature.properties.net);
      geoFeature.setContributors(feature.properties.sources);
      geoFeature.setNoOfStationsReportingEvent(feature.properties.nst);
      geoFeature.setMinDistFromEpicentreToStation(feature.properties.dmin);
      geoFeature.setMagnitudeType(feature.properties.magnitudeType);
      geoFeature.setCode(feature.properties.code);
      featureCollection.add(geoFeature);
    }
  }

  private class UrlDeserializer implements JsonDeserializer<URL> {

    /**
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public URL deserialize(final JsonElement json,
                           final Type typeOfT,
                           final JsonDeserializationContext context) throws JsonParseException {
      try {
        return new URL(json.getAsString());
      } catch (MalformedURLException e) {
        Log.e(this.getClass().getSimpleName(), String.format("Malformed URL with spec, [%s]", json.getAsString()));
      }
      return null;
    }
  }

  private class ContributorsDeserializer implements JsonDeserializer<String[]> {

    /**
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public String[] deserialize(final JsonElement json,
                           final Type typeOfT,
                           final JsonDeserializationContext context) throws JsonParseException {
      return StringUtils.split(json.getAsString(), ",");
    }
  }
}
