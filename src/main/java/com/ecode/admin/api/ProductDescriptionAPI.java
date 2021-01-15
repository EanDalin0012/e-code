package com.ecode.admin.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ecode.admin.service.implement.ProductDetailReferencesServiceImplement;
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
import com.ecode.admin.constant.StatusYN;
import com.ecode.admin.service.implement.ProductDescriptionServiceImplement;

@RestController
@RequestMapping(value = "/api/product/description/v1")
public class ProductDescriptionAPI {
    private static final Logger log = LoggerFactory.getLogger(ProductDescriptionAPI.class);
    @Autowired
    private ProductDescriptionServiceImplement productDescriptionServiceImplement;
    @Autowired
    private ProductDetailReferencesServiceImplement productDetailReferencesServiceImplement;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @GetMapping(value = "/list")
    public ResponseData<MultiMap> index(@RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        return responseDataList(lang);
    }

    @PostMapping(value = "/save")
    public ResponseData<MMap> save(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        return executeSave(param, user_id, lang);
    }

    @PostMapping(value = "/retrieve_id")
    public ResponseData<MMap> retrieveDescriptionByID(@RequestBody MMap param, @RequestParam("userId") int user_id, @RequestParam("lang") String lang) {
        return retrieveValueById(param, user_id, lang);
    }

    private ResponseData<MMap> retrieveValueById(MMap param, int user_id, String lange) {
        ResponseData<MMap> responseData = new ResponseData<MMap>();
        try {
            log.info("============ Start Retrieve Value By ID ==============");

            ObjectMapper objectMapper = new ObjectMapper();
            MMap body = param.getMMap("body");
            MMap input = new MMap();
            String id = body.getString("product_detials_id");
            input.setString("product_detials_id", id);
            MMap output = productDescriptionServiceImplement.retrieveProductDescriptionByProductID(input);
            responseData.setBody(output);

            log.info("========== values: " + objectMapper.writeValueAsString(responseData));
            log.info("============ End Retrieve Value By ID ==============");

            return responseData;
        } catch (ValidatorException ex) {
            log.error("===== get error ValidatorException:", ex);
            ErrorMessage message = MessageUtil.message("product_detail_references_" + ex.getKey(), lange);
            responseData.setError(message);
            return responseData;
        } catch (Exception e) {
            log.error("get error api product save exception", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lange);
            responseData.setError(message);
            return responseData;
        }
    }

    private ResponseData<MMap> executeSave(MMap param, int user_id, String lang) {
        ResponseData<MMap> responseData = new ResponseData<MMap>();
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            log.info("====== Start Product Description Save ===========");

            ObjectMapper objectMapper = new ObjectMapper();
            MMap body = param.getMMap("body");
            MMap productDescription = body.getMMap("product_description");
            MultiMap resourceIDs = body.getMultiMap("resource_id");

            MMap input = new MMap();
            MMap out = new MMap();
            out.setString(StatusYN.STATUS, StatusYN.N);
            int productId = body.getInt("product_id");
            String product_detials_id = Uuid.randomUUID();
            input.setString("id", product_detials_id);
            input.setInt("user_id", user_id);
            input.setString("context_en", productDescription.getString("context_en"));
            input.setString("context_kh", productDescription.getString("context_kh"));
            input.setString("context_ch", productDescription.getString("context_ch"));
            input.setString("status", Status.Active.getValueStr());

            log.info("===== product description content value:" + objectMapper.writeValueAsString(input));
            productDescriptionServiceImplement.save(input);

            if (resourceIDs.size() > 0) {
                for (MMap data : resourceIDs.toListData()) {
                    try {
                        String inDataId = Uuid.randomUUID();
                        MMap inData = new MMap();
                        inData.setString("id", inDataId);
                        inData.setInt("product_id", productId);
                        inData.setString("resource_id", data.getString("resource_id"));
                        inData.setString("product_detials_id", product_detials_id);
                        inData.setString("status", Status.Active.getValueStr());

                        log.info("====== The Values of Product Details Reference:" + objectMapper.writeValueAsString(inData));

                        productDetailReferencesServiceImplement.save(inData);
                    } catch (ValidatorException ex) {
                        transactionManager.rollback(transactionStatus);
                        log.error("===== get error ValidatorException:", ex);
                        ErrorMessage message = MessageUtil.message("product_detail_references_" + ex.getKey(), lang);
                        responseData.setError(message);
                        return responseData;
                    }
                }
            }

            transactionManager.commit(transactionStatus);
            out.setString(StatusYN.STATUS, StatusYN.Y);
            responseData.setBody(out);
            log.info("====== End Product Description Save ===========");
            return responseData;
        } catch (Exception e) {
            log.error("get error api product save exception", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            transactionManager.rollback(transactionStatus);
            return responseData;
        }
    }

    private ResponseData<MultiMap> responseDataList(String lang) {
        ResponseData<MultiMap> responseData = new ResponseData<MultiMap>();
        try {
            log.info("========== Start retreive list of product details ============");

            ObjectMapper objectMapper = new ObjectMapper();
            MMap input = new MMap();
            input.setString("status", Status.Delete.getValueStr());
            MultiMap list = productDescriptionServiceImplement.retrieveList(input);
            responseData.setBody(list);

            log.info("========== Value:" + objectMapper.writeValueAsString(responseData));

        } catch (Exception e) {
            log.error("get error api product save exception", e);
            ErrorMessage message = MessageUtil.message(ErrorCode.EXCEPTION_ERR, lang);
            responseData.setError(message);
            return responseData;
        }
        return responseData;
    }
}
