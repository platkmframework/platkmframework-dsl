package org.platkmframework.dsl.context;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.ArrayList;
import java.util.List;

public class FlowDefinitionContext<T> {

    private final List<? extends FlowStep<T>> components;

    public FlowDefinitionContext(List<? extends FlowStep<T>> components) {
        this.components = components;
    }

    public FlowDefinitionContext() {
        this.components = new ArrayList<>();
    }

    public  FlowStep<T> getBean(Class<? extends FlowStep<T>> flowStepClass){
        return components.stream().filter(c-> c.getClass().isAssignableFrom(flowStepClass)).findFirst().orElse(null);
    }
}
