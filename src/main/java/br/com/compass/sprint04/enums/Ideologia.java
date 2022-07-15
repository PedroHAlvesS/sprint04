package br.com.compass.sprint04.enums;

public enum Ideologia {
    DIREITA("Direita"), CENTRO("Centro"), ESQUERDA("Esquerda");

    private final String ideologiaNome;
    Ideologia(String ideologiaNome) {
        this.ideologiaNome = ideologiaNome;
    }

    public String retornaNomeDaIdeologia() {
        return this.ideologiaNome;
    }
}
