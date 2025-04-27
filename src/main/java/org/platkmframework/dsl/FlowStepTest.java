package org.platkmframework.dsl;

public class FlowStepTest implements FlowStep<String> {

    private String msg;
    public FlowStepTest(String msg) {
        this.msg  = msg;
    }

    @Override
    public boolean process(String data) {
        System.out.println(msg);
        return true;
    }
}
