package org.platkmframework.dsl.steps;

public class FlowStepBasic implements FlowStep<String> {

    private final String message;

    public FlowStepBasic(String msg) {
        this.message = msg;
    }

    @Override
    public boolean process(String data) {
        System.out.println(message);
        return true;
    }
}
