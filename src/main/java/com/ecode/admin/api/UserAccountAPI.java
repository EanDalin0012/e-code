package com.ecode.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecode.admin.constant.StatusYN;
import com.ecode.admin.service.implement.UserAccountServiceImplement;
import com.ecode.admin.util.MessageUtil;
import com.ecode.core.constant.ErrorCode;
import com.ecode.core.constant.Status;
import com.ecode.core.dto.ErrorMessage;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;
import com.ecode.core.template.ResponseData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api/user/account/v1")
public class UserAccountAPI {
	  private static final Logger log = LoggerFactory.getLogger(UserAccountAPI.class);
	  
	    @Autowired
	    private UserAccountServiceImplement userAccountService;
	    
	    @PostMapping(value = "/update")
	    public ResponseData<MMap> updateUserAccount(@RequestParam("lang") String lang,@RequestParam("userId") int user_id, @RequestBody MMap param) {
	        ResponseData<MMap> response = new ResponseData<>();
	        try {
	            log.info("====== Start update user account api param:"+param);
	            MMap body = param.getMMap("body");
	            MMap resp = new MMap();
	            MMap input = new MMap();

	            input.setInt("userID", 					user_id);
	            input.setBoolean("enabled", 			body.getBoolean("enabled"));
	            input.setBoolean("accountLocked", 		body.getBoolean("accountLocked"));
	            input.setBoolean("accountExpired", 		body.getBoolean("accountExpired"));
	            input.setBoolean("credentialsExpired", 	body.getBoolean("credentialsExpired"));
	            input.setString("status", 				Status.Modify.getValueStr());
	            input.setString("userName", 			body.getString("userName"));
	            input.setInt("id", 						body.getInt("id"));

	            log.info("====== update user account api input:"+input);
	            String isSuccess = StatusYN.N;

	            int update = userAccountService.updateUserAccount(input);
	            if (update > 0) {
	                isSuccess = StatusYN.Y;
	                resp.setString(StatusYN.STATUS , isSuccess);
	                log.info("====update user account api success =======");
	            }
	            response.setBody(resp);
	            return response;
	        } catch (ValidatorException ex) {
	        	log.error("========= error :", ex);
	        	ErrorMessage message = MessageUtil.message("account_"+ex.getKey(), lang);
	        	response.setError(message);
	            return response;
			}catch (Exception e) {
				log.error("========= error execption:", e);
				ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
				response.setError(message);
	            return response;
	        }
	        
	    }

	    /**
	     * <pre>
	     *     get list of user
	     * </pre>
	     *
	     * @param
	     * @return
	     */
	    @GetMapping(value = "/list")
	    public ResponseData<MMap> getUserList(@RequestParam("lang") String lang) {
	        ResponseData<MMap> responseData = new ResponseData<>();
	        try {
	            log.info("====Start get list user account api ====");
	            MMap input = new MMap();
	            MMap body = new MMap();

	            input.setString("status", Status.Delete.getValueStr());
	            log.info("==== get list user account api input: "+ input);
	            MultiMap userList = userAccountService.getList(input);

	            int count = userAccountService.count();
	            body.setMultiMap("items", userList);
	            body.setInt("totalRecords", count);

	            responseData.setBody(body);

	            log.info("====End get list user account api return value:"+responseData);
	            log.info("====End get list user account api====");

	            return responseData;
	        } catch (Exception e) {
	        	log.error("========= error execption:", e);
	        	ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
	        	responseData.setError(message);
	            return responseData;
	        }
	    }

	    @PostMapping(value = "/account_id")
	    public ResponseData<MMap> retrieveUserById(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
	        ResponseData<MMap> responseData = new ResponseData<>();
	        MMap out = new MMap();

	        try {
	            MMap body = param.getMMap("body");
	            MMap input = new MMap();
	            input.setInt("id", body.getInt("account_id"));
	            MMap data = userAccountService.retrieveUserAccountByID(input);
	            log.info("Retrieve User Account By Id Data:", data);
	            responseData.setBody(data);
	            out.setString(StatusYN.STATUS, StatusYN.Y);
	            return responseData;
	        } catch (ValidatorException ex) {
	            log.error("get error api retrieveUserById", ex);
	            ErrorMessage message = MessageUtil.message("account_"+ex.getKey(), lang);
	            responseData.setError(message);
	            return responseData;
	        }
	        catch (Exception e) {
	            log.error("get error api retrieveUserById", e);
	            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
	        	responseData.setError(message);
	            return responseData;
	        }

	    }
}
