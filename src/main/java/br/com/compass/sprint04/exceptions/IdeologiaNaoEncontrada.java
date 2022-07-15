package br.com.compass.sprint04.exceptions;

public class IdeologiaNaoEncontrada extends RuntimeException {

    public IdeologiaNaoEncontrada(String message) {
        super(message);
    }

    public IdeologiaNaoEncontrada() {
    }
}
