package com.ecode.core.config.interceptor;
import com.ecode.core.constant.KeyCode;
import com.ecode.core.encryption.EASEncrpter;
import com.ecode.core.map.MMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    private static final Logger log = LoggerFactory.getLogger(CustomRequestBodyAdviceAdapter.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        log.info("\n");
        log.info("=== Start CustomRequestBodyAdviceAdapter ===");
        body = customizeAfterBodyRead(body);
        log.info("=== Start CustomRequestBodyAdviceAdapter ===");
        log.info("\n");

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    public Object customizeAfterBodyRead(Object body) {
        Map<String, Object> dataBody = new LinkedHashMap<>();
        String decrStr = null;
        MMap data1 = new MMap();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String encodedBase64Key = EASEncrpter.encodeKey(KeyCode.keyCode);;
            Map<String, Object> getDataBody = (Map<String, Object>) body;

            log.info("=== getDataBody:"+objectMapper.writeValueAsString(getDataBody));
            String encrypt = getDataBody.get("body").toString();
            log.info("=== data encrypt:"+encrypt);

            decrStr = EASEncrpter.decrypt(encrypt, encodedBase64Key);
            log.info("=== data decrypt:"+decrStr);
            MMap d = new MMap();
            d.set("name", "fafdfsdfsd");
            d.set("description","description");
            data1.setMMap("body", d);
            dataBody = objectMapper.readValue(decrStr,Map.class);

            log.info("=== End RequestBodyAdviceAdapter ===\n");
        return data1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dataBody;
    }
}

