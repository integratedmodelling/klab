/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.utils;

import java.util.List;
// TODO: Auto-generated Javadoc
/**
 * Temp for an ASCII tree visualizer
 * from https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 *
 * @author Vasya Novikov
 * @author Ferd
 * @version $Id: $Id
 */
public class TreeNode {

    final String name;
    final List<TreeNode> children;

    /**
     * Instantiates a new tree node.
     *
     * @param name the name
     * @param children the children
     */
    public TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
    }

    /**
     * Prints the.
     */
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
