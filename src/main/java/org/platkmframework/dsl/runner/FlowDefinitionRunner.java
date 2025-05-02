package org.platkmframework.dsl.runner;

import org.platkmframework.dsl.action.FlowAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FlowDefinitionRunner<T> {

    private static final Logger log = LoggerFactory.getLogger(FlowDefinitionRunner.class);

    List<FlowAction<T>> flows;

    public FlowDefinitionRunner(List<FlowAction<T>> flows) {
        this.flows = flows;
    }

    public void run(T data) {
        for(FlowAction<T> flowAction: flows){
            log.info("Flow elements {} {}", flowAction.getId(), flowAction.getLabel());
            flowAction.process(data);
        }
    }
}
