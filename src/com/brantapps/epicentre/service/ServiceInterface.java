package com.brantapps.epicentre.service;

import java.util.List;

/**
 * This interface defines database service level
 * APIs.
 *
 * @author David C Branton
 */
public interface ServiceInterface<T> {
  
  /**
   * Load from the database a single record.
   * 
   * @param index Database identifier
   * @param preferences Preference information used during the load
   * @return T The java model representation.
   */
  T load(final long index, final Double...preferences);
  
  
  /**
   * Retrieve all results matching a query.
   * 
   * @param longs Any database identifier information.
   * @return  A list of T.
   */
  List<T> list(final Long...longs);
  
  
  /**
   * Persist the provide model to the database.
   * <p> 
   * Care has to be taken when saving hierarchies of java models since all of
   * the related 'child' objects need to also be save within the same
   * transaction.
   * </p>
   * @param persistedModel The parent model to save. 
   * @param longs Database identifier information.
   * @return the row id of the parent entity just entered into the database.
   */
  long save(final T persistedModel, final Long...longs);
  
  
  /**
   * Remove a previously persisted model from the database.
   * <p>
   * Care has to be taken when deleting top level models since all of
   * the related 'child' object need to also be deleted within the same
   * transaction.
   * </p>
   * @param longs Database identifier for the item to delete.
   */
  void delete(final Long...longs);
  
}
