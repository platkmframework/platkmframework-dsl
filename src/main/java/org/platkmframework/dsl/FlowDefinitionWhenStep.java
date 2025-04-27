package org.platkmframework.dsl;

import org.platkmframework.dsl.action.FlowActionCond;
import org.platkmframework.dsl.action.FlowControlAction;

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
        flowControlAction.add(flowStep);
        return this;
    }

    public FlowDefinitionWhenStep<T, I> step(String id, FlowStep<T> flowStep){
        return step(id, id, flowStep);
    }

    public FlowDefinitionWhenStep<T, I> step(String id, String label, FlowStep<T> flowStep){
        flowControlAction.add(id, label, flowStep);
        return this;
    }

    public FlowDefinitionWhenStep<T, I> whenElse(String id, Predicate<T> cond){
        this.flowControlAction.addCondition(id, cond);
        return this;
    }

    public FlowDefinitionWhenStep<T, I> whenElse(String id, String label, Predicate<T> cond){
        this.flowControlAction.addCondition(id, label, cond);
        return this;
    }

    public FlowDefinitionWhenStep<T, I> whenElse(Predicate<T> cond){
        this.flowControlAction.addCondition(cond);
        return this;
    }

    public I whenEnd(){
        return parent;
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T, I>> when(Predicate<T> cond){
        return when(UUID.randomUUID().toString(), cond);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T, I>> when(String id, Predicate<T> cond){
        FlowControlAction<T> whenflowControlAction = new FlowControlAction<T>();
        FlowActionCond<T> whenflowActionCond = whenflowControlAction.addCondition(id, id, cond);
        flowControlAction.add(whenflowControlAction);
        return new FlowDefinitionWhen<>(whenflowControlAction, this);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T, I>> when(String id, String label, Predicate<T> cond){
        FlowControlAction<T> whenflowControlAction = new FlowControlAction<T>();
        FlowActionCond<T> whenflowActionCond = whenflowControlAction.addCondition(id, id, cond);
        flowControlAction.add(whenflowControlAction);
        return new FlowDefinitionWhen<>(whenflowControlAction, this);
    }
}
