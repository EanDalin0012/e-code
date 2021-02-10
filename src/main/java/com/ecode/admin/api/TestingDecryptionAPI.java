package com.ecode.admin.api;

import com.ecode.admin.service.TestingDecryptionService;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;
import com.ecode.core.template.ResponseData;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/decrypt/v1")
public class TestingDecryptionAPI {
    private static final Logger log = LoggerFactory.getLogger(TestingDecryptionAPI.class);
    @Autowired
    private TestingDecryptionService testingDecryptionService;
    @PostMapping(value = "/index")
    public ResponseData<MultiMap> index(@RequestBody MMap dataBody) {
        ResponseData<MultiMap> responseData = new ResponseData<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MMap input = new MMap();
            String id = UUID.randomUUID().toString();
            input.setString("id", id);
            input.setString("name", (String) dataBody.get("name"));

            log.info("TestingDescryptionAPI"+objectMapper.writeValueAsString(dataBody));
            int save = testingDecryptionService.save(input);
            if (save > 0) {
                MultiMap data = testingDecryptionService.retrieve();
                responseData.setBody(data);
                return responseData;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
