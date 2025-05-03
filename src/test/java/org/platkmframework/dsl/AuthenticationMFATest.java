package org.platkmframework.dsl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.platkmframework.dsl.builder.FlowDefinitionBuilder;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;
import org.platkmframework.dsl.steps.FlowStep;

import java.util.List;

class AuthenticationMFATest {


    @Test
    void authenticationMFATest(){

        List<FlowStep<Credential>> list  = List.of(
                new  SendMfaCode(),
                new ValidateMfaCode(),
                new SkipMfaStep(),
                new LoginSuccess());

        FlowDefinitionContext<Credential> context = new FlowDefinitionContext<>(list);
        FlowDefinitionRunner<Credential> fdRunner = FlowDefinitionBuilder.
                builder(context).
                    when(credential -> credential.requiredMFA).
                        step(SendMfaCode.class).
                        step(ValidateMfaCode.class).
                        step(LoginSuccess.class).
                    whenElse(credential -> credential.requiredMFA).
                        step(LoginSuccess.class).
                    end().
                build();

        Credential credential = new Credential();
        credential.requiredMFA = true;
        fdRunner.run(credential);

        Assertions.assertThat(credential.mfaCodeSent).isTrue();
        Assertions.assertThat(credential.mfaCodeValidated).isTrue();
        Assertions.assertThat(credential.loginSuccess).isTrue();
        Assertions.assertThat(credential.mfaSkip).isFalse();

    }

    static class Credential {
        public boolean requiredMFA;
        public boolean mfaCodeSent;
        public boolean mfaCodeValidated;
        public boolean loginSuccess;
        public boolean mfaSkip;

    }

    static class SendMfaCode implements FlowStep<Credential> {
        @Override
        public void process(Credential credential) {
            credential.mfaCodeSent = Boolean.TRUE;
        }
    }

    static class ValidateMfaCode implements FlowStep<Credential> {
        @Override
        public void process(Credential credential) {
            credential.mfaCodeValidated = Boolean.TRUE;
        }
    }

    static class SkipMfaStep implements FlowStep<Credential> {
        @Override
        public void process(Credential credential) {
            credential.mfaSkip = Boolean.TRUE;
        }
    }

    static class LoginSuccess implements FlowStep<Credential> {
        @Override
        public void process(Credential credential) {
            credential.loginSuccess = Boolean.TRUE;
        }
    }

}
