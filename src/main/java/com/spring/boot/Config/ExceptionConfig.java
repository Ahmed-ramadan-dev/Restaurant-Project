package com.spring.boot.Config;
import com.spring.boot.BundleMessage.BundleMessage;
import com.spring.boot.BundleMessage.BundleTranslationService;
import com.spring.boot.Controller.Vm.ExceptionVm.ExceptionResponseVm;
import com.spring.boot.Exception.SystemException;  // ✅ صح
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionConfig {
//لو عايز ابعت اللغه في header
    /*
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ExceptionResponseVm2> handleSystemException(SystemException exception) {
        String message = BundleTranslationService.getBundleMessage(exception.getMessage());
        List<String> messages = List.of(message);
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ExceptionResponseVm2(messages, exception.getStatus()));
    }
}*/

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ExceptionResponseVm> handleSystemException(SystemException exception) {
        // بنجيب الرسالة من البندل باللغتين
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageInEnglishAndArabic(exception.getMessage());

        // بنضيفها في ليست
        List<BundleMessage> messages = List.of(bundleMessage);

        // نرجع ال response
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ExceptionResponseVm(messages, exception.getStatus()));    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseVm> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // تحويل كل رسالة خطأ إلى BundleMessage (عربي وإنجليزي)
        List<BundleMessage> errors = fieldErrors.stream()
                .map(fieldError -> BundleTranslationService.getBundleMessageInEnglishAndArabic(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ExceptionResponseVm response = new ExceptionResponseVm(errors, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseVm> handleConstraintViolationException(ConstraintViolationException ex) {

        List<BundleMessage> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String messageKey = violation.getMessage();  // رسالة المفتاح
                    // استدعاء خدمة الترجمة (مثال)
                    return BundleTranslationService.getBundleMessageInEnglishAndArabic(messageKey);
                })
                .collect(Collectors.toList());

        ExceptionResponseVm response = new ExceptionResponseVm(errors, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(response);
    }


}
