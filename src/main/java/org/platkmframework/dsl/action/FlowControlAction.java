package org.platkmframework.dsl.action;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FlowControlAction<T> extends FlowAction<T>{

    private final List<FlowActionCond<T>> conditions;

    public FlowControlAction( ) {
        super();
        this.conditions = new ArrayList<>();
    }

    public FlowActionCond<T> addCondition(String id, String label, Predicate<T> cond){
        FlowActionCond<T> flowActionCond =  new FlowActionCond<T>(id, label, cond);
        this.conditions.add(flowActionCond);
        return flowActionCond;
    }

    public void add(String id, String label,  FlowStep<T> flowStep) {
        conditions.get(conditions.size()-1).add(id, label, flowStep);
    }

    public void add(String id, String label,  FlowActionParallelStep<T> flowActionParallelStep) {
        conditions.get(conditions.size()-1).add(flowActionParallelStep);
    }

    public void add(FlowControlAction<T> whenflowControlAction) {
        conditions.get(conditions.size()-1).add(whenflowControlAction);
    }

    @Override
    public boolean process(T data) {
        for (FlowActionCond<T> flowActionCond : conditions) {
            flowActionCond.process(data);
        }
        return true;

    }
}
