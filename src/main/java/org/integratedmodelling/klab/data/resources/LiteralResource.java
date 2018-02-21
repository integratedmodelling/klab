package org.integratedmodelling.klab.data.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.kim.model.KimFunctionCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Geometry;

public class LiteralResource extends AbstractResource {

  /**
   * A literal function produces a literal and takes no arguments. It is used to simplify the
   * encoding in dataflows, and its KDL code equivalent is the literal itself.
   * 
   * @author ferdinando.villa
   *
   */
  public static class LiteralFunction extends KimFunctionCall {

    public final static String LITERAL_FUNCTION_ID = "klab.internal.literal";

    Object                     value;

    public LiteralFunction(Object value) {
      super(null);
      this.value = null;
      setName(LITERAL_FUNCTION_ID);
    }

    private static final long serialVersionUID = -5190145577904822153L;

    @Override
    public String getSourceCode() {
      return value == null ? "unknown" : value.toString();
    }

  }

  Object              value;
  IGeometry           geometry   = Geometry.create("*");
  Map<String, Object> parameters = new HashMap<>();

  public LiteralResource(Object value) {
    super(value.toString());
    this.value = value;
  }

  private static final long serialVersionUID = 7943409920767158427L;

  @Override
  public Optional<IResourceAdapter> getAdapter() {
    // TODO use a special adapter ID? Or just return a trivial adapter, given it must exist?
    return null;
  }

  @Override
  public IRawObject get(IScale scale, IMonitor monitor) {
    // TODO return the scalar value
    return null;
  }

  @Override
  public IGeometry getGeometry() {
    return geometry;
  }

  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }

  @Override
  public IKimFunctionCall getComputation() {
    return new LiteralFunction(value);
  }

}
