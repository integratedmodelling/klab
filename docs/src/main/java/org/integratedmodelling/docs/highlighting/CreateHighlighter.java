package org.integratedmodelling.docs.highlighting;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.engine.Engine;

import com.google.common.base.Charsets;

/**
 * Run this within the Maven workflow to create the highlight.js configuration
 * for the current Kim grammar, with color coding for all the worldview
 * concepts used in the docs.
 * 
 * @author Ferd
 *
 */
public class CreateHighlighter {

	enum Section {
		KEYWORDS, QUALITIES, RELATIONSHIPS, SUBJECTS, EVENTS, TRAITS, ROLES, CONFIGURATIONS, PROCESSES
	}

	static public void main(String[] args) throws IOException {

		Engine engine = Engine.start();

		/*
		 * load template as a string
		 */
		URL url = com.google.common.io.Resources.getResource("highlight/template/template.txt");
		String template = com.google.common.io.Resources.toString(url, Charsets.UTF_8);

		/*
		 * determine output file name
		 */
		IWorldview worldview = Resources.INSTANCE.getWorldview();

		for (Section section : Section.values()) {
			switch (section) {
			case CONFIGURATIONS:
				template = substitute(template, section, collect(IKimConcept.Type.CONFIGURATION, worldview));
				break;
			case EVENTS:
				template = substitute(template, section, collect(IKimConcept.Type.EVENT, worldview));
				break;
			case KEYWORDS:
				template = substitute(template, section, pickKeywords());
				break;
			case PROCESSES:
				template = substitute(template, section, collect(IKimConcept.Type.PROCESS, worldview));
				break;
			case QUALITIES:
				template = substitute(template, section, collect(IKimConcept.Type.QUALITY, worldview));
				break;
			case RELATIONSHIPS:
				template = substitute(template, section, collect(IKimConcept.Type.RELATIONSHIP, worldview));
				break;
			case ROLES:
				template = substitute(template, section, collect(IKimConcept.Type.ROLE, worldview));
				break;
			case SUBJECTS:
				template = substitute(template, section, collect(IKimConcept.Type.SUBJECT, worldview));
				break;
			case TRAITS:
				template = substitute(template, section, collect(IKimConcept.Type.TRAIT, worldview));
				break;
			default:
				break;
			}
		}

		/*
		 * write output
		 */
		System.out.println(template);
		
		engine.stop();

	}

	private static Set<String> pickKeywords() {
		Set<String> ret = new HashSet<>();
		Pattern p = Pattern.compile("^[a-z]*$");
		for (String k : Kim.INSTANCE.getKimKeywords()) {
			if (k.length() > 1 && p.matcher(k).find()) {
				ret.add(k);
			}
		}
		return ret;
	}

	private static Set<String> collect(Type event, IWorldview worldview) {
		Set<String> ret = new HashSet<>();
		for (IProject p : worldview.getProjects()) {
			for (INamespace n : p.getNamespaces()) {
				for (IConcept c : n.getOntology().getConcepts()) {
					if (c.is(event) && !c.toString().contains("_")) {
						ret.add(c.toString());
					}
				}
			}
		}
		return ret;
	}

	private static String substitute(String template, Section section, Set<String> words) {

		int n = 0;
		StringBuffer set = new StringBuffer(1024);
		for (String word : words) {

			// add 5, then switch to a new line
			if ((n % 5) == 0) {
				if (n > 0) {
					set.append("' + \n");
				}
				set.append("     '");
			} else {
				set.append(" ");
			}

			set.append(word);
			n++;
		}

		if (!set.toString().endsWith("'")) {
			set.append("'");
		}

		return template.replaceAll("__" + section.name() + "__", set.toString());
	}

}
