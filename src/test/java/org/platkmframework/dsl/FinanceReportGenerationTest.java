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
 class FinanceReportGenerationTest {


    FlowDefinitionRunner<Report> fdRunner;

    @BeforeAll
    void  init(){

        List<FlowStep<Report>> list  = List.of(
                new  FetchSales(),
                new FetchExpenses(),
                new FetchTaxes(),
                new MergeReportData(),
                new GeneratePDF(),
                new SendReportEmail());

        FlowDefinitionContext<Report> context = new FlowDefinitionContext<>(list);
         fdRunner = FlowDefinitionBuilder.
                builder(context).
                    parallel(FetchSales.class, FetchExpenses.class, FetchTaxes.class).
                    step(MergeReportData.class).
                    step(GeneratePDF.class).
                    step(SendReportEmail.class).
                build();

    }


    @Test
    void financeReport(){

        Report report = new Report();
        fdRunner.run(report);

        Assertions.assertThat(report.fetchSales).isTrue();
        Assertions.assertThat(report.fetchExpenses).isTrue();
        Assertions.assertThat(report.fetchTaxes).isTrue();
        Assertions.assertThat(report.mergeReportData).isTrue();
        Assertions.assertThat(report.pdfGenerated).isTrue();
        Assertions.assertThat(report.reportEmailSent).isTrue();
    }

    static class Report {
        public boolean fetchSales;
        public boolean fetchExpenses;
        public boolean fetchTaxes;
        public boolean mergeReportData;
        public boolean pdfGenerated;
        public boolean reportEmailSent;

    }

    static class FetchSales implements FlowStep<Report> {
        @Override
        public void process(Report report) {
            report.fetchSales = Boolean.TRUE;
        }
    }

    static class FetchExpenses implements FlowStep<Report> {
        @Override
        public void process(Report report) {
            report.fetchExpenses = Boolean.TRUE;
        }
    }

    static class FetchTaxes implements FlowStep<Report> {
        @Override
        public void process(Report report) {
            report.fetchTaxes = Boolean.TRUE;
        }
    }

    static class MergeReportData implements FlowStep<Report> {
        @Override
        public void process(Report report) {
            report.mergeReportData = Boolean.TRUE;
        }
    }

    static class GeneratePDF implements FlowStep<Report> {
        @Override
        public void process(Report report) {
            report.pdfGenerated = Boolean.TRUE;
        }
    }

    static class SendReportEmail implements FlowStep<Report> {
        @Override
        public void process(Report report) {
            report.reportEmailSent = Boolean.TRUE;
        }
    }

}
