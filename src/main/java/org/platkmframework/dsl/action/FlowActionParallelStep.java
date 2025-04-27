package org.platkmframework.dsl.action;

import org.platkmframework.dsl.FlowStep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FlowActionParallelStep<T>  extends FlowAction<T> {

    private final FlowStep<T>[] steps;

    public FlowActionParallelStep(FlowStep<T>[] steps) {
        super();
        this.steps = steps;
    }

    public FlowActionParallelStep(String id, String label,  FlowStep<T>[] steps) {
        super(id, label);
        this.steps = steps;
    }

    @Override
    public boolean process(T data) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (FlowStep<T> step : steps) {
            futures.add(CompletableFuture.runAsync(() -> step.process(data)));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return true;
    }
}
