package br.com.compass.sprint04.config;

import br.com.compass.sprint04.util.ConverteDatas;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ModelMapperConfig {
    @Autowired
    private ConverteDatas converteDatas;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> stringParaLocalDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String string) {
                return converteDatas.formataDataISO8601(string);
            }
        };

        Converter<LocalDate, String> localDateParaString = new AbstractConverter<LocalDate, String>() {
            @Override
            protected String convert(LocalDate localDate) {
                return converteDatas.formataDataBrasileira(localDate);
            }
        };



        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.createTypeMap(LocalDate.class, String.class);
        modelMapper.addConverter(stringParaLocalDate);
        modelMapper.addConverter(localDateParaString);


        return modelMapper;
    }




}
