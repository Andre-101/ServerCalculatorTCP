package org.example.core.processor.operations;

import org.example.core.models.Operation;
import org.example.core.models.Operator;
import org.example.core.processor.Processor;
import org.example.core.processor.ValidatorOperations;

public class DivisionProcessor implements Processor {

    @Override
    public boolean applyTo(String message) {
        return message.length() > 1 && message.substring(1).contains(Operator.DIVISION.getSymbol());
    }

    @Override
    public String process(String message) {
        Operation op = ValidatorOperations.buildOperation(message, Operator.DIVISION);
        return String.valueOf(op.a() / op.b());
    }
}
