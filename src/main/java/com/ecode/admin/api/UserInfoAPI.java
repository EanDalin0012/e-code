package com.ecode.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecode.admin.constant.StatusYN;
import com.ecode.admin.dao.UserDetailsDao;
import com.ecode.admin.service.implement.CardIdentifyServiceImplement;
import com.ecode.admin.service.implement.UserInfoServiceImplement;
import com.ecode.admin.service.implement.UserServiceImplement;
import com.ecode.admin.util.MessageUtil;
import com.ecode.admin.util.Uuid;
import com.ecode.core.constant.ErrorCode;
import com.ecode.core.constant.Status;
import com.ecode.core.dto.ErrorMessage;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;
import com.ecode.core.template.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api/user_info/v1")
public class UserInfoAPI {

    private static final Logger log = LoggerFactory.getLogger(UserAPI.class);

    @Autowired
    private CardIdentifyServiceImplement cardIdentifyService;
    @Autowired
    private UserInfoServiceImplement userInfoService;
    @Autowired
    private UserServiceImplement userService;
    @Autowired
    private UserDetailsDao userDetailsDao;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private UserDetailsDao userDetails;

    @GetMapping(value = "/inquiry")
    public ResponseData<MultiMap> index(@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MultiMap> multiMapResponseData = new ResponseData<>();
        try {
            log.info("======== Start retrieve list of user info =======");

            ObjectMapper objectMapper = new ObjectMapper();
            MMap input = new MMap();
            input.setString("status", Status.Delete.getValueStr());
            MultiMap data = userDetails.dataDetails(input);
            multiMapResponseData.setBody(data);

            log.info("========= Response Values:" + objectMapper.writeValueAsString(multiMapResponseData));
            log.info("========= End retrieve list of user info =======");

        } catch (Exception e) {
            log.error("==== error execption:", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            multiMapResponseData.setError(message);
            return multiMapResponseData;
        }
        return multiMapResponseData;
    }

    @PostMapping(value = "/save")
    public ResponseData<MMap> save(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        ResponseData<MMap> responseData = new ResponseData<>();
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        ObjectMapper objectMapper = new ObjectMapper();
        MMap out = new MMap();

        try {
            log.info("======== Start save user info ========");


            MMap body = param.getMMap("body");
            MMap userInfo = body.getMMap("personalInfo");
            MMap cardIdentify = body.getMMap("cardIdentify");
            MMap accountInfo = body.getMMap("accountInfo");

            out.setString(StatusYN.STATUS, StatusYN.N);

            int userSave = 0;
            int cardIdentifySave = 0;
            int accountServiceSave = 0;
            String user_info_id = "";
            String cardIdentifyId = "";
            int accountInfoId = userService.sequence();

            try {
                MMap userInfoInput = new MMap();
                user_info_id = Uuid.randomUUID();
                userInfoInput.setString("id", user_info_id);
                userInfoInput.setString("first_name", userInfo.getString("first_name"));
                userInfoInput.setString("last_name", userInfo.getString("last_name"));
                userInfoInput.setString("gender", userInfo.getString("gender"));
                userInfoInput.setString("date_birth", userInfo.getString("date_birth"));
                userInfoInput.setString("email", userInfo.getString("email"));
                userInfoInput.setString("contact", userInfo.getString("contact"));
                userInfoInput.setString("description", userInfo.getString("description"));
                userInfoInput.setString("profile_id_image", userInfo.getString("profile_id_image"));
                userInfoInput.setInt("user_id", user_id);
                userInfoInput.setString("status", Status.Active.getValueStr());

                log.info("=========== Personal Info Data:" + objectMapper.writeValueAsString(userInfoInput));

                userSave = userInfoService.save(userInfoInput);
            } catch (ValidatorException ex) {
                transactionManager.rollback(transactionStatus);
                ErrorMessage message = MessageUtil.message("user_info_" + ex.getKey(), lang);
                responseData.setError(message);
                log.error("======== error:", ex);
                return responseData;

            }
            try {
                MMap cardIdentifyInput = new MMap();
                cardIdentifyId = Uuid.randomUUID();
                cardIdentifyInput.setString("id", cardIdentifyId);
                cardIdentifyInput.setString("card_id", cardIdentify.getString("card_id"));
                cardIdentifyInput.setString("font_image_id", cardIdentify.getString("font_image_id"));
                cardIdentifyInput.setString("rear_image_id", cardIdentify.getString("rear_image_id"));
                cardIdentifyInput.setString("status", Status.Active.getValueStr());
                cardIdentifyInput.setInt("user_id", user_id);

                log.info("=========== card identify values:" + objectMapper.writeValueAsString(cardIdentifyInput));

                cardIdentifySave = cardIdentifyService.save(cardIdentifyInput);

            } catch (ValidatorException e2) {
                transactionManager.rollback(transactionStatus);
                ErrorMessage message = MessageUtil.message("card_identify_" + e2.getKey(), lang);
                responseData.setError(message);
                return responseData;
            }
            try {
                MMap accountInfoInput = new MMap();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = accountInfo.getString("pw");
                String enCodePasswd = passwordEncoder.encode(password);

                accountInfoInput.setInt("id", accountInfoId);
                accountInfoInput.setString("user_name", accountInfo.getString("user_name"));
                accountInfoInput.setString("passwd", enCodePasswd);
                accountInfoInput.setBoolean("is_first_login", accountInfo.getBoolean("is_first_login"));
                accountInfoInput.setBoolean("enable", accountInfo.getBoolean("enable"));
                accountInfoInput.setBoolean("account_lock", accountInfo.getBoolean("account_lock"));
                accountInfoInput.setBoolean("credential_expired", accountInfo.getBoolean("credential_expired"));
                accountInfoInput.setBoolean("account_expired", accountInfo.getBoolean("account_expired"));
                accountInfoInput.setString("status", Status.Active.getValueStr());
                accountInfoInput.setInt("user_id", user_id);
                accountInfoInput.setInt("user_id", user_id);

                log.info("=========== account info values:" + objectMapper.writeValueAsString(accountInfoInput));

                accountServiceSave = userService.save(accountInfoInput);

            } catch (ValidatorException e3) {
                transactionManager.rollback(transactionStatus);
                log.error("======== error:", e3);
                ErrorMessage message = MessageUtil.message("account_" + e3.getKey(), lang);
                responseData.setError(message);
                return responseData;
            }
            if (userSave > 0 && cardIdentifySave > 0 && accountServiceSave > 0) {
                int save = saveUserDetails(cardIdentifyId, user_info_id, accountInfoId);
                if (save > 0) {
                    transactionManager.commit(transactionStatus);
                }
            } else {
                transactionManager.rollback(transactionStatus);
            }

            log.info("======== Response Values:" + objectMapper.writeValueAsString(responseData));
            log.info("======== End save user info ========");

        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            log.error("========= error execption:", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
        out.setString(StatusYN.STATUS, StatusYN.Y);
        responseData.setBody(out);


        return responseData;
    }

    private int saveUserDetails(String card_identify_id, String user_info_id, int user_id) {
        try {
            log.info("====== Start save user details============");

            MMap input = new MMap();
            input.setString("card_identify_id", card_identify_id);
            input.setString("user_info_id", user_info_id);
            input.setInt("user_id", user_id);

            ObjectMapper objectMapper = new ObjectMapper();
            log.info("======Values:" + objectMapper.writeValueAsString(input));

            int save = userDetailsDao.save(input);

            log.info("====== End save user details============");
            return save;
        } catch (Exception e) {
            log.error("get error exception:", e);
            return 0;
        }
    }
}
