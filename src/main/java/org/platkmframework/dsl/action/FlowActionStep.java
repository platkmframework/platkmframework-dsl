package org.platkmframework.dsl.action;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.UUID;

public class FlowActionStep<T>  extends FlowAction<T> {

    private final FlowStep<T>flowStep;

    public FlowActionStep(String id, String label, FlowStep<T> flowStep) {
        super(id, label);
        this.flowStep = flowStep;
    }

    @Override
    public boolean process(T data) {
        return flowStep.process(data);
    }
}
