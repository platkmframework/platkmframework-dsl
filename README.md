# platkmframework-dsl

DSL significa Domain-Specific Language (Lenguaje Específico de Dominio).

```
    FlowDefinition<String> p = new FlowDefinition<String>().
            step(new FlowStepTest("Init Process")).
            when(msg -> msg.contains("PENDING_DATA")).
                step(new FlowStepTest("Process data")).
                when( msg ->Boolean.TRUE).
                    step( new FlowStepTest("Send Email")).
                whenEnd().
            whenElse(msg -> Boolean.TRUE).
                step(new FlowStepTest("Notify data not found"))
            .whenEnd();
    p.run("PENDING_DATA")
```

```
    FlowDefinition<String> p = new FlowDefinition<String>().
            parallel("searchingStoreInfo",new FlowStepTest("Search Info store 1"), new FlowStepTest("Search Info store 2")).
            when("exists data", msg -> msg.contains("DATA")).
                step("processData", new FlowStepTest("Process data")).
            whenElse("NOT exists data", msg -> !msg.contains("DATA")).
                step("notify", new FlowStepTest("Notify data not found"))
            .whenEnd();

    p.run("DATA");
```


