package org.platkmframework.dsl;

import org.platkmframework.dsl.action.FlowAction;
import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowActionStep;
import org.platkmframework.dsl.action.FlowControlAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class FlowDefinition<T> {

    private static final Logger log = LoggerFactory.getLogger(FlowDefinition.class);

    private final List<FlowAction<T>> flows;

    public FlowDefinition() {
        this.flows = new ArrayList<>();
    }

    public FlowDefinition<T> step(FlowStep<T>  flowStep){
        flows.add(new FlowActionStep<T>(flowStep));
        return this;
    }

    public FlowDefinition<T> step(String id, FlowStep<T> flowStep){
        flows.add(new FlowActionStep<T>(id, flowStep));
        return this;
    }

    public FlowDefinition<T> step(String id, String label, FlowStep<T> flowStep){
        flows.add(new FlowActionStep<T>(id, label, flowStep));
        return this;
    }

    public FlowDefinition<T> parallel(FlowStep<T>... steps){
        flows.add(new FlowActionParallelStep<T>(steps));
        return this;
    }

    public FlowDefinition<T> parallel(String id, FlowStep<T>... steps){
        return parallel(id, id, steps);
    }

    public FlowDefinition<T> parallel(String id, String label, FlowStep<T>... steps){
        flows.add(new FlowActionParallelStep<T>(id, label, steps));
        return this;
    }

    public FlowDefinitionWhen<T,FlowDefinition<T>> when(Predicate<T>  cond){
        FlowControlAction<T> flowControlAction = new FlowControlAction<T>();
        flowControlAction.addCondition(cond);
        flows.add(flowControlAction);
        return new FlowDefinitionWhen<>(flowControlAction, this);
    }

    public FlowDefinitionWhen<T,FlowDefinition<T>> when(String id, Predicate<T>  cond){
        return when(id, id, cond);
    }

    public FlowDefinitionWhen<T,FlowDefinition<T>> when(String id, String label, Predicate<T>  cond){
        FlowControlAction<T> flowControlAction = new FlowControlAction<T>();
        flowControlAction.addCondition(id, label, cond);
        flows.add(flowControlAction);
        return new FlowDefinitionWhen<>(flowControlAction, this);
    }



    public void run(T data) {
        for(FlowAction<T> flowAction: flows){
            log.info("Flow elements {} {}", flowAction.getId(), flowAction.getLabel());
            flowAction.process(data);
        }
    }
}
