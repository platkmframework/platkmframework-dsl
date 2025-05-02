package org.platkmframework.dsl.definition;

import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowControlAction;
import org.platkmframework.dsl.steps.FlowStep;

import java.util.UUID;

public class FlowDefinitionWhen<T, I> {

    private final FlowControlAction<T> flowControlAction;
    private final I  parent;

    public FlowDefinitionWhen(FlowControlAction<T> flowControlAction, I parentFlowDefinition) {
        this.flowControlAction = flowControlAction;
        this.parent = parentFlowDefinition;
    }

    public FlowDefinitionWhenStep<T, I> step(FlowStep<T> flowStep){
        return step(UUID.randomUUID().toString(), flowStep);
    }

    public FlowDefinitionWhenStep<T, I> step(String id, FlowStep<T> flowStep){
        return step(id, null, flowStep);
    }

    public FlowDefinitionWhenStep<T, I> step(String id, String label, FlowStep<T> flowStep){
        flowControlAction.add(id, label, flowStep);
        return new FlowDefinitionWhenStep<>(flowControlAction,  parent);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(FlowStep<T>... flowSteps){
        return parallel(UUID.randomUUID().toString());
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I>  parallel(String id, FlowStep<T>... flowSteps){
        return parallel(id, flowSteps);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(String id, String label, FlowStep<T>... flowSteps){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<T>(id, label);
        for (FlowStep<T> flowStep: flowSteps){
            flowActionParallelStep.add(flowStep);
        }
        flowControlAction.add(id, label, flowActionParallelStep);
        return new FlowDefinitionWhenStep<>(flowControlAction,  parent);
    }

}
