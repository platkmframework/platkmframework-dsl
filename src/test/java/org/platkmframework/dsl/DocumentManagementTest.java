package org.platkmframework.dsl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.platkmframework.dsl.builder.FlowDefinitionBuilder;
import org.platkmframework.dsl.context.FlowDefinitionContext;
import org.platkmframework.dsl.runner.FlowDefinitionRunner;
import org.platkmframework.dsl.steps.FlowStep;

import java.util.List;

class DocumentManagementTest {

    @Test
    void documentManagementTest(){

        List<FlowStep<Document>> list  = List.of(
                new ValidateFormat(),
                new ORCExtraction(),
                new ValidateMetaData(),
                new SaveToDB(),
                new NotifyOperator(),
                new MoveToReviewFolder(),
                new Index(),
                new GeneratePreview(),
                new FinalizeProcessing()
                );

        FlowDefinitionContext<Document> context = new FlowDefinitionContext<>(list);
        FlowDefinitionRunner<Document> fdRunner = FlowDefinitionBuilder.
                builder(context).
                step(ValidateFormat.class).
                step(ORCExtraction.class).
                step(ValidateMetaData.class).
                when(document -> document.isValid).
                    step(SaveToDB.class).
                    parallel(Index.class, GeneratePreview.class).
                whenElse(document -> !document.isValid).
                    step(NotifyOperator.class).
                    step(MoveToReviewFolder.class).
                    parallel(Index.class, GeneratePreview.class).
                end().
                step(FinalizeProcessing.class).
                build();

        Document document = new Document();
        document.isValid = Boolean.TRUE;
        fdRunner.run(document);

        Assertions.assertThat(document.validateFormat).isTrue();
        Assertions.assertThat(document.ocrExtraction).isTrue();
        Assertions.assertThat(document.validateMetaData).isTrue();
        Assertions.assertThat(document.saveToDB).isTrue();
        Assertions.assertThat(document.index).isTrue();
        Assertions.assertThat(document.generatePreview).isTrue();
        Assertions.assertThat(document.finalizeProcessing).isTrue();
        Assertions.assertThat(document.notifyOperator).isFalse();
        Assertions.assertThat(document.moveToReviewFolder).isFalse();

    }

    static class Document {
        public boolean validateFormat;
        public boolean ocrExtraction;
        public boolean validateMetaData;
        public boolean isValid;
        public boolean saveToDB;
        public boolean notifyOperator;
        public boolean moveToReviewFolder;
        public boolean index;
        public boolean generatePreview;
        public boolean finalizeProcessing;
    }

    static class ValidateFormat implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.validateFormat = Boolean.TRUE;
        }
    }

    static class ORCExtraction implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.ocrExtraction = Boolean.TRUE;
        }
    }

    static class ValidateMetaData implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.validateMetaData = Boolean.TRUE;
        }
    }

    static class SaveToDB implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.saveToDB = Boolean.TRUE;
        }
    }

    static class NotifyOperator implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.notifyOperator = Boolean.TRUE;
        }
    }

    static class MoveToReviewFolder implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.moveToReviewFolder = Boolean.TRUE;
        }
    }

    static class Index implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.index = Boolean.TRUE;
        }
    }

    static class GeneratePreview implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.generatePreview = Boolean.TRUE;
        }
    }

    static class FinalizeProcessing implements FlowStep<Document> {
        @Override
        public void process(Document document) {
            document.finalizeProcessing = Boolean.TRUE;
        }
    }
}
