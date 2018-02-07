/**
 * 系统项目名称
 * com.rejoice.active.console
 * Regulatorsconsole.java
 * 
 * 2017年5月10日-下午2:23:55
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console;

import java.util.concurrent.TimeUnit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rejoice.active.console.common.util.ImageService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * Regulatorsconsole
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年5月10日 下午2:23:55
 * 
 * @version 1.0.0
 *
 */
@Controller
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.DELETE})
@EnableScheduling
@EnableAsync
@EnableCaching
@SpringBootApplication(scanBasePackages="com.rejoice.active.console")
@MapperScan(basePackages = "com.rejoice.active.console.mapper")
public class ActiveConsole {

	public static final String QRCODE_ENDPOINT = "/qrcode";
	public static final long THIRTY_MINUTES = 1800000; 
	
	@Autowired
	ImageService imageService;

	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ActiveConsole.class)
				.properties(
						"spring.config.name:application,constant,activemq",
						"spring.config.location:classpath:/config/").build()
				.run(args);
	}
	
	@GetMapping(value = QRCODE_ENDPOINT, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getQRCode(@RequestParam(value = "text", required = true) String text) {
		try {
			return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
					.body(imageService.generateQRCodeAsync(text, 256, 256).get());
		} catch (Exception ex) {
			throw new InternalServerError("Error while generating QR code image.", ex);
		}
	}
	
	




	  @Scheduled(fixedRate = THIRTY_MINUTES)
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@DeleteMapping(value = QRCODE_ENDPOINT)
		public void deleteAllCachedImages() {
			imageService.purgeCache();
		}

		@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
		public class InternalServerError extends RuntimeException {

			private static final long serialVersionUID = 1L;

			public InternalServerError(final String message, final Throwable cause) {
				super(message);
			}

		}

}

