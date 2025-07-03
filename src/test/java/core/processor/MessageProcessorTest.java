package core.processor;

import org.example.core.processor.MessageProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Tests for MessageProcessing")
class MessageProcessorTest {

    private final MessageProcessor processor = new MessageProcessor();

    @Test
    @DisplayName("Must process addition correctly: 2+3 → 5")
    void processSum() {
        // Arrange
        String expression = "2+3";

        // Act
        String result = processor.process(expression);

        // Assert
        assertEquals("5", result);
    }

    @Test
    @DisplayName("Must process subtraction correctly: 9-3 → 6")
    void processSubtraction() {
        // Arrange
        String expression = "9-3";

        // Act
        String result = processor.process(expression);

        // Assert
        assertEquals("6", result);
    }

    @Test
    @DisplayName("Must process multiplication correctly: 3*4 → 12")
    void processMultiplication() {
        // Arrange
        String expression = "3*4";

        // Act
        String result = processor.process(expression);

        // Assert
        assertEquals("12", result);
    }

    @Test
    @DisplayName("Must process division correctly: 3*4 → 12")
    void processDivision() {
        // Arrange
        String expression = "12/6";

        // Act
        String result = processor.process(expression);

        // Assert
        assertEquals("2", result);
    }

}