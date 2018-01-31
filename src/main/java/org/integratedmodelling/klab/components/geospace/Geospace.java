package org.integratedmodelling.klab.components.geospace;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import com.vividsolutions.jts.geom.GeometryFactory;

@Component(id = "geospace", version = Version.CURRENT)
public class Geospace {

  public static final String    DEFAULT_PROJECTION_CODE = "EPSG:4326";
  public static GeometryFactory gFactory                = new GeometryFactory();

  public Geospace() {
    // TODO Auto-generated constructor stub
  }

  @Initialize
  public void initialize() {
    // TODO create the desired geometry factory
    // TODO set up defaults for projections etc.
  }

}
