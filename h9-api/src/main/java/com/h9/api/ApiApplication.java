package com.h9.api;

import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.h9.common","com.h9.api"})
@EnableSwagger2
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		Logger logger = Logger.getLogger(ApiApplication.class);
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
