package com.kazemi.challenge.challenge.exception;

import com.kazemi.challenge.challenge.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class ExceptionControllerAdvice {
    private final ResourceBundleMessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<BaseResponseDto<Void>> handleBusinessException(HttpServletRequest req, BusinessException e) {
        try {
            String locale = req.getHeader("locale");
            if (StringUtils.isEmpty(locale)) {
                locale = "us"; //todo it must be call from application properties base on the locale
            }
            String message = messageSource.getMessage(e.getErrorType().getMessageKey(), null, Locale.forLanguageTag(locale));
            e.setMessage(message);
            return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(new BaseResponseDto<>(e, message));
        } finally {
            log.info("business exception occurred: ", e);
        }
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<BaseResponseDto<Void>> handleException(Exception e) {
        log.warn("exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponseDto<>(
                BaseResponseDto.ErrorData
                        .builder()
                        .errorCode(1000)
                        .message(e.getMessage())
                        .build()
        ));
    }
}
