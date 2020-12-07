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
import com.ecode.admin.service.implement.CompanyServiceImplement;
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
@RequestMapping(value = "/api/company/v1")
public class CompanyAPI {

	private static final Logger log = LoggerFactory.getLogger(CompanyAPI.class);

    @Autowired
    private CompanyServiceImplement companyService;
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    /**
     * <pre>
     *     get list of company
     * </pre>
     *
     * @param param
     * @return
     * @throws
     **/
    @GetMapping(value = "/get/list")
    public ResponseData<MMap> getList(@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MMap> response = new ResponseData<>();
        try {
        	log.info("========== Start retrieve list ==========");
        	
        	ObjectMapper objectMappter = new ObjectMapper();
            MMap input = new MMap();
            input.setString("status", Status.Delete.getValueStr());
            MultiMap list = companyService.getList(input);
            MMap out = new MMap();
            out.setMultiMap("list", list);
            response.setBody(out);
            
            log.info("========== Response Value:"+ objectMappter.writeValueAsString(response));
            log.info("========== End retrieve list ==========");
            
            return response;
        } catch (Exception e) {
            log.error("======== get error api company:", e.getMessage());
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            response.setError(message);
            return response;
        }
    }

    /**
     * <pre>
     *     save information of company
     * </pre>
     *
     * @param param
     * @return
     * @throws
     **/
    @PostMapping(value = "/save")
    public ResponseData<MMap> save(@RequestBody MMap param,@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        return execute(param, "save",lang);
    }

    /**
     * <pre>
     *     update information of company
     * </pre>
     *
     * @param param
     * @return ResponseData<MMap, MMap>
     * @throws Exception
     **/
    @PostMapping(value = "/update")
    public ResponseData<MMap> update(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        return execute(param, "update", lang);
    }

    /**
     * <pre>
     *
     * </pre>
     *
     * @param param
     * @functionName delete
     * @description delete main category by id list
     **/
    @PostMapping(value = "/update/status/to/delete")
    public ResponseData<MMap> delete(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MMap> response = new ResponseData<>();
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
        	log.info("========= Start Delete company =====");
        	
        	ObjectMapper objectMapper = new ObjectMapper();
        	MMap header 	= param.getMMap("header");
            MMap body 		= param.getMMap("body");
            MultiMap list 	= body.getMultiMap("list");
            
            log.info("========= Delete Values:"+ objectMapper.writeValueAsString(list));
            
            for (MMap in : list.toListData()) {
                MMap input = new MMap();
                input.setString("status", Status.Delete.getValueStr());
                input.setInt("userID", header.getInt("userID"));
                input.setInt("id", in.getInt("id"));
                System.out.println(input);
                companyService.delete(input);
            }
            
            MMap resBody = new MMap();
            resBody.setString(StatusYN.STATUS, StatusYN.Y);
            response.setBody(resBody);
            transactionManager.commit(transactionStatus);
            
            log.info("=========== Response Data:"+ objectMapper.writeValueAsString(response));
            log.info("========= End Delete company =====");
            
            return response;
        } catch (ValidatorException ex) {
        	log.error("get error api:", ex);
            ErrorMessage message = MessageUtil.message("company_"+ex.getKey(), lang);
            response.setError(message);
            return response;
		} 
        catch (Exception e) {
            log.error("============ get error:", e.getMessage());
            transactionManager.rollback(transactionStatus);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            response.setError(message);
            return response;
        }
    }

    /**
     * <pre>
     *     register or update information of main category
     * </pre>
     *
     * @param param
     * @param function
     * @return ResponseEntity<MMap>
     * @throws Exception
     */
    private ResponseData<MMap> execute(MMap param, String function, String lang) {
        ResponseData<MMap> response = new ResponseData<>();
        MMap getHeader 	= param.getMMap("header");
        MMap body 		= param.getMMap("body");
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            MMap input = new MMap();
            MMap responseBody = new MMap();
            String Yn = StatusYN.N;
            
            input.setString("name", body.getString("name"));
            input.setLong("contact", body.getLong("contact"));
            input.setString("email", body.getString("email"));
            input.setInt("addressID", body.getInt("addressID"));
            input.setString("description", body.getString("description"));
            input.setInt("userID", getHeader.getInt("userID"));

            if (function == "save") {
                input.setString("status", Status.Active.getValueStr());
                int save = companyService.save(input);
                if (save > 0) {
                    Yn = StatusYN.Y;
                }
            }
            if (function == "update") {
                input.setLong("id", body.getLong("id"));
                input.setString("status", Status.Modify.getValueStr());
                int update = companyService.update(input);
                if (update > 0) {
                    Yn = StatusYN.Y;
                }
            }

            transactionManager.commit(transactionStatus);
            responseBody.setString(StatusYN.STATUS, Yn);
            response.setBody(responseBody);
            return response;
            
        }catch (ValidatorException ex) {
        	log.error("get error api comapy", ex);
            ErrorMessage message = MessageUtil.message("company_"+ex.getKey(), lang);
            response.setError(message);
            return response;
		} 
        catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            log.error("get Exception ", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            response.setError(message);
            return response;
        }
    }

    /**
     * <pre>
     *     get value by id
     * </pre>
     *
     * @param param <pre>
     *                   header:
     *                   body: id: int
     *               </pre>
     */
    @PostMapping(value = "/get/value/by/id")
    public ResponseData<MMap> getValueById(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MMap> response = new ResponseData<>();
        try {
            MMap body = param.getMMap("body");
            MMap input = new MMap();
            input.setInt("id", body.getInt("id"));

            MMap output = companyService.getValueById(input);
            response.setBody(output);

            return response;
        } catch (ValidatorException ex) {
        	log.error("======= get error:", ex);
        	ErrorMessage message = MessageUtil.message("company_"+ex.getKey(), lang);
            response.setError(message);
            return response;
		} 
        catch (Exception e) {
        	log.error("======= get error:", e);
        	ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            response.setError(message);
            return response;
        }

    }
}
