package org.platkmframework.dsl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.platkmframework.dsl.builder.FlowDefinitionBuilder;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;
import org.platkmframework.dsl.steps.FlowStep;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankCustomerRegistrationTest {

    FlowDefinitionRunner<Client> fdRunner;

    @BeforeAll
    void  init(){
        List<FlowStep<Client>> list  = List.of(
                new ValidateCustomer(),
                new VerifyIdentity(),
                new SaveCustomer(),
                new NotifyAnalyst(),
                new SendWelcomeEmail(),
                new IssueCard());

        FlowDefinitionContext<Client> context = new FlowDefinitionContext<>(list);
        fdRunner = FlowDefinitionBuilder.
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
    }


    @Test
    void validateCustomerVerified(){
        Client client = new Client();
        client.isVerified = true;
        fdRunner.run(client);

        Assertions.assertThat(client.validateCustomer).isTrue();
        Assertions.assertThat(client.verifyIdentity).isTrue();
        Assertions.assertThat(client.saveCustomer).isTrue();
        Assertions.assertThat(client.sendWelcomeEmail).isTrue();
        Assertions.assertThat(client.issueCard).isTrue();
        Assertions.assertThat(client.notifyAnalyst).isFalse();

    }

    @Test
    void validateCustomerNotVerified(){
        Client client = new Client();
        fdRunner.run(client);

        Assertions.assertThat(client.validateCustomer).isTrue();
        Assertions.assertThat(client.verifyIdentity).isTrue();
        Assertions.assertThat(client.saveCustomer).isFalse();
        Assertions.assertThat(client.sendWelcomeEmail).isFalse();
        Assertions.assertThat(client.issueCard).isFalse();
        Assertions.assertThat(client.notifyAnalyst).isTrue();

    }


    static class Client {
        public boolean validateCustomer;
        public boolean verifyIdentity;
        public boolean isVerified;
        public boolean saveCustomer;
        public boolean notifyAnalyst;
        public boolean sendWelcomeEmail;
        public boolean issueCard;
    }

    static class ValidateCustomer implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.validateCustomer = Boolean.TRUE;
        }
    }

    static class VerifyIdentity implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.verifyIdentity = Boolean.TRUE;
        }
    }

    static class SaveCustomer implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.saveCustomer = Boolean.TRUE;
        }
    }

    static class NotifyAnalyst implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.notifyAnalyst = Boolean.TRUE;
        }
    }

    static class SendWelcomeEmail implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.sendWelcomeEmail = Boolean.TRUE;
        }
    }

    static class IssueCard implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.issueCard = Boolean.TRUE;
        }
    }
}
