package br.com.compass.sprint04.exceptions;

public class DataInvalida extends RuntimeException {
    public DataInvalida(String message) {
        super(message);
    }

    public DataInvalida() {
    }
}
