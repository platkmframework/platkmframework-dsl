package org.platkmframework.dsl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.platkmframework.dsl.builder.FlowDefinitionBuilder;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;
import org.platkmframework.dsl.steps.FlowStep;

import java.util.List;

class BackClientTest {

    @Test
    void validateCustomer(){

        List<FlowStep<Client>> list  = List.of(
                new VerifyIdentity(),
                new SaveCustomer(),
                new NotifyAnalyst(),
                new SendWelcomeEmail(),
                new IssueCard());

        FlowDefinitionContext<Client> context = new FlowDefinitionContext<>(list);
        FlowDefinitionRunner<Client> fdRunner = FlowDefinitionBuilder.
                builder(context).
                step(VerifyIdentity.class).log("VerifyIdentity").
                when(client-> client.verified).
                    step(SaveCustomer.class).log("SaveCustomer").
                whenElse(client -> !client.verified).
                    step(NotifyAnalyst.class).log("NotifyAnalyst").
                end().parallel(SendWelcomeEmail.class, IssueCard.class).log("Parallel send").
                build();

        Client client = new Client();
        fdRunner.run(client);

        Assertions.assertThat(client.verified).isTrue();
        Assertions.assertThat(client.analystNotified).isFalse();
        Assertions.assertThat(client.customerSaved).isTrue();
        Assertions.assertThat(client.emailSent).isTrue();
        Assertions.assertThat(client.cardIssued).isTrue();

    }


    static class Client {
        public boolean verified;
        public boolean customerSaved;
        public boolean analystNotified;
        public boolean emailSent;
        public boolean cardIssued;

    }

    static class VerifyIdentity implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.verified = Boolean.TRUE;
        }
    }

    static class SaveCustomer implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.customerSaved = Boolean.TRUE;
        }
    }

    static class NotifyAnalyst implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.analystNotified = Boolean.TRUE;
        }
    }

    static class SendWelcomeEmail implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.emailSent = Boolean.TRUE;
        }
    }

    static class IssueCard implements FlowStep<Client> {
        @Override
        public void process(Client client) {
            client.cardIssued = Boolean.TRUE;
        }
    }
}
