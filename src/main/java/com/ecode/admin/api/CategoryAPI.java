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
import com.ecode.admin.service.implement.CategoryServiceImplement;
import com.ecode.admin.util.MessageUtil;
import com.ecode.core.component.Translator;
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
@RequestMapping(value = "/api/category/v1")
public class CategoryAPI {

	private static final Logger log = LoggerFactory.getLogger(CategoryAPI.class);

    @Autowired
    private CategoryServiceImplement categoryService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @GetMapping(value = "/list")
    public ResponseData<MultiMap> list(@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        ResponseData<MultiMap> response = new ResponseData<>();
        try {
            log.info("=====Start get list of category=====");
            MMap input = new MMap();
            input.setString("status", Status.Delete.getValueStr());

            MultiMap out    = categoryService.retrieveList(input);
            response.setBody(out);

            log.info("===== Result : "+response);
            log.info("===== End get list of category=====");
            return response;
        } catch (ValidatorException ev) {
            log.error("========= error ValueException api category get list", ev);
            ev.printStackTrace();
            ErrorMessage message = MessageUtil.message("category_"+ev.getKey(), lang);
            response.setError(message);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("============ error Exception api category get list", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            response.setError(message);
            return response;
        }

    }

    @PostMapping(value = "/save")
    public ResponseData<MMap> save (@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) {
        ResponseData<MMap> responseData = new ResponseData<>();
        try {
            log.info("===== Start save category =====");
            MMap body  = param.getMMap("body");

            MMap responseBody = new MMap();
            int sequence = categoryService.sequence();

            MMap input = new MMap();
            input.setInt("id", sequence);
            input.setInt("user_id", user_id);
            input.setString("name", body.getString("name"));
            input.setString("description", body.getString("description"));
            input.setString("status", Status.Active.getValueStr());

            log.info("===== Start value param: "+param);
            log.info("===== Start value: "+input);

            int save = categoryService.save(input);
            if (save > 0) {
                responseBody.setString("status", "Y");
            }
            responseData.setBody(responseBody);

            log.info("======== ResponseData:"+responseData);
            log.info("======== End save category ====");
            
        } catch (ValidatorException ev) {
            log.error("========== error ValidatorException api category get list", ev);
            ErrorMessage message = MessageUtil.message("category_"+ev.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("========= error Exception api save category", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            return responseData;
        }
        return responseData;
    }

    @PostMapping(value = "/update")
    public ResponseData<MMap> update(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) throws Exception {
        ResponseData<MMap> responseData = new ResponseData<>();
        try {
          log.info("========= Start Update category update data ======");

          MMap out = new MMap();
          
          MMap body = param.getMMap("body");
          MMap input = new MMap();
          input.setInt("user_id", user_id);
          input.setInt("id", body.getInt("id"));
          input.setString("name", body.getString("name"));
          input.setString("description", body.getString("description"));
          input.setString("status", Status.Modify.getValueStr());

          log.info("====== Update category update data param: "+param);
          log.info("====== Update category update data  input: "+input);

          int update = categoryService.update(input);
          if (update > 0) {
              out.setString(StatusYN.STATUS, StatusYN.Y);
          }

          responseData.setBody(out);

          log.info("====== Out put data: "+responseData);
          log.info("====== End Update category update data ======");

        } catch (ValidatorException ev){
            ev.printStackTrace();
            log.error("======= error:", ev);
            ErrorMessage message = MessageUtil.message("category_"+ev.getKey(), lang);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("======== error: ", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
        return responseData;
    }

    @PostMapping(value = "/delete")
    public ResponseData<MMap> delete(@RequestParam("userId") int user_id, @RequestParam("lang") String lang, @RequestBody MMap param) throws Exception {
        ResponseData<MMap> responseData = new ResponseData<>();
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            log.info("============ Start delete api category delete =============");
            log.info("============ Start delete api category param: "+param);

            MMap out = new MMap();
            out.setString(StatusYN.STATUS, StatusYN.N);
            MultiMap body = param.getMultiMap("body");

            if (body.size() > 0) {
                MMap input = new MMap();
                for (MMap data : body.toListData()) {
                    input.setInt("id", data.getInt("id"));
                    input.setInt("user_id", user_id);
                    input.setString("status", Status.Delete.getValueStr());
                    categoryService.delete(input);
                }

                transactionManager.commit(transactionStatus);
                out.setString(StatusYN.STATUS, StatusYN.Y);
                responseData.setBody(out);
            }

        } catch (ValidatorException ev) {
            ev.printStackTrace();
            log.error("error Application Exception api save category", ev);
            ErrorMessage message = MessageUtil.message("category_"+ev.getKey(), lang);
            responseData.setError(message);
            return responseData;
        }catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            e.printStackTrace();
            log.error("get error exception delete api category", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
        return responseData;
    }

}
