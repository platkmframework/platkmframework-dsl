# cplatkm-dsl

Es un DSL (Domain-Specific Language) personalizado en Java para definir flujos de procesos de forma clara, flexible y escalable.
🚀 Inspirado en DSLs como Apache Camel y Spring Integration, este lenguaje es ideal para diseñar flujos inteligentes, adaptar decisiones sobre la marcha y mantener procesos limpios y mantenibles.

Este lenguaje de dominio específico permite construir 
-    secuencias lógicas con pasos condicionales,
-    ramificaciones y control de flujo,
-    todo con una sintaxis fluida y legible.

🔍 ¿Qué resuelve?
CPlatkmDSL simplifica la construcción de procesos complejos:
-    como reglas de negocio, 
-    workflows, 
-    pipelines de datos o decisiones automatizadas sin caer en estructuras rígidas ni lógica dispersa.

🔧 Características clave:

-    Sintaxis fluida e intuitiva: .step(), .choice(), .when(), .otherwise(), .endChoice(), etc.
-    Evaluación condicional dinámica con soporte para anidación.
-    Contexto compartido entre pasos para mantener y modificar el estado.
-    Enfoque extensible y testable, ideal para integrar IA, reglas o procesos batch.
  

```
Bank Customer Registration
List<FlowStep<Client>> list  = List.of(
                new ValidateCustomer(),
                new VerifyIdentity(),
                new SaveCustomer(),
                new NotifyAnalyst(),
                new SendWelcomeEmail(),
                new IssueCard());

 FlowDefinitionContext<Client> context = new FlowDefinitionContext<>(list);
 FlowDefinitionRunner<Client> fdRunner = FlowDefinitionBuilder.
                builder(context).
                    step(ValidateCustomer.class).
                    log("Validate Customer").
                    step(VerifyIdentity.class).
                    log("Verify Identity").
                    when(c -> c.isVerified).
                        step(SaveCustomer.class).log("Save Customer").
                        parallel(SendWelcomeEmail.class, IssueCard.class).
                    whenElse(c -> !c.isVerified).
                        step(NotifyAnalyst.class).log("Notify Analyst").
                    end().
                build();

Client client = new Client();
client.isVerified = true;
fdRunner.run(client);
```

```
List<FlowStep<Credential>> list  = List.of(
                new  SendMfaCode(),
                new ValidateMfaCode(),
                new SkipMfaStep(),
                new LoginSuccess());
FlowDefinitionContext<Credential> context = new FlowDefinitionContext<>(list);
fdRunner = FlowDefinitionBuilder.
        builder(context).
            when(credential -> credential.requiredMFA).
                step(SendMfaCode.class).
                step(ValidateMfaCode.class).
                step(LoginSuccess.class).
            whenElse(credential -> !credential.requiredMFA).
                step(SkipMfaStep.class).
                step(LoginSuccess.class).
            end().build();

Credential credential = new Credential();
credential.requiredMFA = true;
fdRunner.run(credential);
```


