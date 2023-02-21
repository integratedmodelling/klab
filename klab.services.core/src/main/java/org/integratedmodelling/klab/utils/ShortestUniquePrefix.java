package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * http://www.mitbbs.com/article_t/JobHunting/32439261.html
 * Compute the shortest unique prefix of each word in a set of words.
 * 
 * For example:
 * 1) apple, bee, cat 			=> shortest unique prefixes: [a, b, c]
 * 2) apple, bee, cat, cedar	=> [a, b, ca, ce]
 * 
 * @author Yue https://github.com/niyue
 */
public class ShortestUniquePrefix {
	
	public List<String> find(List<String> strings) {
		TrieNode root = new TrieNode();
		for(String s : strings) {
			root.insert(s, 0);
		}
		List<String> prefixes = new ArrayList<String>();
		for(String s : strings) {
			String shortestUniquePrefix = root.search(s, 0);
			prefixes.add(shortestUniquePrefix);
		}
		return prefixes;
	}
	
	private static final class TrieNode {
		private Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
		private int count = 0;
		
		public void insert(String s, int i) {
			count++;
			if(i < s.length()) {
				Character current = s.charAt(i);
				if(!children.containsKey(current)) {
					children.put(current, new TrieNode());
				}
				TrieNode child = children.get(current);
				child.insert(s, i + 1);
			}
		}
		
		public String search(String s, int i) {
			String prefix = null;
			if(i > 0 && count == 1) {
				prefix = s.substring(0, i);
			} else {
				Character ch = s.charAt(i);
				TrieNode child = children.get(ch);
				prefix = child.search(s, i + 1);
			}
			return prefix;
		}
	}
}
