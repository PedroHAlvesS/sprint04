package br.com.compass.sprint04.util;

import br.com.compass.sprint04.exceptions.DataInvalida;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class ConverteDatas {
    public LocalDate formataDataISO8601(String data) {
        try {
            DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(data, formatoBrasileiro);
        } catch (Exception e) {
            throw new DataInvalida();
        }
    }

    public String formataDataBrasileira(LocalDate dataFormatoISO) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        return dataFormatoISO.format(formatoBrasileiro);
    }
}
