package org.integratedmodelling.klab.components.geospace.extents;

import org.geotools.referencing.CRS;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class Projection implements IProjection {

  private String code;
  CoordinateReferenceSystem crs;
  
  private Projection(String code) {
    this.code = code;
    try {
      this.crs = CRS.decode(this.code, true);
    } catch (FactoryException e) {
      throw new KlabRuntimeException(e);
    }
  }

  @Override
  public String getCode() {
    return code;
  }

  /**
   * Obtain the projection corresponding to the passed EPSG (or other supported authority) code, in
   * the format "EPSG:nnnn".
   * 
   * @param code
   * @return the projection, which may be invalid.
   */
  public static Projection create(String code) {
    return new Projection(code);
  }

  public static Projection create(String authority, int code) {
    return new Projection(authority + ":" + code);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Projection other = (Projection) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    return true;
  }

  public CoordinateReferenceSystem getCoordinateReferenceSystem() {
    return crs;
  }


}
