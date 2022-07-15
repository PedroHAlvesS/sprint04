package br.com.compass.sprint04.util;

import br.com.compass.sprint04.enums.CargoPolitico;
import br.com.compass.sprint04.enums.Sexo;
import br.com.compass.sprint04.exceptions.CargoPoliticoNaoEncontrado;
import br.com.compass.sprint04.exceptions.SexoNaoEncontrado;
import org.springframework.stereotype.Component;

@Component
public class AssociadoValidacao {

    public String validaCargoPolitico(String cargoPolitico) {
        try {
            String cargoPoliticoValidado = CargoPolitico.valueOf(cargoPolitico.toUpperCase()).retornaNomeDoCargo();
            return cargoPoliticoValidado;
        } catch (IllegalArgumentException e) {
            throw new CargoPoliticoNaoEncontrado("Cargo nao foi encontrado");
        }
    }

    public String validaSexo(String sexo) {
        try {
            String sexoValidado = Sexo.valueOf(sexo.toUpperCase()).retornaSexo();
            return sexoValidado;
        } catch (IllegalArgumentException e) {
            throw new SexoNaoEncontrado("Sexo nao encontrado");
        }
    }
}
