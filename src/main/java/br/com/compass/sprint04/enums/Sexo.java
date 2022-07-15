package br.com.compass.sprint04.enums;

public enum Sexo {
    MASCULINO("Masculino"), FEMININO("Feminino");

    private final String sexo;
    Sexo(String sexo) {
        this.sexo = sexo;
    }

    public String retornaSexo() {
        return this.sexo;
    }
}
