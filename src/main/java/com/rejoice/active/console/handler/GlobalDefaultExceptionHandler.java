package com.rejoice.active.console.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.rejoice.active.console.controller.CompanyController;
/**
* @ClassName: GlobalDefaultExceptionHandler 
* @Description: 全局异常处理
* @author rejoice  948870341@qq.com 
* @date 2016年9月27日 下午5:07:48 
*
 */   
@ControllerAdvice(basePackageClasses={CompanyController.class})
class GlobalDefaultExceptionHandler {
  public static final String DEFAULT_ERROR_VIEW = "/error";
  private Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
  
  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    // If the exception is annotated with @ResponseStatus rethrow it and let
    // the framework handle it - like the OrderNotFoundException example
    // at the start of this post.
    // AnnotationUtils is a Spring Framework utility class.
	logger.error("出错了：{}",e.getMessage(),e);
    if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
      throw e;

    // Otherwise setup and send the user to a default error-view.
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e.getMessage());
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    return mav;
  }
  
}