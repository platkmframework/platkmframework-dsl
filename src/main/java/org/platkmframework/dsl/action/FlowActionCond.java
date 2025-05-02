package org.platkmframework.dsl.action;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FlowActionCond<T>  extends FlowAction<T>  {

    private final Predicate<T> cond;
    private final List<FlowAction<T>> flows;

    public FlowActionCond(Predicate<T> cond) {
        super();
        this.cond = cond;
        this.flows = new ArrayList<>();
    }

    public FlowActionCond(String id, Predicate<T> cond) {
        this(id, id, cond);
    }

    public FlowActionCond(String id, String label, Predicate<T> cond) {
        super(id, label);
        this.cond = cond;
        this.flows = new ArrayList<>();
    }

    public void add(FlowStep<T> flowStep){
        flows.add(new FlowActionStep<>(flowStep));
    }

    public void add(String id, FlowStep<T> flowStep){
        add(id, id, flowStep);
    }

    public void add(String id, String label, FlowStep<T> flowStep){
        flows.add(new FlowActionStep<>(id, label, flowStep));
    }

    public void add(FlowActionParallelStep<T> flowActionParallelStep){
        flows.add(flowActionParallelStep);
    }

    public void add(FlowControlAction<T> whenflowControlAction){
        flows.add(whenflowControlAction);
    }

    @Override
    public  boolean process(T data) {
        if(cond.test(data)){
            for(FlowAction<T> flowAction: flows){
                flowAction.process(data);
            }
            return true;
        }else
            return false;
    }
}
