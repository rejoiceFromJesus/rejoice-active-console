/**
 * 系统项目名称
 * com.rejoice.active.console.controller
 * ActiveHistoryController.java
 * 
 * 2017年6月11日-下午6:27:17
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rejoice.active.console.entity.ActiveHistory;
import com.rejoice.active.console.service.ActiveHistoryService;

/** 
 *
 * ActiveHistoryController
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年6月11日 下午6:27:17
 * 
 * @version 1.0.0
 *
 */
@RequestMapping("active")
@RestController
public class ActiveHistoryController extends BaseController<ActiveHistory, ActiveHistoryService>{
  
}
