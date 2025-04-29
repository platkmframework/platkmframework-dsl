package org.platkmframework.dsl;

import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowControlAction;

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
        flowControlAction.add(id, flowStep);
        return new FlowDefinitionWhenStep<>(flowControlAction,  parent);
    }

    public FlowDefinitionWhenStep<T, I> step(String id, String label, FlowStep<T> flowStep){
        flowControlAction.add(id, label, flowStep);
        return new FlowDefinitionWhenStep<>(flowControlAction,  parent);
    }

    public FlowDefinitionParallel<T, FlowDefinitionWhen<T,I>> parallel(){
        return parallel(UUID.randomUUID().toString());
    }

    public FlowDefinitionParallel<T, FlowDefinitionWhen<T,I>>  parallel(String id){
        return parallel(id, null);
    }

    public FlowDefinitionParallel<T, FlowDefinitionWhen<T,I>> parallel(String id, String label){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<T>(id, label);
        flowControlAction.add(id, label, flowActionParallelStep);
        return new FlowDefinitionParallel<>(flowActionParallelStep, this);
    }

}
