package br.com.compass.sprint04.exceptions;

public class CargoPoliticoInvalido extends RuntimeException{
    public CargoPoliticoInvalido(String message) {
        super(message);
    }

    public CargoPoliticoInvalido() {
    }
}
