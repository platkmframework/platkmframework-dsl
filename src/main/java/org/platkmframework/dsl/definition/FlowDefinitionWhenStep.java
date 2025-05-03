package org.platkmframework.dsl.definition;

import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowControlAction;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.steps.FlowStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Predicate;

public class FlowDefinitionWhenStep<T, I> {

    private static final Logger log = LoggerFactory.getLogger(FlowDefinitionWhenStep.class);

    private final I parent;
    private final FlowControlAction<T> flowControlAction;
    private final FlowDefinitionContext<T> flowDefinitionContext;

    public  FlowDefinitionWhenStep(FlowControlAction<T> flowControlAction, I parent, FlowDefinitionContext<T> flowDefinitionContext) {
        this.flowControlAction = flowControlAction;
        this.parent = parent;
        this.flowDefinitionContext = flowDefinitionContext;
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

    public FlowDefinitionWhenStep<T,I> step(Class<? extends FlowStep<T>> flowStepClass){
        return step(UUID.randomUUID().toString(), flowStepClass);
    }

    public FlowDefinitionWhenStep<T,I> step(String id, Class<? extends FlowStep<T>> flowStepClass){
        return step(id, null, flowStepClass);
    }

    public FlowDefinitionWhenStep<T,I> step(String id, String label, Class<? extends FlowStep<T>> flowStepClass){
        return step(id, label, flowDefinitionContext.getBean(flowStepClass));
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(FlowStep<T>... flowSteps){
        return parallel(UUID.randomUUID().toString(), flowSteps);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(String id, FlowStep<T>... flowSteps){
        return parallel(id, null, flowSteps);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(String id, String label, FlowStep<T>... flowSteps){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<>(id, label);
        for (FlowStep<T> flowStep: flowSteps){
            flowActionParallelStep.add(flowStep);
        }
        flowControlAction.add(flowActionParallelStep);
        return this;
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T,I> parallel(Class<? extends FlowStep<T>> ... flowStepClass){
        return parallel(UUID.randomUUID().toString(), flowStepClass);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T,I> parallel(String id, Class<? extends FlowStep<T>> ... flowStepClass){
        return parallel(id, null, flowStepClass);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T,I> parallel(String id, String label, Class<? extends FlowStep<T>> ... flowStepClass){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<>(id, label);
        for (Class<? extends FlowStep<T>> flowStepClas: flowStepClass){
            flowActionParallelStep.add(flowDefinitionContext.getBean(flowStepClas));
        }
        flowControlAction.add(flowActionParallelStep);
        return new FlowDefinitionWhenStep<>(flowControlAction, parent, flowDefinitionContext);
    }

    public FlowDefinitionWhen <T,I> whenElse(Predicate<T> cond){
        return whenElse(UUID.randomUUID().toString(), cond);
    }

    public FlowDefinitionWhen<T,I> whenElse(String id, Predicate<T> cond){
        return whenElse(id, null, cond);
    }

    public FlowDefinitionWhen<T,I> whenElse(String id, String label, Predicate<T> cond){
        this.flowControlAction.addCondition(id, label, cond);
        return  new FlowDefinitionWhen<>(this.flowControlAction, parent, flowDefinitionContext);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T,I>> when(Predicate<T>  cond){
        return when(UUID.randomUUID().toString(), cond);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T,I>> when(String id, Predicate<T>  cond){
        return when(id, null, cond);
    }

    public FlowDefinitionWhen<T,FlowDefinitionWhenStep<T,I>> when(String id, String label, Predicate<T>  cond){
        FlowControlAction<T> flowControlAction1 = new FlowControlAction<>();
        flowControlAction1.addCondition(id, label, cond);
        this.flowControlAction.add(flowControlAction1);
        return new FlowDefinitionWhen<>(flowControlAction1, this, flowDefinitionContext);
    }

    public I end(){
        return parent;
    }

    public FlowDefinitionWhenStep<T,I> log(String msg){
        log.info(msg);
        return  this;
    }


}
