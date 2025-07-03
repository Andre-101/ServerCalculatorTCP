package org.example.core.processor.operations;

import org.example.core.models.Operation;
import org.example.core.models.Operator;
import org.example.core.processor.Processor;
import org.example.core.processor.ValidatorOperations;

public class SumProcessor implements Processor {

    @Override
    public boolean applyTo(String message) {
        return message != null && message.trim().length() > 1 && message.trim().substring(1).contains(Operator.SUM.getSymbol());
    }

    @Override
    public String process(String message) {
        Operation op = ValidatorOperations.buildOperation(message, Operator.SUM);
        return String.valueOf(op.a() + op.b());
    }
}
