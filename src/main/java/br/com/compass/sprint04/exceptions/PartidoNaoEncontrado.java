package br.com.compass.sprint04.exceptions;

public class PartidoNaoEncontrado extends RuntimeException {

    public PartidoNaoEncontrado(String message) {
        super(message);
    }

    public PartidoNaoEncontrado() {
    }
}
