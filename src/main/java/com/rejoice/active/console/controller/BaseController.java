/**
 * 系统项目名称
 * com.rejoice.active.console.controller
 * BaseController.java
 * 
 * 2017年6月9日-下午2:28:19
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.rejoice.active.console.common.bean.EasyUIResult;
import com.rejoice.active.console.service.BaseService;

/**
 *
 * BaseController
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年6月9日 下午2:28:19
 * 
 * @version 1.0.0
 *
 */ 
public class BaseController<T,S> { 

	
	@Autowired
	BaseService<T> baseService;
	
	@SuppressWarnings("unused")
	private Class<T> clazz;
	
	
	@SuppressWarnings("unchecked")
	protected S getService(){
		return (S) baseService;
	}
	
  @SuppressWarnings("unchecked")
    public BaseController() {
        super();
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType ptype = (ParameterizedType) type;
        this.clazz = (Class<T>) ptype.getActualTypeArguments()[0];
    }

	
	@GetMapping("all")
	public ResponseEntity<List<T>> findAll(){
		return ResponseEntity.ok(baseService.queryAll());
	}
	@GetMapping("page") 
	public ResponseEntity<EasyUIResult> findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "30") Integer rows,String[] sort,String[] order, T t) throws Exception{
		PageInfo<T> pageInfo = baseService.queryListByPageAndOrder(t, page, rows,sort,order);
		 EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
		return ResponseEntity.ok(easyUIResult);
	}
	@PutMapping
	public void update(@RequestBody T t) throws Exception{
		baseService.updateByIdSelective(t);
	}     
	
	@PostMapping 
	public void save(@RequestBody T t) throws Exception{
		baseService.save(t);
	}
	@DeleteMapping("{ids}")
	public void delete(@PathVariable("ids") String ids){
		baseService.deleteByIds(Arrays.asList(ids.split(",")));
	}
	@GetMapping("{id}")
	public T getById(@PathVariable("id") Serializable id){
		return baseService.queryByID(id);
	} 
}
