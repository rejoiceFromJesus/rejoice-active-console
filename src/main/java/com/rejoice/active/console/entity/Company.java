/**
 * 系统项目名称
 * com.rejoice.active.console.entity
 * Company.java
 * 
 * 2017年5月23日-下午4:24:33
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Company
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年5月23日 下午4:24:33
 * 
 * @version 1.0.0
 *
 */
@Table(name="rejoice_company")
public class Company extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String code;
	private Long maxActiveSize;
	private Long activeSize;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getMaxActiveSize() {
		return maxActiveSize;
	}
	public void setMaxActiveSize(Long maxActiveSize) {
		this.maxActiveSize = maxActiveSize;
	}
	public Long getActiveSize() {
		return activeSize;
	}
	public void setActiveSize(Long activeSize) {
		this.activeSize = activeSize;
	}
	
}
