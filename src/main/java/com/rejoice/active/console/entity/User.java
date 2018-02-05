/**
 * 系统项目名称
 * com.rejoice.active.console.entity
 * User.java
 * 
 * 2017年6月9日-下午2:19:10
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * User
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年6月9日 下午2:19:10
 * 
 * @version 1.0.0
 *
 */
@Table(name="rejoice_user")
public class User extends BaseEntity {
	@Transient
	private String oldPassword;
	
	public String getOldPassword() {
		return oldPassword == null ? password:oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String realName;
	private String nickName;
	private String englishName;
	private String username;
	private String password;
	private String address;
	private Integer age;
	private String sex;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public int getColumnCount() {
        return getClass().getDeclaredFields().length;
    }
}
