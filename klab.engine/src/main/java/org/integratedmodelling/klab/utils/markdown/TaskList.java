package org.integratedmodelling.klab.utils.markdown;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends UnorderedList<TaskListItem> {

    public TaskList() {
        this.items = new ArrayList<>();
    }

    public TaskList(List<TaskListItem> items) {
        this.items = items;
    }

}
