package org.platkmframework.dsl;

import org.junit.jupiter.api.Test;
import org.platkmframework.dsl.builder.FlowDefinitionBuilder;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;
import org.platkmframework.dsl.steps.FlowStepBasic;

public class DslWorkFlowTest {

    @Test
    void flowDefinitionDefaultIds() {

        FlowDefinitionRunner<String> flowRunner = FlowDefinitionBuilder.
                builder(String.class).
                            when(msg-> Boolean.TRUE).
                                step(new FlowStepBasic("Step 1")).
                                when("next", "Next Step",msg->Boolean.TRUE).
                                    step(new FlowStepBasic("Step 2")).
                                whenElse(msg-> Boolean.TRUE).
                                    step(new FlowStepBasic("Stpe 3")).
                                whenElse(msg->Boolean.TRUE).
                                    step(new FlowStepBasic("5-parallel- Step not used")).
                                    when("id", "label",msg->Boolean.TRUE).
                                        step(new FlowStepBasic("5-parallel- Step not used")).
                                        when("id", "label",msg->Boolean.TRUE).
                                            step(new FlowStepBasic("5-parallel- Step not used")).
                                            when("id", "label",msg->Boolean.TRUE).
                                                step(new FlowStepBasic("5-parallel- Step not used")).
                                            whenElse(msg->Boolean.TRUE).
                                                step(new FlowStepBasic("5-parallel- Step not used")).
                                            whenElse(msg->Boolean.TRUE).
                                                step(new FlowStepBasic("5-parallel- Step not used")).
                                            end().
                                        end().
                                    end().
                                end().
                            whenElse(msg -> Boolean.TRUE).
                                step("id", "label",new FlowStepBasic("6-Notify data not found in local area 1")).
                            whenElse(msg-> Boolean.FALSE).
                                step("id", "label", new FlowStepBasic("7-Notify data not found in central area 2")).
                            end().
                build();
        flowRunner.run("PENDING_DATA");

         /**       build();
                    when(msg-> Boolean.TRUE).
                        parallel().
                            add(new FlowStepBasic("8-parallel-store local process id")).
                            add(new FlowStepBasic("9-parallel-store central process id")).
                        end().
                    end().
                build();*/


    }
/**
    @Test
    void flowDefinitionIds() {

        FlowDefinition<String> p = new FlowDefinition<String>().
                step("init", new FlowStepTest("Init Process")).
                    when("existsPending",msg -> msg.contains("PENDING_DATA")).
                        step("processData", new FlowStepTest("Process data")).
                        when("defaultOK", msg ->Boolean.TRUE).
                            step("sendMail", new FlowStepTest("Send Email")).
                        whenEnd().
                    whenElse("defaultElse", msg -> Boolean.TRUE).
                        step("notify", new FlowStepTest("Notify data not found"))
                    .whenEnd();
        p.run("PENDING_DATA");
    }


    @Test
    void flowDefinitionLabels() {

        FlowDefinition<String> p = new FlowDefinition<String>().
                step("init", "Init Process", new FlowStepTest("Init Process")).
                    when("existsPending", " Check if pending data exists", msg -> msg.contains("PENDING_DATA")).
                step("processData", "Process Data", new FlowStepTest("Process data")).
                    when("defaultOK", " Its always true",  msg ->Boolean.TRUE).
                        step("sendMail", "step to send email",new FlowStepTest("Send Email")).
                    whenEnd().
                whenElse("defaultElse", "Its always true", msg -> Boolean.TRUE).
                    step("notify", "step to notify", new FlowStepTest("Notify data not found"))
                .whenEnd();

        p.run("PENDING_DATA");
    }


/
    @Test
    void flowDefinitionWithOutPending() {

        FlowDefinition<String> p = new FlowDefinition<String>().
                step("init", new FlowStepTest("Init Process")).
                    when("existsPending",msg -> msg.contains("PENDING_DATA")).
                step("processData", new FlowStepTest("Process data")).
                    when("defaultOK", msg ->Boolean.TRUE).
                        step("sendMail", new FlowStepTest("Send Email")).
                    whenEnd().
                whenElse("defaultElse", msg -> Boolean.TRUE).
                    step("notify", new FlowStepTest("Notify data not found"))
                .whenEnd();

        p.run("");

        p = new FlowDefinition<String>().
                step("init", new FlowStepTest("Init Process")).
                    when("existsPending",msg -> msg.contains("PENDING_DATA")).
                        step("processData", new FlowStepTest("Process data")).
                    when("ok", msg -> msg.contains("OK")).
                        step("sendMail", new FlowStepTest("Send Email")).
                    whenEnd().
                whenElse("defaultElse", msg -> Boolean.TRUE).
                    step("notify", new FlowStepTest("Notify data not found"))
                .whenEnd();

        p.run("PENDING_DATA");
    }


    @Test
    void flowDefinitionParallel() {

        FlowDefinition<String> p = new FlowDefinition<String>().
                parallel("searchingStoreInfo",new FlowStepTest("Search Info store 1"), new FlowStepTest("Search Info store 2")).
                when("exists data", msg -> msg.contains("DATA")).
                    step("processData", new FlowStepTest("Process data")).
                whenElse("NOT exists data", msg -> !msg.contains("DATA")).
                    step("notify", new FlowStepTest("Notify data not found"))
                .whenEnd();

        p.run("DATA");

       p = new FlowDefinition<String>().
                parallel(new FlowStepTest("Search Info store 1"), new FlowStepTest("Search Info store 2")).
                    when(msg -> msg.contains("DATA")).
                        step(new FlowStepTest("Process data")).
                    whenElse(msg -> !msg.contains("DATA")).
                        step(new FlowStepTest("Notify data not found"))
                .whenEnd();

        p.run("DATA");

    }
*/
}
