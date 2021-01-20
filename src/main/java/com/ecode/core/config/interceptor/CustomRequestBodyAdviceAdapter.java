package com.ecode.core.config.interceptor;
import com.ecode.core.constant.KeyCode;
import com.ecode.core.encryption.EASEncrpter;
import com.ecode.core.map.MMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import com.google.gson.Gson;
import java.io.DataInput;
import java.lang.reflect.Type;
import java.util.HashMap;
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
        MMap dataBody = new MMap();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String encodedBase64Key = EASEncrpter.encodeKey(KeyCode.keyCode);;
            MMap getDataBody = (MMap) body;
            log.info("=== Data From Client Side :"+objectMapper.writeValueAsString(getDataBody));
            String encrypt = getDataBody.get("body").toString();
            log.info("===Encryption Data:"+encrypt);
            String decrStr = EASEncrpter.decrypt(encrypt, encodedBase64Key);
            log.info("=== Decryption Data :"+decrStr);
            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(decrStr, Map.class);
            MMap input = new MMap();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String k = entry.getKey();
                String v = (String) entry.getValue();
                input.put(k,entry.getValue());
            }
            dataBody.setMMap("body", input);
            log.info("=== Return Data To Controller :"+objectMapper.writeValueAsString(dataBody));
            log.info("=== End RequestBodyAdviceAdapter ===\n");
            return dataBody;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dataBody;
    }

}

