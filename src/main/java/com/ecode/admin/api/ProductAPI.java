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
import com.ecode.admin.service.implement.ProductServiceImplement;
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
@RequestMapping(value = "/api/product/v1")
public class ProductAPI {
	private static final Logger log = LoggerFactory.getLogger(ProductAPI.class);
    
	@Autowired
    private ProductServiceImplement productService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @GetMapping(value = "/list")
    public ResponseData<MultiMap> list(@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        return getProductList(lang);
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
    public ResponseData<MMap> delete(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        return delete(user_id, lang, param.getMultiMap("body"));
    }

    @PostMapping(value = "/switch_web")
    public ResponseData<MMap> updateShowOnWeb(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        return  swichOn("web", param.getMMap("body"), user_id, lang);
    }

    @PostMapping(value = "/switch_mobile")
    public ResponseData<MMap> updateShowOnMobile(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        return  swichOn("mobile", param.getMMap("body"), user_id, lang);
    }

    private ResponseData<MMap> execute(String function, int user_id, String lang, MMap param) {
        ResponseData<MMap> responseData = new ResponseData<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MMap input = new MMap();
            MMap out = new MMap();
            out.setString(StatusYN.STATUS, StatusYN.N);

            input.setInt("user_id", 			user_id);
            input.setString("name", 			param.getString("name"));
            input.setString("description", 		param.getString("description"));
            input.setString("status", 			Status.Active.getValueStr());
            input.setInt("category_id", 		param.getInt("category_id"));
            input.setString("resource_img_id", 	param.getString("resource_img_id"));


            if (function == "save") {
                int id = productService.sequence();
                input.setInt("id", id);
                log.info("product info:", input);
                int save = productService.save(input);
                if (save > 0) {
                	out.setString(StatusYN.STATUS, StatusYN.Y);
                }

            } else if (function == "update") {
                input.setInt("id", param.getInt("id"));
                log.info("product info:", objectMapper.writeValueAsString(input));
                int update = productService.update(input);
                if (update > 0) {
                    out.setString(StatusYN.STATUS, StatusYN.Y);
                }
            }

            responseData.setBody(out);

            log.info("=====****Product response : " + responseData + "***====");
            log.info("=====****End product " + function);
            return responseData;
        } catch (ValidatorException ex) {
            log.error("===== get error save api product:", ex);
            ErrorMessage message = MessageUtil.message("product_"+ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            log.error("get error api product save exception", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
       
    }

    private ResponseData<MMap> delete(int user_id, String lang, MultiMap param) {
        ResponseData<MMap> responseData = new ResponseData<>();
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            log.info("====== Data delete: " + param);

            MMap out = new MMap();
            out.setString(StatusYN.STATUS, StatusYN.N);
            if (param.size() > 0) {
                MMap input = new MMap();
                for (MMap data : param.toListData()) {
                    input.setInt("id", data.getInt("id"));
                    input.setInt("user_id", user_id);
                    input.setString("status", Status.Delete.getValueStr());
                    productService.delete(input);
                }

                transactionManager.commit(transactionStatus);
                out.setString(StatusYN.STATUS, StatusYN.Y);
                responseData.setBody(out);
            }
        } catch (ValidatorException ex) {
        	log.error("===== get error save api product:", ex);
            ErrorMessage message = MessageUtil.message("product_"+ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
        	log.error("========== get error exection", e);
        	ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
        return responseData;
    }

    private ResponseData<MultiMap> getProductList(String lang) {
        ResponseData<MultiMap> responseData = new ResponseData<>();
        try {
            log.info("====== Start Product get list ====");

            MMap input = new MMap();
            input.setString("status", Status.Delete.getValueStr());

            MultiMap responseBody = productService.retrieveList(input);
            responseData.setBody(responseBody);

            log.info("===== Product list value:" + responseData );
            log.info("=====End Product get list====");

        } catch (ValidatorException ex) {
            log.error("====== get error api product list:", ex);
            ErrorMessage message = MessageUtil.message("product_"+ex.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            log.error("get error exception api product:", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
        return responseData;
    }


    public ResponseData<MMap> swichOn(String note, MMap data, int user_id, String lang) {
        ResponseData<MMap> responseData = new ResponseData<>();
        try{
            MMap input = new MMap();
            MMap output = new MMap();
            output.setString("status", StatusYN.N);

            input.setInt("id", data.getInt("product_id"));
            input.setString("status", Status.Modify.getValueStr());
            input.setInt("user_id", user_id);
            ObjectMapper objectMapper = new ObjectMapper();

            log.info("data", objectMapper.writeValueAsString(input));

            if(note == "web") {
                input.setBoolean("web_show", data.getBoolean("value"));
                int u = productService.updateShowOnWeb(input);
                if (u > 0) {
                    output.setString(StatusYN.STATUS, StatusYN.Y);
                }
            } else if (note == "mobile") {
                input.setBoolean("mobile_show", data.getBoolean("value"));
                int u = productService.updateShowOnMobile(input);
                if (u > 0) {
                    output.setString(StatusYN.STATUS, StatusYN.Y);
                }
            }
            responseData.setBody(output);
            return responseData;
    } catch(ValidatorException ex){
        log.error("get error updateShowOnWeb api product", ex);
        ErrorMessage message = MessageUtil.message("product_"+ex.getKey(), lang);
        responseData.setError(message);
        return responseData;
    } catch(Exception e) {
    	 ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
         responseData.setError(message);
         return responseData;
    }
       
    }
}
