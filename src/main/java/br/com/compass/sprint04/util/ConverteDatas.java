package br.com.compass.sprint04.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class ConverteDatas {
    public LocalDate formataDataISO8601(String data) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate dataFormatada = LocalDate.parse(data, formatoBrasileiro);
        return dataFormatada;
    }

    public String formataDataBrasileira(LocalDate dataFormatoISO) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        String dataBrasileira = dataFormatoISO.format(formatoBrasileiro);
        return dataBrasileira;

    }
}
