package org.platkmframework.dsl.definition;

import org.platkmframework.dsl.action.FlowActionParallelStep;
import org.platkmframework.dsl.action.FlowControlAction;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.steps.FlowStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FlowDefinitionWhen<T, I> {

    private static final Logger log = LoggerFactory.getLogger(FlowDefinitionWhen.class);

    private final FlowControlAction<T> flowControlAction;
    private final I  parent;
    private final FlowDefinitionContext<T> flowDefinitionContext;

    public FlowDefinitionWhen(FlowControlAction<T> flowControlAction, I parentFlowDefinition, FlowDefinitionContext<T> flowDefinitionContext) {
        this.flowControlAction = flowControlAction;
        this.parent = parentFlowDefinition;
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
        return new FlowDefinitionWhenStep<>(flowControlAction, parent, flowDefinitionContext);
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
    public final FlowDefinitionWhenStep<T, I>  parallel(String id, FlowStep<T>... flowSteps){
        return parallel(id, null, flowSteps);
    }

    @SafeVarargs
    public final FlowDefinitionWhenStep<T, I> parallel(String id, String label, FlowStep<T>... flowSteps){
        FlowActionParallelStep<T> flowActionParallelStep = new FlowActionParallelStep<>(id, label);
        for (FlowStep<T> flowStep: flowSteps){
            flowActionParallelStep.add(flowStep);
        }
        flowControlAction.add(flowActionParallelStep);
        return new FlowDefinitionWhenStep<>(flowControlAction, parent, flowDefinitionContext);
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

    public FlowDefinitionWhen<T,I> log(String msg){
        log.info(msg);
        return  this;
    }

}