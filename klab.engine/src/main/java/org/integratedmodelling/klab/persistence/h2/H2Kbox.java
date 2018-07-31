/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.persistence.h2;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabStorageException;

/**
 * Poor-man hybernate with Thinklab-specialized query language, aware of semantics and time/space,
 * using assumptions and conventions to allow IKbox clean model with little or no configuration.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class H2Kbox {

  public static final String             FIELD_PKEY    = "OID";
  public static final String             FIELD_FKEY    = "FID";

  protected H2Database                   database;
  protected Map<Class<?>, Serializer<?>> serializers   = new HashMap<>();
  protected Map<Class<?>, Deserializer>  deserializers = new HashMap<>();
//  protected IMonitor                     monitor;

//  private static Map<String, H2Kbox>     kboxes        = new HashMap<>();

//  protected static H2Kbox set(String name, H2Kbox kbox) {
//    kboxes.put(name, kbox);
//    return kbox;
//  }
//
//  protected static H2Kbox get(String name) {
//    if (kboxes.containsKey(name)) {
//      return kboxes.get(name);
//    }
//    return null;
//  };

  public H2Kbox(String name) {
    database = H2Database.get(name);
//    this.monitor = monitor;
  }

//  public H2Kbox(String name, File directory) {
//    database = H2Database.get(directory, name);
////    this.monitor = monitor;
//  }

  public <T> List<T> query(String query, Class<T> cls, IMonitor monitor) throws KlabException {
    return querySql(query, cls, monitor);
  }

//  /**
//   * Call the H2 recover tool.
//   * 
//   * @throws KlabException
//   */
//  public void recover() throws KlabException {
//    database.recover();
//  }

  public <T> List<T> querySql(String query, Class<T> cls, IMonitor monitor) throws KlabException {

    Deserializer deserializer = getDeserializer(cls);

    final List<T> ret =
        deserializer instanceof DeferredDeserializer ? new H2Result<T>(this, monitor)
            : new ArrayList<>();

    database.query(query, new SQL.ResultHandler() {

      @Override
      public void onRow(ResultSet rs) {
        if (deserializer instanceof DeferredDeserializer) {
          try {
            ((DeferredDeserializer<T>) deserializer).addId(rs.getLong(FIELD_PKEY));
          } catch (SQLException e) {
            throw new KlabStorageException(e);
          }
        } else {
          ret.add(((DirectDeserializer<T>) deserializer).deserialize(rs));
        }
      }

      @Override
      public void nResults(int nres) {}
    });

    return ret;
  }

  @SuppressWarnings("unchecked")
  private <T> Deserializer getDeserializer(Class<? extends T> cls) {
    Deserializer ret = null;
    for (Class<?> cl : deserializers.keySet()) {
      if (cl.isAssignableFrom(cls)) {
        ret = (Deserializer) deserializers.get(cl);
      }
    }
    if (ret == null) {
      throw new KlabStorageException("kbox: no deserializer for class " + cls.getCanonicalName());
    }
    return ret;
  }

  public long store(Object o, IMonitor monitor) throws KlabException {
    return database.storeObject(o, 0l, getSerializer(o.getClass()), monitor);
  }

  @SuppressWarnings("unchecked")
  private <T> Serializer<T> getSerializer(Class<? extends T> cls) {
    Serializer<T> ret = null;
    for (Class<?> cl : serializers.keySet()) {
      if (cl.isAssignableFrom(cls)) {
        ret = (Serializer<T>) serializers.get(cl);
      }
    }
    if (ret == null) {
      throw new KlabStorageException("kbox: no serializer for class " + cls.getCanonicalName());
    }
    return ret;
  }

  public <T> T retrieve(long id, Class<T> cls) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  public void remove(long id) throws KlabException {
    // TODO Auto-generated method stub

  }

  public void remove(String query) throws KlabException {
    // TODO Auto-generated method stub

  }

  public void clear() throws KlabException {
    // TODO Auto-generated method stub

  }

  protected <T> void setSerializer(Class<? extends T> cls, Serializer<T> serializer) {
    this.serializers.put(cls, serializer);
  }

  protected void setDeserializer(Class<?> cls, Deserializer deserializer) {
    this.deserializers.put(cls, deserializer);
  }

  protected void setSchema(Class<?> cls, Schema schema) {
    database.setSchema(cls, schema);
  }

  // TODO these interfaces should go in implementation.

  /**
   * Turns an object into SQL instructions to store it.
   * 
   * @author ferdinando.villa
   *
   */
  public static interface Serializer<T> {

    /**
     * 
     * @param o
     * @param primaryKey
     * @param foreignKey
     * @return SQL instructions to serialize object
     */
    String serialize(T o, long primaryKey, long foreignKey);
  }

  /**
   * Turns a SQL result into an object. Only a tag interface: the actual working API is in the two
   * derived ones.
   * 
   * @author ferdinando.villa
   *
   */
  public abstract interface Deserializer {

  }

  /**
   * Builds the object directly in the query result list. Use when building the objects is fast and
   * cheap.
   * 
   * @author ferdinando.villa
   * @param <T>
   *
   */
  public interface DirectDeserializer<T> extends Deserializer {

    /**
     * Passed a result set localized on a valid match. Just get your things from it and return - its
     * lifetime is controlled outside. Should return null in error.
     * 
     * @param rs
     * @return deserialized object
     */
    T deserialize(ResultSet rs);

  }

  /**
   * Turns a database unique ID into an object. Use when building objects is complex, requiring more
   * than one query, or when performance is an issue when creating objects. In this case, the query
   * strategy will return the OID field for all matches to it, and the serializer will have to
   * behave like a list that lazily extracts the objects when get() is called.
   * 
   * @author ferdinando.villa
   * @param <T>
   *
   */
  public interface DeferredDeserializer<T> extends Deserializer, List<T> {
    void addId(long id);
  }

  public static interface Schema {

    /**
     * Return non-null if this schema creates a whole table.
     * 
     * @return SQL to create schema
     */
    String getCreateSQL();

    /**
     * Return the name of the primary table created by this schema, if any, or null. Used to check
     * for the need of creating the corresponding tableset.
     * 
     * @return the primary table name or null
     */
    String getTableName();

  }

  protected int deleteAllObjectsWithNamespace(String namespaceId, IMonitor monitor)
      throws KlabException {
    // TODO Auto-generated method stub
    return 0;
  }

}
