package org.platkmframework.dsl.action;

import java.util.UUID;

public abstract  class FlowAction<T>  {

    protected String id;
    protected String label;

    public FlowAction() {
        this.id = UUID.randomUUID().toString();
        this.label = this.id;
    }

    public FlowAction(String id) {
        this.id = id;
    }

    public FlowAction(String id, String label ) {
        this(id);
        this.id = id;
        this.label = label;
    }

    public abstract boolean process(T data);

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
