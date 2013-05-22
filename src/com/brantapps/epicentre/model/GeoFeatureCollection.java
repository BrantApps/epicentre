package com.brantapps.epicentre.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * A collection of geographic features.
 *
 * @author David C Branton
 */
public class GeoFeatureCollection implements List<GeoFeature> {
  private long identifier;
  private final List<GeoFeature> geoFeatures;
  private LocalDateTime generated;
  private URL url;
  private String title;
  private String subTitle;
  private Period cacheMaxAge;

  /**
   * Constructs a the collection of geographic
   * features.
   */
  public GeoFeatureCollection() {
    geoFeatures = new ArrayList<GeoFeature>();
  }


  /**
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(final int location, final GeoFeature object) {
    geoFeatures.add(location, object);
  }


  /**
   * @see java.util.List#add(java.lang.Object)
   */
  @Override
  public boolean add(GeoFeature object) {
    return geoFeatures.add(object);
  }


  /**
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int location, Collection<? extends GeoFeature> collection) {
    return geoFeatures.addAll(location, collection);
  }


  /**
   * @see java.util.List#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends GeoFeature> collection) {
    return geoFeatures.addAll(collection);
  }


  /**
   * @see java.util.List#clear()
   */
  @Override
  public void clear() {
    geoFeatures.clear();
  }


  /**
   * @see java.util.List#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object object) {
    return geoFeatures.contains(object);
  }


  /**
   * @see java.util.List#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection<?> collection) {
    return geoFeatures.containsAll(collection);
  }


  /**
   * @see java.util.List#get(int)
   */
  @Override
  public GeoFeature get(int location) {
    return geoFeatures.get(location);
  }


  /**
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object object) {
    return geoFeatures.indexOf(object);
  }


  /**
   * @see java.util.List#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return geoFeatures.isEmpty();
  }


  /**
   * @see java.util.List#iterator()
   */
  @Override
  public Iterator<GeoFeature> iterator() {
    return geoFeatures.iterator();
  }


  /**
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  @Override
  public int lastIndexOf(Object object) {
    return geoFeatures.lastIndexOf(object);
  }


  /**
   * @see java.util.List#listIterator()
   */
  @Override
  public ListIterator<GeoFeature> listIterator() {
    return geoFeatures.listIterator();
  }


  /**
   * @see java.util.List#listIterator(int)
   */
  @Override
  public ListIterator<GeoFeature> listIterator(int location) {
    return geoFeatures.listIterator(location);
  }


  /**
   * @see java.util.List#remove(int)
   */
  @Override
  public GeoFeature remove(int location) {
    return geoFeatures.remove(location);
  }


  /**
   * @see java.util.List#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object object) {
    return geoFeatures.remove(object);
  }


  /**
   * @see java.util.List#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> collection) {
    return geoFeatures.removeAll(collection);
  }


  /**
   * @see java.util.List#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> collection) {
    return geoFeatures.retainAll(collection);
  }


  /**
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public GeoFeature set(int location, GeoFeature object) {
    return geoFeatures.set(location, object);
  }


  /**
   * @see java.util.List#size()
   */
  @Override
  public int size() {
    return geoFeatures.size();
  }


  /**
   * @see java.util.List#subList(int, int)
   */
  @Override
  public List<GeoFeature> subList(int start, int end) {
    return geoFeatures.subList(start, end);
  }


  /**
   * @see java.util.List#toArray()
   */
  @Override
  public Object[] toArray() {
    return geoFeatures.toArray();
  }


  /**
   * @see java.util.List#toArray(T[])
   */
  @Override
  public <T> T[] toArray(T[] array) {
    return geoFeatures.toArray(array);
  }


  /**
   * @return the date the feature collection report was assembled.
   */
  public LocalDateTime getGenerated() {
    return generated;
  }


  /**
   * @param Assign the date the feature collection report was assembled.
   */
  public void setGenerated(final LocalDateTime generated) {
    this.generated = generated;
  }


  /**
   * @return the url of the feed used to compile this feature collection
   *         report.
   */
  public URL getUrl() {
    return url;
  }


  /**
   * @param url Assign the URL of where the
   *            feature collection report is generated from.
   */
  public void setUrl(final URL url) {
    this.url = url;
  }


  /**
   * @return the title of the feature collection report.
   */
  public String getTitle() {
    return title;
  }


  /**
   * @param Assign the title of the feature collection report.
   */
  public void setTitle(final String title) {
    this.title = title;
  }


  /**
   * @return the subTitle of the feature collection report.
   */
  public String getSubTitle() {
    return subTitle;
  }


  /**
   * @param Assign the subtitle of the feature collection report.
   */
  public void setSubTitle(final String subTitle) {
    this.subTitle = subTitle;
  }


  /**
   * @return the maximum time this collection can exist.
   */
  public Period getCacheMaxAge() {
    return cacheMaxAge;
  }


  /**
   * @param Assign the cache maximum time.
   */
  public void setCacheMaxAge(final Period cacheMaxAge) {
    this.cacheMaxAge = cacheMaxAge;
  }


  /**
   * @return Retrieve the database identifier.
   */
  public long getIdentifier() {
    return identifier;
  }


  /**
   * @param identifier Assign the database identifier.
   */
  public void setIdentifier(final long identifier) {
    this.identifier = identifier;
  }
}
