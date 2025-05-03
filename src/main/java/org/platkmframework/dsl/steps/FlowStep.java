package org.platkmframework.dsl.steps;

public interface FlowStep <T>{

    void process(T data);
}
