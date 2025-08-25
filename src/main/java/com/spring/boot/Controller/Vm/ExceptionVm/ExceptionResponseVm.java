package com.spring.boot.Controller.Vm.ExceptionVm;
import com.spring.boot.BundleMessage.BundleMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(name = "ExceptionResponseVm", description = "View Model for Exception Response")
public class ExceptionResponseVm {
    @Schema(description = "List of error or validation messages")
    private List<BundleMessage> messages;
    @Schema(description = "HTTP status of the response", example = "BAD_REQUEST")
    private HttpStatus httpStatus;
    @Schema(description = "Date of the exception response", example = "2025-08-14")
    private LocalDate localDate;

    public ExceptionResponseVm(List<BundleMessage> messages, HttpStatus httpStatus) {
        this.messages = messages;
        this.httpStatus = httpStatus;
        this.localDate = LocalDate.now();

    }


}
