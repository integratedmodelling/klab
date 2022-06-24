package org.integratedmodelling.geoprocessing.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.scale.Scale;

@UrnAdapter(type = GridAdapter.NAME, version = Version.CURRENT)
public class GridAdapter implements IUrnAdapter {

    public static final String NAME = "grid";

    public static final String NAMESPACE_TILES = "tiles";
    public static final String NAMESPACE_ROWS = "rows";
    public static final String NAMESPACE_COLS = "columns";
    public static final String NAMESPACE_INDEX = "index";
    
    public GridAdapter() {
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isOnline(Urn urn) {
        return true;
    }

    @Override
    public void encodeData(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
        switch (urn.getNamespace()) {
        case NAMESPACE_TILES:
            makeTiles(urn, builder, geometry, context);
            break;
        }
        // return builder.build();
    }

    private void makeTiles(Urn urn, Builder builder, IGeometry geometry, IContextualizationScope context) {
//
//      // TODO use parameters
//      int vertices = urn.getParameters().containsKey(VERTICES) ? Integer.parseInt(urn.getParameters().get(VERTICES))
//              : 5;
//      int xdivs = urn.getParameters().containsKey(XDIVS) ? Integer.parseInt(urn.getParameters().get(XDIVS)) : 10;
//      int ydivs = urn.getParameters().containsKey(YDIVS) ? Integer.parseInt(urn.getParameters().get(YDIVS)) : 10;
//      double probability = urn.getParameters().containsKey(FRACTION)
//              ? Double.parseDouble(urn.getParameters().get(FRACTION))
//              : 0.5;
//      String artifactName = urn.getResourceId().substring(0, urn.getResourceId().length() - 1);
//
//      IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
//
//      if (scale.getSpace() != null) {
//
//          IEnvelope envelope = scale.getSpace().getEnvelope();
//
//          Collection<IShape> shapes = null;
//          switch (urn.getResourceId()) {
//          case POINTS:
//              shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, 1);
//              break;
//          case LINES:
//              shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, 2);
//              break;
//          case POLYGONS:
//              shapes = RandomShapes.INSTANCE.create(envelope, xdivs, ydivs, probability, vertices);
//              break;
//          }
//
//          int n = 1;
//          for (IShape shape : shapes) {
//
//              /*
//               * TODO honor any filters on the shapes - area, width, length, whatever
//               */
//
//              Builder obuilder = builder.startObject(context.getTargetName(), artifactName + "_" + (n++),
//                      makeScale(urn, shape, context));
//
//              /*
//               * If states are requested, add them to the returned builder. Could be encoded
//               * in any parameters not understood, e.g. "precipitation=normal(2.3, 1.2)" or
//               * "temperature=12.3". Of course these would need to be returned in the
//               * attributes for the resource too.
//               */
//              for (String attribute : urn.getParameters().keySet()) {
//                  if (Arrays.binarySearch(object_attribute_ids, attribute) < 0) {
//                      Object value = getAttributeValue(urn.getParameters().get(attribute));
//                      if (value != null) {
//                          obuilder.withMetadata(attribute, value);
//                      }
//                  }
//              }
//              obuilder.finishObject();
//          }
//      }

    }

//  private synchronized Object getAttributeValue(String string) {
//      if (NumberUtils.encodesDouble(string) || NumberUtils.encodesLong(string)) {
//          return Double.parseDouble(string);
//      }
//      String[] tokens = Utils.parseAsFunctionCall(string);
//      if (tokens != null && Arrays.binarySearch(distribution_ids, tokens[0]) >= 0) {
//          return sampleDistribution(tokens);
//      }
//
//      return null;
//  }

    private IGeometry makeScale(Urn urn, IShape shape, IContextualizationScope scope) {

        List<IExtent> extents = new ArrayList<>();
//      if (EVENTS.equals(urn.getNamespace())) {
//
//          /*
//           * TODO tie to parameters
//           */
//          String[] tspecs = new String[3];
//          // this is the time of the extent of computation
//          ITime time = scope.getScale().getTime();
//
//          long start = time.getStart().getMilliseconds();
//          long duration = time.getEnd().getMilliseconds() - start;
//
//          if (urn.getParameters().containsKey(START)) {
//              // same - use ref = 0-1 and refer to length of timestep in ms
//              Object d = getAttributeValue(START);
//              if (d instanceof Number && ((Number) d).doubleValue() > 0) {
//                  if (((Number) d).doubleValue() >= duration) {
//                      d = duration;
//                  }
//                  start += (long) ((double) duration * ((Number) d).doubleValue());
//              }
//          }
//
//          if (urn.getParameters().containsKey(DURATION)) {
//              // expected to produce a number of intervals
//              Object d = getAttributeValue(DURATION);
//              if (d instanceof Number && ((Number) d).doubleValue() > 0) {
//                  duration = (long) ((double) duration * ((Number) d).doubleValue());
//              }
//          }
//
//          extents.add(Time.create(start, start + duration));
//
//      } else {
//          // full extent for a continuant
//          extents.add(scope.getContextSubject().getScale().getTime());
//      }
//
//      // TODO if a grid is requested, modify the shape
//      if (urn.getParameters().containsKey(GRID)) {
//          // TODO
//      }
//
//      extents.add(shape);

        return Scale.create(extents).asGeometry();
    }

//  private void makeData(Urn urn, Builder stateBuilder, IGeometry geometry, IContextualizationScope scope) {
//
////        Builder stateBuilder = builder.startState(scope.getTargetName() == null ? "result" : scope.getTargetName(),
////                null, scope);
//
//      Object distribution = null;
//      if (!"year".equals(urn.getResourceId())) {
//          distribution = getDistribution(urn);
//      }
//      double year = scope.getScale().getTime() == null ? 0 : scope.getScale().getTime().getStart().getYear();
//      for (ILocator locator : geometry) {
//          if ("year".equals(urn.getResourceId())) {
//              stateBuilder.add(year);
//          } else if (distribution instanceof RealDistribution) {
//              stateBuilder.add(((RealDistribution) distribution).sample());
//          } else if (distribution instanceof IntegerDistribution) {
//              stateBuilder.add(((IntegerDistribution) distribution).sample());
//          }
//      }
////        stateBuilder.finishState();
//  }

    @Override
    public Type getType(Urn urn) {
        switch (urn.getNamespace()) {
        case NAMESPACE_TILES:
            return Type.OBJECT;
        }
        throw new KlabUnimplementedException("random: can't handle URN " + urn);
    }

    @Override
    public IGeometry getGeometry(Urn urn) {
        switch (urn.getNamespace()) {
        case NAMESPACE_TILES:
            // TODO use parameters
            return Geometry.create("#S2");
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Random data and objects for testing and stochastic simulations.";
    }

    @Override
    public Collection<String> getResourceUrns() {
        List<String> ret = new ArrayList<>();
        // TODO
        return ret;
    }

    @Override
    public IResource getResource(String urn) {

        Urn kurn = new Urn(urn);
        ResourceReference ref = new ResourceReference();
        ref.setUrn(urn.toString());
        ref.setAdapterType(getName());
        ref.setLocalName(kurn.getResourceId());

        switch (kurn.getNamespace()) {
        case NAMESPACE_TILES:
            // TODO use parameters
            ref.setGeometry("#S2");
            break;
        }

        /**
         * any parameters not understood become attributes
         */
//      if (!DATA.equals(kurn.getNamespace())) {
//          for (String attribute : kurn.getParameters().keySet()) {
//              if (Arrays.binarySearch(object_attribute_ids, attribute) < 0) {
//                  Object value = getAttributeValue(kurn.getParameters().get(attribute));
//                  if (value != null) {
//                      Type type = Utils.getArtifactType(value.getClass());
//                      AttributeReference aref = new AttributeReference();
//                      aref.setName(attribute);
//                      aref.setExampleValue(value.toString());
//                      aref.setType(type);
//                      ref.getAttributes().add(aref);
//                  }
//              }
//          }
//      }

        ref.setVersion(Version.CURRENT);
        ref.setType(getType(kurn));
        return new Resource(ref);
    }

    @Override
    public IResource contextualize(IResource resource, IGeometry scale, IGeometry overallScale, IObservable semantics) {
        return resource;
    }

}

