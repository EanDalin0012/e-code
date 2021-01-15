package com.ecode.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ecode.admin.service.implement.VendorServiceImplement;
import com.ecode.admin.util.MessageUtil;
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
@RequestMapping(value = "/api/vendor/v1")
public class VendorApi {

    private static final Logger log = LoggerFactory.getLogger(VendorApi.class);

    @Autowired
    private VendorServiceImplement vendorService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @GetMapping(value = "/list")
    public ResponseData<MultiMap> list(@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MultiMap> responseData = new ResponseData<>();
        try {
            log.info("====== Start Vendor get list ====");

            ObjectMapper objectMapper = new ObjectMapper();
            MMap input = new MMap();
            input.setString("status", Status.Delete.getValueStr());
            MultiMap responseBody = vendorService.retrieveList(input);
            responseData.setBody(responseBody);

            log.info("====== Vendor list value:" + objectMapper.writeValueAsString(responseData));
            log.info("<===== End Vendor get list ====");

            return responseData;
        } catch (ValidatorException ex) {
            log.error("get error", ex);
            ErrorMessage message = MessageUtil.message("vendor_" + ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            log.error("get error", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }

    }

    @PostMapping(value = "/save")
    public ResponseData<MMap> save(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        return execute("save", user_id, lang, param.getMMap("body"));
    }

    @PostMapping(value = "/update")
    public ResponseData<MMap> update(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        return execute("update", user_id, lang, param.getMMap("body"));
    }

    @PostMapping(value = "/delete")
    public ResponseData<MMap> delete(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) throws Exception {
        return delete(user_id, lang, param.getMultiMap("body"));
    }

    private ResponseData<MMap> execute(String function, int user_id, String lang, MMap param) {
        ResponseData<MMap> responseData = new ResponseData<>();
        try {
            log.info("========= Start Vendor " + function + "==========");

            ObjectMapper objectMapper = new ObjectMapper();
            MMap input = new MMap();
            MMap out = new MMap();
            out.setString(StatusYN.STATUS, StatusYN.N);
            input.setInt("user_id", user_id);
            input.setString("name", param.getString("name"));
            input.setString("contact", param.getString("contact"));
            input.setString("email", param.getString("email"));
            input.setString("description", param.getString("description"));
            input.setString("other_contact", param.getString("other_contact"));
            input.setString("address", param.getString("address"));
            input.setString("status", Status.Active.getValueStr());

            if (function == "save") {
                int id = vendorService.sequence();
                input.setInt("id", id);

                log.info("========== Values:" + objectMapper.writeValueAsString(input));

                int save = vendorService.save(input);
                if (save > 0) {
                    out.setString(StatusYN.STATUS, StatusYN.Y);
                }

            } else if (function == "update") {
                input.setInt("id", param.getInt("id"));
                int update = vendorService.update(input);

                log.info("========== Values:" + objectMapper.writeValueAsString(input));

                if (update > 0) {
                    out.setString(StatusYN.STATUS, StatusYN.Y);
                }
            }

            responseData.setBody(out);

            log.info("===== Vendor response : " + objectMapper.writeValueAsString(responseData));
            log.info("===== End Vendor save api ====");

            return responseData;
        } catch (ValidatorException ex) {
            log.error("====get error", ex);
            ErrorMessage message = MessageUtil.message("vendor_" + ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            log.error("========error exception:", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
    }

    private ResponseData<MMap> delete(int user_id, String lang, MultiMap param) {
        ResponseData<MMap> responseData = new ResponseData<>();
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            log.info("=========Start Delete Vendor========");

            ObjectMapper objectMapper = new ObjectMapper();
            log.info("========= Delete values:" + objectMapper.writeValueAsString(param));

            MMap out = new MMap();
            out.setString(StatusYN.STATUS, StatusYN.N);
            if (param.size() > 0) {
                MMap input = new MMap();
                for (MMap data : param.toListData()) {
                    input.setInt("id", data.getInt("id"));
                    input.setInt("user_id", user_id);
                    input.setString("status", Status.Delete.getValueStr());
                    vendorService.delete(input);
                }

                transactionManager.commit(transactionStatus);
                out.setString("status", "Y");
                responseData.setBody(out);
            }

            log.info("======== Response Values:" + objectMapper.writeValueAsString(responseData));
            log.info("=========Start Delete Vendor========");

            return responseData;
        } catch (ValidatorException ex) {
            log.error("get error", ex);
            ErrorMessage message = MessageUtil.message("vendor_" + ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            log.error("get error", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }

    }
}
