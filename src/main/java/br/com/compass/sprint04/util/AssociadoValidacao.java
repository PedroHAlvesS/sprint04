package br.com.compass.sprint04.util;

import br.com.compass.sprint04.enums.CargoPolitico;
import br.com.compass.sprint04.enums.Sexo;
import br.com.compass.sprint04.exceptions.CargoPoliticoInvalido;
import br.com.compass.sprint04.exceptions.SexoInvalido;
import org.springframework.stereotype.Component;

@Component
public class AssociadoValidacao {

    public String validaCargoPolitico(String cargoPolitico) {
        try {
            String cargoPoliticoValidado = CargoPolitico.valueOf(cargoPolitico.toUpperCase()).retornaNomeDoCargo();
            return cargoPoliticoValidado;
        } catch (IllegalArgumentException e) {
            throw new CargoPoliticoInvalido();
        }
    }

    public String validaSexo(String sexo) {
        try {
            String sexoValidado = Sexo.valueOf(sexo.toUpperCase()).retornaSexo();
            return sexoValidado;
        } catch (IllegalArgumentException e) {
            throw new SexoInvalido();
        }
    }
}
