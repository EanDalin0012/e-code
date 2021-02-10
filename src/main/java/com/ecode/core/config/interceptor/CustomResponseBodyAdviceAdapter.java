package com.ecode.core.config.interceptor;

import com.ecode.core.constant.KeyCode;
import com.ecode.core.encryption.EASEncrpter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseBodyAdviceAdapter.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("\n");
        log.info("=== Start Custom Response Body Advice Adapter ===");
        //body = customizeBeforeBodyWrite(body, request, response);
        log.info("=== End Custom Response Body Advice Adapter ===");
        log.info("\n");
        return  body;
    }

    private Object customizeBeforeBodyWrite(Object body,ServerHttpRequest request, ServerHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataBody = new LinkedHashMap<>();
        if (request instanceof ServletServerHttpRequest &&
                response instanceof ServletServerHttpResponse) {
            try {
                String secretKey = KeyCode.keyCode;
                String encodedBase64Key = EASEncrpter.encodeKey(secretKey);
                String rawData = objectMapper.writeValueAsString(body);
                String data = EASEncrpter.encrypt(rawData, encodedBase64Key);
                // new response data
                log.info("=== original data:"+objectMapper.writeValueAsString(body));

                dataBody.put("body", data);
                log.info("=== add object map field body:"+objectMapper.writeValueAsString(dataBody));
                log.info("=== End ResponseBodyAdvice ===\n");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataBody;
    }
}
