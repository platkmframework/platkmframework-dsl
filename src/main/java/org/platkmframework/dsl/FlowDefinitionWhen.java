package org.platkmframework.dsl;

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


    /**  public T jump(String id, String stepId){

        //searching for flowaction  in flows and add again in flows
        FlowActionStep flowAction = flows.stream().filter(f->f.g);
        flows.add(new FlowActionStep(id, flowStep));
        return new FlowDefinitionWhenStepJump<T>(parent);
    }
*/


}
