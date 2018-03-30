package org.integratedmodelling.klab.utils;

import java.util.List;
/**
 * Temp for an ASCII tree visualizer
 * from https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 * @author Vasya Novikov
 * @author Ferd
 *
 */
public class TreeNode {

    final String name;
    final List<TreeNode> children;

    public TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}
