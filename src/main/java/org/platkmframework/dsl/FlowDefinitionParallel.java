package org.platkmframework.dsl;

import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowControlAction;

import java.util.UUID;

public class FlowDefinitionParallel<T,I>{

    private final FlowActionParallelStep<T> flowActionParallelStep;
    private final I parent;

    public FlowDefinitionParallel(FlowActionParallelStep<T> flowActionParallelStep, I parentFlowDefinition) {
        this.flowActionParallelStep = flowActionParallelStep;
        this.parent = parentFlowDefinition;
    }

    public FlowDefinitionParallel<T,I> add(FlowStep<T> step){
        return add(UUID.randomUUID().toString(), null, step);
    }

    public FlowDefinitionParallel<T,I> add(String id, FlowStep<T> step){
        return add(id, null, step);
    }

    public FlowDefinitionParallel<T,I> add(String id, String label, FlowStep<T> step){
        flowActionParallelStep.add(id, label, step);
        return this;
    }

    public I endParallel(){ return parent;}
}
