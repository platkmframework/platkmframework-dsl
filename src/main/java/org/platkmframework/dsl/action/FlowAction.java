package org.platkmframework.dsl.action;

import java.util.UUID;

public abstract  class FlowAction<T>  {

    protected String id;
    protected String label;

    protected FlowAction() {
        this.id = UUID.randomUUID().toString();
        this.label = this.id;
    }

    protected FlowAction(String id) {
        this.id = id;
    }

    protected FlowAction(String id, String label ) {
        this(id);
        this.id = id;
        this.label = label;
    }

    public abstract void process(T data);

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
