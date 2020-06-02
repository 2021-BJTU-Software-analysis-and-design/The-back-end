package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTest {
    //生成一个jwt令牌
    @Test
    public void testCreateJwt(){
        //证书文件
        String key_location = "xc.keystore";
        //密钥库密码
        String keystore_password = "xuechengkeystore";
        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, keystore_password.toCharArray());
        //密钥的密码，此密码和别名要匹配
        String keypassword = "xuecheng";
        //密钥别名
        String alias = "xckey";
        //密钥对（密钥和公钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypassword.toCharArray());
        //私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        //定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "123");
        tokenMap.put("name", "mrt");
        tokenMap.put("roles", "r01,r02");
        tokenMap.put("ext", "1");
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(aPrivate));
        //取出jwt令牌
        String token = jwt.getEncoded();
        System.out.println(token);
    }

    //资源服务使用公钥验证jwt的合法性，并对jwt解码
    @Test
    public void testVerify(){
        //jwt令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOiIxIiwidXNlcnBpYyI6bnVsbCwidXNlcl9uYW1lIjoiaXRjYXN0Iiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOiJ0ZXN0MDIiLCJ1dHlwZSI6bnVsbCwiaWQiOiI0OSIsImV4cCI6MTU5MTAyNjAzNSwiYXV0aG9yaXRpZXMiOlsiY291cnNlX2Jhc2VfbGlzdCIsImNvdXJzZV9waWNfbGlzdCJdLCJqdGkiOiIyZTM5NTc2Ni1jNDAwLTQxOTgtODgyNC0wYWRmOGNhZGIyNjciLCJjbGllbnRfaWQiOiJYY1dlYkFwcCJ9.bibkQxBgTLy7NYHTVCY133lIa1T4Mj3zJ-wiOb8nFBwaaRTZkgZ7ubKNTkSKdy1N6ZefsBcZIH9hnesdVi9bFBCFDDbgt0jR4K_9XgjeeMgLZpRYkkXZLH5HHyv4A9MAN9jtieEghwnWsDWWChLM2m4TrGMquxXeI7xqWbGrPNTTd4Fdk3WSJXgra6aG0U-RBnn7T6Q4-v6lFFoyPGczdrj3FU4A5OKfZJdAkLR5tR2ZCOIx63AMDSkRl0tPhiqGD_qeuzPKbkyKq8_oXXsTppJCu02x5tpQWiJ0pAMVJS1CDoO1cbrnEz0ZBsoV-O-pbEjtO68dKozuMTnIg3RlLw"; //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi1pGvYqLcTG2dcKhrtisQgkB90iWaCwE4OriDwCLMdiUV2NViEn+r/jMbuIFCBtnB21yWZlIPnXjzcre/8HIUJy2dMWqP9NUhzoCzwdC1I9clZRVHTpe1H0eiaQY4BLxz5EScBZdr5u4Q0hT+t6D3t7qQg1MHxLBaFy2cdHQbmz5Ly/1mmnWBHmFgjbbNG7gfaO3jRCl7RbNVUfSjb6gN+MfpyLk/iXr5S8Qhc2X07hvtm09QEk3cl14tQkZkXAUk7rAl9kgPSKoKr4MAdiYEsVNplKd4LMs4S2AC0dYrhdIX754eo6u4Ehpe6v5hSsF2d3ZpuV7nJ6JDCNxo7tU9wIDAQAB-----END PUBLIC KEY-----";
        //校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        //获取jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
