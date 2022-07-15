package br.com.compass.sprint04.exceptions;

public class AssociadoNaoEncontrado extends RuntimeException {
    public AssociadoNaoEncontrado(String message) {
        super(message);
    }

    public AssociadoNaoEncontrado() {
    }
}
