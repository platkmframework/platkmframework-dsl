package org.platkmframework.dsl;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.List;

public abstract class FlowDefinitionTestBase {

    static class Data {
        public StringBuilder stringBuilder = new StringBuilder();
    }

    static class Step1 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 1");
        }
    }

    static class Step2 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 2");
        }
    }

    static class Step3 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 3");
        }
    }

    static class Step4 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 4");
        }
    }

    static class Step5 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 5");
        }
    }

    static class Step6 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 6");
        }
    }

    static class Step7 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 7");
        }
    }

    static class Step8 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 8");
        }
    }

    static class Step9 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 9");
        }
    }

    static class Step10 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 10");
        }
    }

    static class Step11 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 11");
        }
    }

    static class Step12 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 12");
        }
    }

    static class Step13 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 13");
        }
    }

    static class Step14 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 14");
        }
    }

    static class Step15 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 15");
        }
    }

    static class Step16 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 16");
        }
    }

    static class Step17 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 17");
        }
    }

    static class Step18 implements FlowStep<Data> {
        @Override
        public void process(Data data) {
            data.stringBuilder.append("Step 18");
        }
    }

    static final List<FlowStep<Data>> STEPS = List.of(
            new Step1(), new Step2(), new Step3(), new Step4(),
            new Step5(), new Step6(), new Step7(), new Step8(),
            new Step9(), new Step10(), new Step11(), new Step12(),
            new Step13(), new Step14(), new Step15(), new Step16()
            , new Step17(), new Step18()
    );


}

