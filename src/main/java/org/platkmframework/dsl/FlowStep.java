package org.platkmframework.dsl;

public interface FlowStep <T>{

    boolean process(T data);
}
