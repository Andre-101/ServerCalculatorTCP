package org.example.core.processor;

import org.example.core.exceptions.InvalidOperationException;
import org.example.core.models.Operation;
import org.example.core.models.Operator;

public class ValidatorOperations {

    public static Operation buildOperation(String message, Operator operator) {

        if (message==null || message.isEmpty())
            throw new UnsupportedOperationException("The message is empty");

        String[] split = message.substring(1).split("\\"+operator.getSymbol());

        if (split.length != 2)
            throw new InvalidOperationException("There must be two operands and just one operator in the middle of the message: " + message);

        split[0] = message.charAt(0)+split[0];

        try {
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            return new Operation(a, b, operator);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException("They must be integers or just one operator in the middle of the message: " + message);
        }

    }
}
