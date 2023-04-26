package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IContextualizable.InteractiveParameter;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IInteractionService;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.rest.UserInputRequest;
import org.integratedmodelling.klab.rest.UserInputResponse;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.TypeUtils;
import org.integratedmodelling.klab.utils.Utils;

public enum Interaction implements IInteractionService {

	INSTANCE;

	/**
	 * Describe the passed parameter for the passed service call.
	 * 
	 * @param call
	 * @param parameter
	 * @return the interactive parameter descriptor.
	 */
	public InteractiveParameter getParameterDescriptor(String id, IServiceCall call, String parameter) {
		InteractiveParameter p = null;
		IPrototype prototype = Extensions.INSTANCE.getPrototype(call.getName());
		if (prototype != null) {
			p = new InteractiveParameter();
			p.setFunctionId(id + "/" + prototype.getName());
			p.setId(parameter);
			p.setDescription(prototype.getArgument(parameter).getDescription());
			p.setType(prototype.getArgument(parameter).getType());
			p.setSectionTitle(prototype.getLabel());
			p.setSectionDescription(prototype.getDescription());
			if (call.getParameters().contains(parameter)) {
				p.setInitialValue(call.getParameters().get(parameter).toString());
			}
		}
		return p;
	}

	private InteractiveParameter getParameterDescriptor(String id, IAnnotation annotation, IModel model) {
		InteractiveParameter p = new InteractiveParameter();
		p.setFunctionId(id + "/EXTERNAL");
		p.setId(annotation.get("name", String.class));
		p.setLabel(annotation.get("label", annotation.get("name", String.class)));
		p.setDescription(annotation.get("description", String.class));
		p.setSectionTitle(annotation.get("sectiontitle", String.class));
		p.setSectionDescription(
				annotation.get("sectiondescription", model.getMetadata().get(IMetadata.DC_COMMENT, String.class)));
		p.setType(Utils.getArtifactType(annotation.get("default", Object.class).getClass()));
		p.setInitialValue(annotation.get("default", Object.class).toString());
		return p;
	}

	private InteractiveParameter getParameterDescriptor(IAnnotation annotation, IObservable observable,
			String parameter) {
		InteractiveParameter p = null;
		IPrototype prototype = Annotations.INSTANCE.getPrototype(annotation.getName());
		if (prototype != null) {
			p = new InteractiveParameter();
			p.setFunctionId(((Annotation) annotation).getId() + "/" + prototype.getName());
			p.setId(parameter);
			p.setDescription(prototype.getArgument(parameter).getDescription());
			p.setLabel(prototype.getArgument(parameter).getLabel() + " for "
					+ StringUtils.capitalize(observable.getName().replaceAll("_", " ")));
			p.setType(prototype.getArgument(parameter).getType());

			p.setSectionTitle(prototype.getLabel());
			p.setSectionDescription(prototype.getDescription());

			if (annotation.contains(parameter)) {
				p.setInitialValue(annotation.get(parameter) == null ? "unknown" : annotation.get(parameter).toString());
				if (p.getType() == Type.VALUE) {
					p.setType(Utils.getArtifactType(Utils.getPODClass(annotation.get(parameter))));
				}
			}
		} else {
			// TODO for now just complain
			throw new KlabUnimplementedException(
					"Sorry, for now only known annotations can have interactive parameters.");
		}
		return p;
	}

	@Override
	public Collection<InteractiveParameter> getInteractiveParameters(IContextualizable computable, IModel model) {
		List<InteractiveParameter> ret = new ArrayList<>();
		if (computable.getServiceCall() != null) {
			for (String id : computable.getServiceCall().getInteractiveParameters()) {
				InteractiveParameter descriptor = getParameterDescriptor(((ComputableResource) computable).getId(),
						computable.getServiceCall(), id);
				if (descriptor != null) {
					ret.add(descriptor);
				}
			}
		}
		if (((ComputableResource) computable).getExternalParameters() != null) {
			for (IAnnotation parameter : ((ComputableResource) computable).getExternalParameters()) {
				ret.add(getParameterDescriptor(((ComputableResource) computable).getId(), parameter, model));
			}
		}
		return ret;
	}

