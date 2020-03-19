package com.qf.controller;

import com.qf.constant.RedisConstant;
import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class RegistController {

    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender sender;
    @Value("${activeServerUrl}")
    private String activeServerUrl;
    @Autowired
    private RedisTemplate redisTemplate;





    @RequestMapping("regist")
    @ResponseBody
    public ResultVO getResult(@RequestParam String name,@RequestParam String password,@RequestParam String codeID){
        TUser user = new TUser();
        user.setName(name);
        user.setPassword(password);
        int insert = userMapper.insert(user);
        ResultVO resultVO = new ResultVO();

        if (insert>0){
            resultVO.setCode(200);
            resultVO.setData("");
            resultVO.setMsg("添加成功!!!");
        }else {

            resultVO.setCode(400);
            resultVO.setData("");
            resultVO.setMsg("添加失败!!!");
        }

        return resultVO;
    }

    @RequestMapping("registByEmail")
    @ResponseBody
    public ResultVO registByEmail(@RequestParam String email,
                                  @RequestParam String password){

        TUser user = userMapper.selectByEmail(email);
        ResultVO resultVO = new ResultVO();

        if(user != null){
            resultVO.setCode(400);
        }else {
            TUser user1 = new TUser();
            user1.setName("");
            user1.setPassword(password);
            user1.setEmail(email);
            userMapper.insertSelective(user1);
            resultVO.setCode(200);
        }
        return resultVO;
    }

    @RequestMapping("email/send")
    @ResponseBody
    public ResultVO emailSend(@RequestParam String email,
                              @RequestParam String uuid){
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper mailMessage = null;
        try {
            mailMessage = new MimeMessageHelper(message,true);
            mailMessage.setSubject("账号激活");

            //读取模板内容
            Context context = new Context();
            context.setVariable("username",email.substring(0,email.lastIndexOf('@')));
            context.setVariable("url",activeServerUrl+uuid);

            String info = templateEngine.process("emailTemplate", context);

            mailMessage.setText(info,true);

            mailMessage.setFrom("aspio8888@163.com");
            mailMessage.setTo(email);

            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultVO(200,"发送成功","");
    }

    @RequestMapping("email/active/account")
    @ResponseBody
    public ResultVO activeAccount(@RequestParam String uuid){
        String key = RedisConstant.REGIST_EMAIL_URL_KEY_PRE + uuid;
        String email = (String) redisTemplate.opsForValue().get(key);
        TUser user = userMapper.selectByEmail(email);
        if(user!=null){
            user.setIsAdmin("1");
            userMapper.updateByPrimaryKeySelective(user);
            return new ResultVO(200,"激活成功","");
        }else {
            return new ResultVO(400,"激活失败","");
        }

    }

}
