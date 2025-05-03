package org.platkmframework.dsl.builder;

import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.definition.FlowDefinition;

public class FlowDefinitionBuilder {

    private FlowDefinitionBuilder(){}

    public static <T> FlowDefinition<T> builder(FlowDefinitionContext<T> flowDefinitionContext){
        return new FlowDefinition<>(flowDefinitionContext);
    }


}
