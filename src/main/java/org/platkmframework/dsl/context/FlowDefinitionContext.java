package org.platkmframework.dsl.context;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.List;

public class FlowDefinitionContext<T> {

    private List<? extends FlowStep<T>> flows;

    public FlowDefinitionContext(List<? extends FlowStep<T>> flows) {
        this.flows = flows;
    }


}
