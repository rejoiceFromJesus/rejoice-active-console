/**
 * 系统项目名称
 * com.rejoice.active.console.controller
 * CompanyController.java
 * 
 * 2017年5月23日-下午4:35:35
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rejoice.active.console.response.ResponseVo;
import com.rejoice.active.console.service.CompanyService;

/**
 *
 * CompanyController
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年5月23日 下午4:35:35
 * 
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("api/company")
public class ApiCompanyController {

	@Autowired
	CompanyService companyService;
	
	@PostMapping("active")
	public ResponseVo active(@RequestBody String code){
		return ResponseVo.success("activation success");
	}
	
}
