package core.builders;

import org.example.core.builders.FrameBuilder;
import org.example.core.models.Frame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for FrameBuilder")
class FrameBuilderTest {

    @Test
    @DisplayName("Must successfully get size a Frame")
    void sizeFrame() {
        // Arrange
        String text = "ABCD";

        // Act
        Frame frame = FrameBuilder.create(text);

        // Assert
        assertEquals(4, frame.size());
    }

    @Test
    @DisplayName("Must get the text correctly from a Frame")
    void getTextFrame() {
        // Arrange
        Frame frame = FrameBuilder.create("ABCD");

        // Act
        String text = FrameBuilder.getText(frame);

        // Assert
        assertEquals("ABCD", text);
    }

    @Test
    @DisplayName("Must calculate the valid checksum of a frame")
    void ValidChecksum() {
        // Arrange
        String data = "ABCD";
        short checksum = calculateChecksum(data.length(),data);

        // Act
        Frame frame = FrameBuilder.create(data);

        // Assert
        assertEquals(checksum, frame.checksum());
    }

    private short calculateChecksum(int size, String data) {
        byte[] bytes = data.getBytes();
        int sum = size;
        for (byte b : bytes) sum += (b & 0xFF);
        return (short) sum;
    }
}