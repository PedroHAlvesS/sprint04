package br.com.compass.sprint04.util;

import br.com.compass.sprint04.enums.Ideologia;
import br.com.compass.sprint04.exceptions.IdeologiaNaoEncontrada;
import org.springframework.stereotype.Component;

@Component
public class PartidoValidacao {
    public String validaIdeologia(String ideologiaRecebida) {
        try {
            String ideologiaValidada = Ideologia.valueOf(ideologiaRecebida.toUpperCase()).retornaNomeDaIdeologia();
            return ideologiaValidada;
        } catch (IllegalArgumentException e) {
            throw new IdeologiaNaoEncontrada("Ideologia não encontrada");
        }
    }


}
