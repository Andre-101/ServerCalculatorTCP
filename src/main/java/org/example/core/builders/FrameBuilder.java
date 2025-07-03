package org.example.core.builders;

import org.example.core.exceptions.ProtocolException;
import org.example.core.models.Frame;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FrameBuilder {

    private static final int MAX_PAYLOAD = 8;

    public static Frame create(String ascii) throws ProtocolException {
        if (ascii == null || ascii.isEmpty()) {
            throw new ProtocolException("The message is empty");
        }

        byte[] data = ascii.getBytes(StandardCharsets.US_ASCII);

        if (data.length > MAX_PAYLOAD) {
            throw new ProtocolException("The message is larger than 8 bytes");
        }

        byte size = (byte) data.length;
        short checksum = calculateChecksum(data);

        return new Frame(size, data, checksum);
    }

    public static Frame create(byte[] packet) throws ProtocolException {
        if (packet == null || packet.length < 3) {
            throw new ProtocolException("Packet too short: " + (packet != null? new String(packet, StandardCharsets.UTF_8) : ""));
        }

        byte size = packet[0];
        if (size < 0 || size > MAX_PAYLOAD) {
            throw new ProtocolException("Packet out of bounds: " + size);
        }

        int totalExpected = 1 + size + 2;
        if (packet.length < totalExpected) {
            throw new ProtocolException("Packet incomplete: (expected " + totalExpected + " bytes) / (received " + packet.length + "bytes)");
        }

        byte[] data = Arrays.copyOfRange(packet, 1, 1 + size);

        short checksumReceived = (short) (((packet[packet.length - 2] & 0xFF) << 8) | (packet[packet.length - 1] & 0xFF));
        short checksumCalculated = calculateChecksum(data);

        if (checksumReceived != checksumCalculated) {
            throw new ProtocolException("Invalid checksum (expected: " + checksumCalculated + ", received: " + checksumReceived + ")");
        }

        return new Frame(size, data, checksumReceived);
    }

    public static Frame createFromHex(String messageHex) throws ProtocolException {
        messageHex = messageHex.replaceAll("\\s+", "").toUpperCase();

        if (messageHex.length() < 4) {
            throw new ProtocolException("Packet too short: " + messageHex);
        }

        byte size = (byte) Integer.parseInt(messageHex.substring(0, 2), 16);

        int dataExpected = size * 2;
        int endData = 2 + dataExpected;

        if (messageHex.length() < endData + 4) {
            throw new ProtocolException("Packet incomplete: " + messageHex);
        }

        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = (byte) Integer.parseInt(messageHex.substring(2 + i * 2, 4 + i * 2), 16);
        }

        short checksumReceived = (short) Integer.parseInt(messageHex.substring(endData, endData + 4), 16);
        short checksumCalculated = calculateChecksum(data);

        if (checksumReceived != checksumCalculated) {
            throw new ProtocolException("Invalid checksum (expected: " + checksumCalculated + ", received: " + checksumReceived + ")");
        }

        return new Frame(size, data, checksumCalculated);
    }

    public static String getText(Frame frame) {
        return new String(frame.data(), StandardCharsets.US_ASCII);
    }

    private static short calculateChecksum(byte[] data) {
        int sum = (data.length & 0xFF);
        for (byte b : data) {
            sum += (b & 0xFF);
        }
        return (short) (sum & 0xFFFF);
    }
}
