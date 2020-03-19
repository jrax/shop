package com.qf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopRegistWebApplicationTests {

    static String wsdlUrl = "http://112.74.76.186:8030/service/webService/Web_Service";

    //帐号
    static String account = "C75119283";
    //密码
    static String password = "0f2f9afd3aa997b92b6dc4eb665dba8e";
    //校验码
    static String checkCode = "124283";

	@Test
	void contextLoads() {
        StringBuilder xmlParams = new StringBuilder();
        xmlParams.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xmlParams.append("<root>");
        xmlParams.append("<username>").append(account).append("</username>");
        xmlParams.append("<password>").append(password).append("</password>");
        /*xmlParams.append("<veryCode>").append(checkCode).append("</veryCode>");*/
        xmlParams.append("<mobile>").append("18320816202").append("</mobile>");
        xmlParams.append("<content>").append("123456").append("</content>");
        xmlParams.append("<extcode></extcode>");
        xmlParams.append("<sendtime></sendtime>");
        xmlParams.append("<msgtype>2</msgtype>");
        xmlParams.append("<signtext></signtext>");
        xmlParams.append("<tempid>").append(1).append("</tempid>");
        xmlParams.append("</root>");

        xmlParams.toString();
	}

}
