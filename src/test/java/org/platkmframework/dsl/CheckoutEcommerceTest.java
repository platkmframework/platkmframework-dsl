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
class CheckoutEcommerceTest {

    FlowDefinitionRunner<Payment> fdRunner;

    @BeforeAll
     void  init(){
        List<FlowStep<Payment>> list = List.of(new ValidateCart(),
                new CheckStock(),
                new ReserveInventory(),
                new ProcessPayment(),
                new NotifyOutOfStock(),
                new CancelCart(),
                new GenerateOrder());

        FlowDefinitionContext<Payment> context = new FlowDefinitionContext<>(list);

        fdRunner = FlowDefinitionBuilder.
                builder(context).
                    step(ValidateCart.class).
                    step(CheckStock.class).
                    when(p-> p.isStockAvailable).
                        parallel(ReserveInventory.class, ProcessPayment.class).
                        step(GenerateOrder.class).
                    whenElse(p-> !p.isStockAvailable).
                        step(NotifyOutOfStock.class).
                        step(CancelCart.class).
                    end().
                build();
    }

    @Test
    void checkOutECommerceIsInStock(){

        Payment payment = new Payment();
        payment.isStockAvailable = true;
        fdRunner.run(payment);

        Assertions.assertThat(payment.validateCart).isTrue();
        Assertions.assertThat(payment.checkStock).isTrue();
        Assertions.assertThat(payment.notifyOutOfStock).isFalse();
        Assertions.assertThat(payment.reserveInventory).isTrue();
        Assertions.assertThat(payment.processPayment).isTrue();
        Assertions.assertThat(payment.cancelCart).isFalse();
        Assertions.assertThat(payment.generateOrder).isTrue();

    }

    @Test
    void checkOutECommerceIsInNotStock(){

        Payment payment = new Payment();
        fdRunner.run(payment);

        Assertions.assertThat(payment.validateCart).isTrue();
        Assertions.assertThat(payment.checkStock).isTrue();
        Assertions.assertThat(payment.reserveInventory).isFalse();
        Assertions.assertThat(payment.processPayment).isFalse();
        Assertions.assertThat(payment.generateOrder).isFalse();
        Assertions.assertThat(payment.notifyOutOfStock).isTrue();
        Assertions.assertThat(payment.cancelCart).isTrue();

    }


    static class Payment{
        public boolean validateCart;
        public boolean checkStock;
        public boolean isStockAvailable;
        public boolean reserveInventory;
        public boolean processPayment;
        public boolean generateOrder;
        public boolean notifyOutOfStock;
        public boolean cancelCart;
    }

    static class ValidateCart implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.validateCart = Boolean.TRUE;
        }
    }

    static class CheckStock implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.checkStock = Boolean.TRUE;
        }
    }

    static class ReserveInventory implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.reserveInventory = Boolean.TRUE;
        }
    }

    static class NotifyOutOfStock implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.notifyOutOfStock = Boolean.TRUE;
        }
    }

    static class ProcessPayment implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.processPayment = Boolean.TRUE;
        }
    }

    static class CancelCart implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.cancelCart = Boolean.TRUE;
        }
    }

    static class GenerateOrder implements FlowStep<Payment> {
        @Override
        public void process(Payment payment) {
            payment.generateOrder = Boolean.TRUE;
        }
    }
}
