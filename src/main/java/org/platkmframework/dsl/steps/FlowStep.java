package org.platkmframework.dsl.steps;

public interface FlowStep <T>{

    boolean process(T data);
}