	public Collection<InteractiveParameter> getInteractiveParameters(IAnnotation annotation, IObservable observable) {
		List<InteractiveParameter> ret = new ArrayList<>();
		for (String id : ((Annotation) annotation).getInteractiveParameters()) {
			InteractiveParameter descriptor = getParameterDescriptor(annotation, observable, id);
			if (descriptor != null) {
				ret.add(descriptor);
			}
		}
		return ret;
	}

	/**
	 * Submit the passed parameters, wait for a user's response which will directly
	 * modify the the passed resources. Returns the annotation parameters, which
	 * cannot be directly set into resources, as triples (annotationId, parameterId,
	 * value.toString).
	 * 
	 * This one <b>may return null</b> if the user has chosen to cancel the
	 * computation. If instead the choice is to proceed with default, it will either
	 * return an empty collection or all the field with their default values.
	 * 
	 * @param resources
	 * @param fields
	 * @return a collection of parameters, modified or their defaults. If null is
	 *         returned, the user has asked to cancel the run.
	 */
	public Collection<Triple<String, String, String>> submitParameters(
			List<Pair<IContextualizable, List<String>>> resources, List<InteractiveParameter> fields,
			ISession session) {

		UserInputRequest request = new UserInputRequest();
		List<Triple<String, String, String>> ret = new ArrayList<>();

		/*
		 * Send the session ID as request ID so that multiple requests can be compounded
		 * at the client side for a single response.
		 */
		request.setRequestId(session.getId());
		request.setSectionTitle("Inspect and edit the dataflow user parameters before running");

		try {
			request.setDescription(
					"The following parameters admit user input in interactive mode. Please inspect the default values and change them as required. "
							+ " Press Submit Values to continue with the modified values, Use Defaults to proceed with the default values, or"
							+ " Cancel Run to stop the computation.");

			request.getFields().addAll(fields);
			IMessage resp = session.getMonitor()
					.ask(IMessage.MessageClass.UserInterface, IMessage.Type.UserInputRequested, request).get();
			Object payload = resp.getPayload(Object.class);
			if (payload instanceof Map) {
				UserInputResponse response = JsonUtils.convertMap((Map<?, ?>) payload, UserInputResponse.class);

				if (response.isCancelRun()) {
					return null;
				}

				for (String value : response.getValues().keySet()) {
					String keys[] = value.split("/");
					if (keys[0].startsWith("ann")) {
						ret.add(new Triple<>(keys[0], keys[2], response.getValues().get(value)));
					} else {
						for (Pair<IContextualizable, List<String>> resource : resources) {
							if (((ComputableResource) resource.getFirst()).getId().equals(keys[0])) {
								((ComputableResource) resource.getFirst()).setInteractiveParameter(keys[2],
										parseValue(response.getValues().get(value), keys[2], resource.getFirst()));
							}
						}
					}
				}
			}

		} catch (Throwable e) {
			session.getMonitor().error(e);
		}

		return ret;
	}

	private Object parseValue(Object value, String key, IContextualizable resource) {
		boolean found = false;
		if (resource.getServiceCall() != null) {
			Prototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			Argument argument = prototype == null ? null : prototype.getArgument(key);
			if (argument != null) {
				found = true;
				value = argument.getType() == Type.VALUE ? value : TypeUtils.convert(value, Utils.getClassForType(argument.getType()));
			}
		}
		if (!found) {
			for (IAnnotation annotation : ((ComputableResource) resource).getExternalParameters()) {
				if (annotation.get("name", String.class).equals(key)) {
					value = TypeUtils.convert(value, annotation.get("default").getClass());
					break;
				}
			}
		}
		return value;
	}

}
