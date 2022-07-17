package br.com.compass.sprint04.exceptionsHandlers;

import br.com.compass.sprint04.dto.response.ExceptionResponseDTO;
import br.com.compass.sprint04.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandlers {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponseDTO>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        List<ExceptionResponseDTO> responseDTOList = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionResponseDTO error = new ExceptionResponseDTO(e.getField(), message);
            responseDTOList.add(error);
        });
        return ResponseEntity.badRequest().body(responseDTOList);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(PartidoNaoEncontrado.class)
    public ResponseEntity<ExceptionResponseDTO> handlerPartidoNaoEncontrado(PartidoNaoEncontrado exception) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Partido nao encontrado", "Partido");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);

    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AssociadoNaoEncontrado.class)
    public ResponseEntity<ExceptionResponseDTO> handlerAssociadoNaoEncontrado(AssociadoNaoEncontrado exception) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Associado nao encontrado", "Associado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SexoInvalido.class)
    public ResponseEntity<ExceptionResponseDTO> handlerSexoInvalido(SexoInvalido exception) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Sexo apenas Masculino ou Feminino", "Sexo");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataInvalida.class)
    public ResponseEntity<ExceptionResponseDTO> handlerDataInvalida(DataInvalida exception) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Data invalida", "Data");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CargoPoliticoInvalido.class)
    public ResponseEntity<ExceptionResponseDTO> handlerCargoPoliticoInvalido(CargoPoliticoInvalido exception) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Cargo politico invalido", "cargoPolitico");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdeologiaInvalida.class)
    public ResponseEntity<ExceptionResponseDTO> handlerIdeologiaInvalida(IdeologiaInvalida exception) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Ideologia invalida", "ideologia");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }



    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ExceptionResponseDTO> handleUniqueError(PersistenceException exception) {
        String message = exception.getMessage();
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(message, "valor duplicado em: Sigla ou Nome do partido");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }


}
