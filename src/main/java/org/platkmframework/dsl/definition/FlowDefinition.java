package org.platkmframework.dsl.definition;

import org.platkmframework.dsl.action.FlowAction;
import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowActionStep;
import org.platkmframework.dsl.action.FlowControlAction;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;
import org.platkmframework.dsl.steps.FlowStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class FlowDefinition<T> {

    private static final Logger log = LoggerFactory.getLogger(FlowDefinition.class);

    private final List<FlowAction<T>> flows;
    FlowDefinitionContext<T> flowDefinitionContext;

    public FlowDefinition() {
        this.flows = new ArrayList<>();
    }

    public FlowDefinition(FlowDefinitionContext<T> flowDefinitionContext) {
        this();
        this.flowDefinitionContext = flowDefinitionContext;
    }

    public FlowDefinition<T> step(FlowStep<T> flowStep){
        return step(UUID.randomUUID().toString(), flowStep);
    }

    public FlowDefinition<T> step(String id, FlowStep<T> flowStep){
        return step(id, null, flowStep);
    }

    public FlowDefinition<T> step(String id, String label, FlowStep<T> flowStep){
        flows.add(new FlowActionStep<>(id, label, flowStep));
        return this;
    }

    public FlowDefinition<T> step(Class<? extends FlowStep<T>> flowStepClass){
        return step(UUID.randomUUID().toString(), flowStepClass);
    }

    public FlowDefinition<T> step(String id, Class<? extends FlowStep<T>> flowStepClass){
        return step(id, null, flowStepClass);
    }

    public FlowDefinition<T> step(String id, String label, Class<? extends FlowStep<T>> flowStepClass){
        return step(id, label, flowDefinitionContext.getBean(flowStepClass));
    }

    @SafeVarargs
    public final FlowDefinition<T> parallel(FlowStep<T>... flowSteps){
        return parallel(UUID.randomUUID().toString(), flowSteps);
    }

    @SafeVarargs
    public final FlowDefinition<T> parallel(String id, FlowStep<T>... flowSteps){
        return parallel(id, null, flowSteps);
    }

    @SafeVarargs
    public final FlowDefinition<T> parallel(String id, String label, FlowStep<T>... flowSteps){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<>(id, label);
        for (FlowStep<T> flowStep: flowSteps){
            flowActionParallelStep.add(flowStep);
        }
        flows.add(flowActionParallelStep);
        return this;
    }

    @SafeVarargs
    public final FlowDefinition<T> parallel(Class<? extends FlowStep<T>> ... flowStepClass){
        return parallel(UUID.randomUUID().toString(), flowStepClass);
    }

    @SafeVarargs
    public final FlowDefinition<T> parallel(String id, Class<? extends FlowStep<T>> ... flowStepClass){
        return parallel(id, null, flowStepClass);
    }

    @SafeVarargs
    public final FlowDefinition<T> parallel(String id, String label, Class<? extends FlowStep<T>> ... flowStepClass){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<>(id, label);
        for (Class<? extends FlowStep<T>> flowStepClas: flowStepClass){
            flowActionParallelStep.add(flowDefinitionContext.getBean(flowStepClas));
        }
        flows.add(flowActionParallelStep);
        return this;
    }

    public FlowDefinitionWhen<T,FlowDefinition<T>> when(Predicate<T>  cond){
         return when(UUID.randomUUID().toString(), cond);
    }

    public FlowDefinitionWhen<T,FlowDefinition<T>> when(String id, Predicate<T>  cond){
        return when(id, id, cond);
    }

    public FlowDefinitionWhen<T,FlowDefinition<T>> when(String id, String label, Predicate<T>  cond){
        FlowControlAction<T> flowControlAction = new FlowControlAction<>();
        flowControlAction.addCondition(id, label, cond);
        flows.add(flowControlAction);
        return new FlowDefinitionWhen<>(flowControlAction, this, flowDefinitionContext);
    }

    public FlowDefinition<T> log(String msg){
        log.info(msg);
        return  this;
    }

    public FlowDefinitionRunner<T> build(){
        return new FlowDefinitionRunner<>(this.flows);
    }


}
