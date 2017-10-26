package com.h9.admin;

import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.h9.common", "com.h9.admin"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);

        Logger logger = Logger.getLogger(AdminApplication.class);
        logger.info("\n" +
                "                                        _ooOoo_\n" +
                "                                       o8888888o\n" +
                "                                       88\\\" . \\\"88\n" +
                "                                        (| -_- |)\n" +
                "                                       O\\\\  =  /O\n" +
                "                                    ____/`---'\\\\____\n" +
                "                                  .'  \\\\\\\\|     |//  `.\n" +
                "                                 /  \\\\\\\\|||  :  |||//  \\\\\n" +
                "                                /    _||||| -:- |||||-  \\\\\n" +
                "                                |  | \\\\\\\\\\\\  -  /// |   |\n" +
                "                                | \\\\_|  ''\\\\---/''  |   |\n" +
                "                                \\\\  .-\\\\__  `-`  ___/-. /\n" +
                "                              ___`. .'  /--.--\\\\  `. . __\n" +
                "                           .\\\"\\\" '<  `.___\\\\_<|>_/___.'  >'\\\"\\\".\n" +
                "                          | | :  `- \\\\`.;`\\\\ _ /`;.`/ - ` : | |\n" +
                "                          \\\\  \\\\ `-.   \\\\_ __\\\\ /__ _/   .-` /  /\n" +
                "                     ======`-.____`-.___\\\\_____/___.-`____.-'======\n" +
                "                                        `=---='\n" +
                "                     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" +
                "                                佛祖保佑  永不冗机  永无BUG");
    }


}
