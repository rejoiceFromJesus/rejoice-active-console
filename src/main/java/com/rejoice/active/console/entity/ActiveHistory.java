/**
 * 系统项目名称
 * com.rejoice.active.console.entity
 * ActiveHistory.java
 * 
 * 2017年6月11日-下午6:18:38
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * ActiveHistory
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年6月11日 下午6:18:38
 * 
 * @version 1.0.0
 *
 */
@Table(name="rejoice_active_history")
public class ActiveHistory extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String companyCode;
	private String companyName;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date activeTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	
}
