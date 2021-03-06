package com.qf.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PayServiceController {

    @RequestMapping("doPay")
    void doPay(HttpServletRequest httpRequest,
               HttpServletResponse httpResponse,
               @RequestParam("oid") String oid, @RequestParam("price") Double price) throws IOException {
        /**
         *参数说明：
         * 1. 支付网关
         * 2.应用id
         * 3.商户应用私钥
         * 4.返回的数据类型： json
         * 5.字符集： utf-8
         * 6. 支付宝公钥  注意，是支付宝公钥，不是商户公钥
         * 7. 密钥生成方式： RSA2
         */
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                "2016101800719298",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC3B5SjUwFnA46nqXcyKdxWUs/2cp+jtRxr3p3paU2yVC72dHrFD4+Bu5AatzkeyW2dUUJlX2nxu5uRA1Xv2ITbdJbkUnsTR+yDcGSxMpDmfkoUKjwV5LuSpgsvZCGP4spzK6z8unMODLEI0L4Gy1LdEt26JA3Bq3fUJvcQppyICD60vdHAGqs3aPUPwhhW7i1W52JG8NaKxiJl2hlH4Y95Tnb1tFwJSiOKQoqzkaO2kukDIlNJkZ7LOgLtkaJckdzrG+kybOE+vSHdv45WqbmJUVmAUGIEfcrVyojH7m7W1+q5yHU3unZt5uLjFgf0j12lL4weCHM8TABOVqGxtcbNAgMBAAECggEAR6D/2qsxcU9DFPqNW2RmhqICIUDamQbYCJ8mzaIYdiHb/ze2lEhysSLmUo5CHHpYG6khgurEW4ZAa5ASobrqw7ftFY3x/Km5NfdWcMKJUqs58opU2Xyaq9mvpuxY9iabdFcza/TL8kOlRP3sYFxSIZKwk5hoidLBIpXdOvb5a4KknU0dK3sYEQJsaLTLy4Yx0YH59/ctpouacXLAIUyR6os6Slox1WviFe7nQLUkkoYn1IDpLFwNgh9dWsV7tApQucQz3T1ddXo3A7YPGDQHeOu+j/Lb+mJcfVAWhW1xH0n86MSIQBtsSvUB23yg3HcJ2RqReEy1TXnay7LOF0kdQQKBgQDphuMcvXt3+7opfSWM2IAMvCt4dX+lcAb09vCpbpNLfCKkC3jmeWZmKTpUK14dDIxNQroFENnyMie9uzh/NoFVlD8ouAsirWKlhv+fyf4rjn6qlYSsYMiYCq1OFUCox8jryg+94i7SvSz4HvQZnRKFM6oV6IuHEjq7Ev0x3NaOfwKBgQDIpKd/lZ+k0bJVmkPdvIiqB5yoPd4NMc+d5FHdhdC1dw0c9dh7onAKp+3j+IG6At83/fbw+9HEQHgBOQb8kNgbV0rPJ/bMRg7SmQ3/VdyV/siMJTthgTqjtHiKLH2jb7np1BQ243uLq+wYRp99+0L4irW+2BTSWdOLIBCEA0XcswKBgAL5r/0WwPL3ZxjOOmNAAummoBspzBWMhtoqJ0jRWtkUXNCgQMsW8uIMVmzqHIncNhFp7zrPSyeV0gHN19tvE1jGycM4DRRFB0O6o5kilrVxXFXKD+WuWf/t6uVayfccjIfMjETME/KlbGRA6LVG1gySavFahCSVa+AkTsuK18T7AoGBAJSlb7ZU+k4YUP+cZ1UaPUGCnCwEkcLCuWbnZUMxxTr7dUqUVIrcABfU66huTmWphQgtbcqBNZoojP+S6TsqcABpfnHU0MHr0UVywOz/BopVlOxHFSQaon/vzzHG3lm8B0dVqJg1li0W9LXTdIvYH3iVAyrdWSeyBshWvAWhixuDAoGBAN+lfS5aUs7btegP4YMqqebP6xSCJdzoPXDMpFo2nRM+4dc++5/aFLsE8HS6dReSj5nbSyVk1tWmak3C2ccc456zBUegfWV/lSvV58zJp9iRkaVFpMr9QLp1sMtJuhh/3fry28nSFd/62rM+lxdI99ObR9ffutpb3tGMBeR2M4FH",
                "json",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtKyvFfOyyCnFA3Sgg4em4nXC8+ITqow+8fxZ9ml87aODuKE08GaCx9Cpp7frIdWaV3Tuk2rjGVsCETDMrNvXdk7PU5BxGw2yK25ywwT+hoki75k+zGhGzgOTVfULK42rxdTNMbDuutiIPWfQYKRzZxeoXVghgJ+Ggub0+SfF5OF+h0IJBhrEPcp238rsi8Miig16Hfgeb2C9X4Z8iyhxGeB7/YHtyiL4tA6NxWK29nK5NRg0Mywvse3StGeVfWP0Vo7lee5UaiD5O/8wfsH/4OI4ZRQYEfQaJgzsvhsNus4mQLmsBMq6j3Y+xcRMlhxA716MrbtR9jopIPNotTrauQIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://9ee8iu.natappfree.cc/pay/success");
        alipayRequest.setNotifyUrl("http://9ee8iu.natappfree.cc/pay/notifyUrl");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+oid+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}
