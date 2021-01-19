package com.ecode.core.encryption;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author : Mr.Yan
 * @program : com
 * @create : 2019-03-28 09:30
 * @description : 读取配置文件Bean
 */
@Component
@ConfigurationProperties(prefix = "rsa")
public class ReadProBean {

    /**
     * RSA 加密定义公钥
     */
    private String publicKey;

    /**
     * RSA 加密定义私钥
     */
    private String privateKey;

    /**
     * 前台发送的加密的json串命名
     */
    private String requestInput;

    /**
     * 后台发送的加密的json串命名
     */
    private String responseOutput;

}
