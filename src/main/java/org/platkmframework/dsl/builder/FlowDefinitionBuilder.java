package org.platkmframework.dsl.builder;

import org.platkmframework.dsl.definition.FlowDefinition;


public class FlowDefinitionBuilder {

    private FlowDefinitionBuilder(){}

    public static <T> FlowDefinition<T> builder(Class<T> classType){
        return new FlowDefinition<>();
    }


}
