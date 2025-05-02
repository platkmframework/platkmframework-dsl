package org.platkmframework.dsl.action;

import org.platkmframework.dsl.steps.FlowStep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FlowActionParallelStep<T>  extends FlowAction<T> {

    private final List<FlowStep <T>> steps;

    public FlowActionParallelStep(String id, String label) {
        super(id, label);
        this.steps = new ArrayList<>();
    }

    public void add(FlowStep<T> step) {
        this.steps.add(step);
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
