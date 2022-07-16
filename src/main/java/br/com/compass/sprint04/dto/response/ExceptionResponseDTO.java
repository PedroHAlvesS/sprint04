package br.com.compass.sprint04.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ExceptionResponseDTO {
    private String message;
    private String type;


    @Override
    public String toString() {
        return "{\"message\":" + "\"" + message + "\"," + "\"type\":" + "\"" + type + "\"}";
    }
}
