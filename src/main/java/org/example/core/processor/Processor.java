package org.example.core.processor;

public interface Processor {
    boolean applyTo(String message);
    String process(String message);
}
