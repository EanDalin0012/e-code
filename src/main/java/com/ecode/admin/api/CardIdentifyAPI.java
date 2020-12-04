package com.ecode.admin.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecode.admin.constant.StatusYN;
import com.ecode.admin.service.implement.CardIdentifyServiceImplement;
import com.ecode.admin.util.MessageUtil;
import com.ecode.core.constant.ErrorCode;
import com.ecode.core.dto.ErrorMessage;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.template.ResponseData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/card-identify/v1")
public class CardIdentifyAPI {
	private static final Logger log = LoggerFactory.getLogger(CardIdentifyAPI.class);

    @Autowired
    private CardIdentifyServiceImplement cardIdentifyService;
    
    @PostMapping(value = "/id")
    public ResponseData<MMap> retrieveCardIdentifyById(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MMap> responseData = new ResponseData<>();
        MMap out = new MMap();

        try {
            MMap body = param.getMMap("body");
            MMap input = new MMap();
            input.setString("card_id", body.getString("card_id"));
            MMap data = cardIdentifyService.retrieveCardIdentifyById(input);
            log.info("Retrieve Card Identify By Id Data:", data);
            responseData.setBody(data);
            out.setString("status", StatusYN.Y);
            return responseData;
        } catch (ValidatorException ex) {
            log.error("get error api /api/card-identify", ex);
            ErrorMessage message = MessageUtil.message("card_identify_"+ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        }
        catch (Exception e) {
            log.error("get error api /api/card-identify", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }

    }
}
