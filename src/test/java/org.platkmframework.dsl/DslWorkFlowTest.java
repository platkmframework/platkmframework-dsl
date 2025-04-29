package org.platkmframework.dsl;

import org.junit.jupiter.api.Test;

public class DslWorkFlowTest {


    @Test
    void flowDefinitionDefaultIds() {

        FlowDefinition<String> p = new FlowDefinition<String>().
                step(new FlowStepTest("Init Process")).
                parallel().
                    add(new FlowStepTest("Load local sales")).
                    add(new FlowStepTest("Request central sales info")).
                endParallel().
                step(new FlowStepTest("Bidirectional check")).
                when(msg -> msg.contains("PENDING_DATA")).
                    step(new FlowStepTest("Process data")).
                    when( msg ->Boolean.TRUE).
                        step( new FlowStepTest("Send Email")).
                    whenEnd().
                whenElse(msg -> Boolean.TRUE).
                    step(new FlowStepTest("Notify data not found"))
                .whenEnd();
        p.run("PENDING_DATA");
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
