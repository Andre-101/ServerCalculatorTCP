package org.example.core.models;

import java.util.Arrays;

public record Frame(byte size, byte[] data, short checksum) {

    @Override
    public byte[] data() {
        return Arrays.copyOf(data, data.length);
    }

    public byte[] toByteArray() {
        byte[] packet = new byte[1 + data.length + 2];
        packet[0] = size;
        System.arraycopy(data, 0, packet, 1, data.length);
        packet[packet.length - 2] = (byte) ((checksum >> 8) & 0xFF);
        packet[packet.length - 1] = (byte) (checksum & 0xFF);
        return packet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02X ", size));
        for (byte b : data) sb.append(String.format("%02X ", b));
        sb.append(String.format("%02X ", (checksum >> 8) & 0xFF));
        sb.append(String.format("%02X", checksum & 0xFF));
        return sb.toString().trim();
    }
}
