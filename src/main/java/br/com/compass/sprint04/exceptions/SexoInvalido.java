package br.com.compass.sprint04.exceptions;

public class SexoInvalido extends RuntimeException{
    public SexoInvalido(String message) {
        super(message);
    }

    public SexoInvalido() {
    }
}
