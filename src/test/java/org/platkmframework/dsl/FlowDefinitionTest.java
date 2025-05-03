package org.platkmframework.dsl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.platkmframework.dsl.builder.FlowDefinitionBuilder;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;

class FlowDefinitionTest extends FlowDefinitionTestBase {

    @Test
    void deepTestOptions() {

        FlowDefinitionRunner<Data> flowRunner = FlowDefinitionBuilder.
                builder(new FlowDefinitionContext<Data>()).
                            when(msg-> Boolean.TRUE).log("LOG-> when condition 1").
                                step(new Step1()).log("LOG-> Step 1").
                                when(msg->Boolean.TRUE).log("LOG-> when condition 2").
                                    step(new Step2()).log("LOG-> Step 2").
                                whenElse(msg-> Boolean.TRUE).log("LOG-> whenElse 1").
                                    step(new Step3()).
                                whenElse(msg->Boolean.TRUE).
                                    step(new Step4()).
                                    when(msg->Boolean.TRUE).
                                        parallel(new Step5()).
                                        step(new Step6()).
                                        when(msg->Boolean.TRUE).
                                            step(new Step7()).
                                            when(msg->Boolean.TRUE).
                                                step(new Step8()).
                                            whenElse(msg->Boolean.TRUE).
                                                step(new Step9()).
                                            whenElse(msg->Boolean.TRUE).
                                                step(new Step10()).
                                                    parallel(new Step11(),new Step12()).
                                            end().
                                        end().
                                    end().
                                end().
                            whenElse(msg -> Boolean.TRUE).
                                step(new Step13()).
                            whenElse(msg-> Boolean.TRUE).
                                step(new Step14()).
                            end().
                            step(new Step15()).
                            parallel(new Step16(), new Step17()).
                            when(msg->Boolean.FALSE).
                                step(new Step18()).
                            end().
                build();

        Data data = new Data();
        flowRunner.run(data);
        flowRunner.run(data);

        Assertions.assertThat(data.stringBuilder.toString()).doesNotContain("Step18");

    }


    @Test
    void deepTestOptionsContext(){

        FlowDefinitionContext<Data> flowDefinitionContext =
                new FlowDefinitionContext<>(STEPS);

        FlowDefinitionRunner<Data> flowRunner = FlowDefinitionBuilder.
                builder(flowDefinitionContext).log("Starting flow").
                when(msg-> Boolean.TRUE).
                    step(Step1.class).
                    when(data->Boolean.TRUE).
                        step(Step2.class).
                    whenElse(data-> Boolean.TRUE).
                        step(Step3.class).
                    whenElse(msg->Boolean.TRUE).
                        step(Step4.class).
                        when(msg->Boolean.TRUE).
                            parallel(Step5.class).
                            step(Step6.class).
                            when(msg->Boolean.TRUE).
                                step(Step7.class).
                                when(msg->Boolean.TRUE).
                                    step(Step8.class).
                                whenElse(msg->Boolean.TRUE).
                                    step(Step9.class).
                                whenElse(msg->Boolean.TRUE).
                                    step(Step10.class).
                                        parallel(Step11.class, Step12.class).
                                end().
                            end().
                        end().
                    end().
                whenElse(msg -> Boolean.TRUE).
                    step(Step13.class).
                whenElse(msg-> Boolean.TRUE).
                    step(Step14.class).
                end().
                step(Step15.class).
                    parallel(Step16.class, Step17.class).
                when(msg->Boolean.FALSE).
                    step(Step18.class).
                end().
                build();

        Data data = new Data();
        flowRunner.run(data);
        System.out.println(data.stringBuilder.toString());

        Assertions.assertThat(data.stringBuilder.toString()).doesNotContain("Step18");
    }
}
