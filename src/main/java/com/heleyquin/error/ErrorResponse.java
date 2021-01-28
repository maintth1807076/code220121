package com.heleyquin.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(int code) {
        this.code = "ERR_" + code;
    }
}
