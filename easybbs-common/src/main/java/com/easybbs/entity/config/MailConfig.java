//package com.easybbs.entity.config;
//
//import com.easybbs.entity.dto.SysSetting4EmailDto;
//import com.easybbs.entity.enums.SysSettingCodeEnum;
//import com.easybbs.entity.po.SysSetting;
//import com.easybbs.service.SysSettingService;
//import com.easybbs.utils.JsonUtils;
//import com.easybbs.utils.SysCacheUtils;
//import org.springframework.boot.autoconfigure.mail.MailProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import javax.annotation.Resource;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
////@Component
//public class MailConfig {
//
//    @Resource
//    private SysSettingService sysSettingService;
//
//    /**
//     * # 配置邮件服务器的地址 smtp.qq.com
//     * #spring.mail.host=smtp.qq.com
//     * #111spring.mail.host=smtp.163.com
//     * # 配置邮件服务器的端口（465或587）
//     * #1111spring.mail.port=465
//     * # 配置用户的账号
//     * #spring.mail.username=laoluo_coder@qq.com
//     * #1111pring.mail.username=m19169640517@163.com
//     * # 配置用户的密码
//     * #spring.mail.password=bnahvyoyjitrecci
//     * #1111spring.mail.password=XYFOZVBEWZMFQIRF
//     * @return
//     */
//
//    public void start(){
//        getMailSender();
//    }
//
//
//    @Bean
//    MailSender getMailSender() {
//
//        SysSetting sysSettingByCode = sysSettingService.getSysSettingByCode(SysSettingCodeEnum.EMAIL.getCode());
//        SysSetting4EmailDto emailSetting = JsonUtils.convertJson2Obj(sysSettingByCode.getJsonContent(), SysSetting4EmailDto.class);
////        SysSetting4EmailDto emailSetting = SysCacheUtils.getSysSetting().getEmailSetting();
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        MailProperties properties = new MailProperties();
//        if (emailSetting.getMailHost() == null || emailSetting.getMailHost() == ""){
//            properties.setHost("smtp.qq.com");
//        } else {
//            properties.setHost(emailSetting.getMailHost());
//        }
//
//        if (emailSetting.getMailPort() == null || emailSetting.getMailPort() == 0){
//            properties.setPort(465);
//        } else {
//            properties.setPort(emailSetting.getMailPort());
//        }
//
//        if (emailSetting.getMailUsername() == null || emailSetting.getMailUsername() == ""){
//            properties.setUsername("laoluo_coder@qq.com");
//        } else {
//            properties.setUsername(emailSetting.getMailUsername());
//        }
//
//        if (emailSetting.getMailPassword() == null || emailSetting.getMailPassword() == ""){
//            properties.setPassword("bnahvyoyjitrecci");
//        } else {
//            properties.setPassword(emailSetting.getMailPassword());
//        }
//
//        applyProperties(mailSender,properties);
//        return mailSender;
//    }
//
//    private void applyProperties(JavaMailSenderImpl sender, MailProperties properties) {
//        sender.setHost(properties.getHost());
//        if (properties.getPort() != null) {
//            sender.setPort(properties.getPort());
//        }
//        sender.setUsername(properties.getUsername());
//        sender.setPassword(properties.getPassword());
//        sender.setProtocol(properties.getProtocol());
//        if (properties.getDefaultEncoding() != null) {
//            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
//        }
//        if (!properties.getProperties().isEmpty()) {
//            sender.setJavaMailProperties(asProperties(properties.getProperties()));
//        }
//    }
//
//    private Properties asProperties(Map<String, String> source) {
//        Properties properties = new Properties();
//        properties.putAll(source);
//        return properties;
//    }
//}
