package org.example.core.processor;

import org.example.core.processor.operations.DivisionProcessor;
import org.example.core.processor.operations.MultiplicationProcessor;
import org.example.core.processor.operations.SubtractionProcessor;
import org.example.core.processor.operations.SumProcessor;

import java.util.List;

public class MessageProcessor {

    private final List<Processor> strategies;

    public MessageProcessor() {
        strategies = List.of(
                new SumProcessor(),
                new SubtractionProcessor(),
                new MultiplicationProcessor(),
                new DivisionProcessor()
        );
    }

    public String process(String message) {
        String finalMessage = message.trim().replaceAll("\\s+","");
        return strategies.stream().filter(p -> p.applyTo(finalMessage))
                .findFirst().orElseThrow(()-> new IllegalArgumentException("unsupported operation"))
                .process(finalMessage);
    }
}
