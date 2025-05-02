package org.platkmframework.dsl.definition;

import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowControlAction;
import org.platkmframework.dsl.steps.FlowStep;

import java.util.UUID;
import java.util.function.Predicate;

public class FlowDefinitionWhenStep<T, I> {

    private final I parent;
    private final FlowControlAction<T> flowControlAction;

    public  FlowDefinitionWhenStep(FlowControlAction<T> flowControlAction, I parent) {
        this.flowControlAction = flowControlAction;
        this.parent = parent;
    }

    public FlowDefinitionWhenStep<T, I> step(FlowStep<T> flowStep){
        return step(UUID.randomUUID().toString(), flowStep);
    }

    public FlowDefinitionWhenStep<T, I> step(String id, FlowStep<T> flowStep){
        return step(id, null, flowStep);
    }

    public FlowDefinitionWhenStep<T, I> step(String id, String label, FlowStep<T> flowStep){
        flowControlAction.add(id, label, flowStep);
        return this;
    }

    public FlowDefinitionWhenStep<T, I> parallel(FlowStep<T>... flowSteps){
        return parallel(UUID.randomUUID().toString(), flowSteps);
    }

    public FlowDefinitionWhenStep<T, I> parallel(String id, FlowStep<T>... flowSteps){
        return parallel(id, null, flowSteps);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(String id, String label, FlowStep<T>... flowSteps){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<T>(id, label);
        for (FlowStep<T> flowStep: flowSteps){
            flowActionParallelStep.add(flowStep);
        }
        flowControlAction.add(id, label, flowActionParallelStep);
        return this;
    }

    public FlowDefinitionWhen <T,I> whenElse(Predicate<T> cond){
        return whenElse(UUID.randomUUID().toString(), cond);
    }

    public FlowDefinitionWhen<T,I> whenElse(String id, Predicate<T> cond){
        return whenElse(id, null, cond);
    }

    public FlowDefinitionWhen<T,I> whenElse(String id, String label, Predicate<T> cond){
        this.flowControlAction.addCondition(id, label, cond);
        return  new FlowDefinitionWhen<>(this.flowControlAction, parent);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T,I>> when(Predicate<T>  cond){
        return when(UUID.randomUUID().toString(), cond);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T,I>> when(String id, Predicate<T>  cond){
        return when(id, null, cond);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T,I>> when(String id, String label, Predicate<T>  cond){
        FlowControlAction<T> flowControlAction1 = new FlowControlAction<T>();
        flowControlAction1.addCondition(id, label, cond);
        this.flowControlAction.add(flowControlAction1);
        return new FlowDefinitionWhen<>(flowControlAction1, this);
    }

    public I end(){
        return parent;
    }


}
