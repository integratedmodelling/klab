package org.integratedmodelling.klab.documentation;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * Simple AsciiDoc document builder with delayed action placeholders. Very
 * hacky.
 * 
 * @author Ferd
 *
 */
public class AsciiDocBuilder {

	private static final String SECTION_MARKER = "_#_#_#_#_#_#_#_#_#_#";
	private static final String ACTION_MARKER = "_A_A_A_A_A_A_A_A_A_A";

	Section rootSection;

	public class Section {

		String id = NameGenerator.newName();
		Section parent;
		String title;
		int level = 1;
		List<String> paragraphs = new ArrayList<>();
		List<Section> children = new ArrayList<>();
		List<Supplier<String>> actions = new ArrayList<>();

		public Section(String title) {
			this.title = title;
		}

		public void paragraph(String string) {
			paragraphs.add(string);
		}

		public Section getChild(String title) {
			Section section = new Section(title);
			section.level = this.level + 1;
			section.parent = this;
			this.children.add(section);
			paragraphs.add(SECTION_MARKER + section.id);
			return section;
		}

		public void action(Supplier<String> action) {
			actions.add(action);
			paragraphs.add(ACTION_MARKER + (actions.size() - 1));
		}

		public void listingBlock(String sourceCode, String language) {
			paragraphs.add("[source" + (language == null ? "]" : ("," + language)) + "]\n----\n" + sourceCode
					+ (sourceCode.endsWith("\n") ? "" : "\n") + "----\n");
		}

	}

	public AsciiDocBuilder(String title) {
		rootSection = new Section(title);
	}

	public AsciiDocBuilder() {
		rootSection = new Section(null);
	}

	public void writeToFile(Path path, Charset charset) {
		try (OutputStream output = new FileOutputStream(path.toFile())) {
			PrintWriter writer = new PrintWriter(output);
			write(rootSection, writer);
			writer.flush();
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	private void write(Section section, PrintWriter writer) {

		if (section.title != null) {
			writer.println(StringUtil.repeat('#', section.level) + " " + section.title + "\n");
			for (String paragraph : section.paragraphs) {
				if (paragraph.startsWith(SECTION_MARKER)) {
					for (Section child : section.children) {
						if (paragraph.equals(SECTION_MARKER + child.id)) {
							writer.println();
							write(child, writer);
							break;
						}
					}
				} else if (paragraph.startsWith(ACTION_MARKER)) {
					int index = Integer.parseInt(paragraph.substring(ACTION_MARKER.length()));
					writer.println(section.actions.get(index).get());
				} else {
					writer.println(paragraph + "\n");
				}
			}
		}

	}

	public Section getRootSection() {
		return rootSection;
	}

}
