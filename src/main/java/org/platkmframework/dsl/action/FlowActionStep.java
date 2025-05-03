package org.platkmframework.dsl.action;

import org.platkmframework.dsl.steps.FlowStep;

public class FlowActionStep<T>  extends FlowAction<T> {

    private final FlowStep<T>flowStep;

    public FlowActionStep(String id, String label, FlowStep<T> flowStep) {
        super(id, label);
        this.flowStep = flowStep;
    }

    @Override
    public void process(T data) {
        flowStep.process(data);
    }


}
