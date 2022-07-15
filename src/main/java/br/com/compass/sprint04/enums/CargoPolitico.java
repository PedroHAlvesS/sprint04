package br.com.compass.sprint04.enums;

public enum CargoPolitico {
    VEREADOR("Vereador"), PREFEITO("Prefeito"), DEPUTADO_ESTADUAL("Deputado Estadual"),
    DEPUTADO_FEDERAL("Deputado Federal"), SENADOR("Senador"), GOVERNADOR("Governador"),
    PRESIDENTE("Presidente"), NENHUM("Nenhum");

    private final String nomeDoCargo;

    CargoPolitico(String nomeDoCargo) {
        this.nomeDoCargo = nomeDoCargo;
    }

    public String retornaNomeDoCargo() {
        return this.nomeDoCargo;
    }
}
