/**
 * 系统项目名称
 * com.rejoice.active.console.service
 * CompanyService.java
 * 
 * 2017年5月23日-下午4:35:01
 *  2017金融街在线公司-版权所有
 *
 */
package com.rejoice.active.console.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rejoice.active.console.common.util.RejoiceUtil;
import com.rejoice.active.console.entity.ActiveHistory;
import com.rejoice.active.console.entity.Company;

/**
 *
 * CompanyService
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年5月23日 下午4:35:01
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class CompanyService extends BaseService<Company> {

	
	@Autowired
	ActiveHistoryService activeHistoryService;
	
	public PageInfo<Company> queryListByPageAndOrder(Company company, Integer page,
			Integer rows,String sorts[],String orders[]) {
		// 第一个参数是起始页，第二个参数是，页面显示的数据条数
        PageHelper.startPage(page, rows);
        Example example = new Example(Company.class);
        if (RejoiceUtil.isNotBlank(sorts) && RejoiceUtil.isNotBlank(orders)) {
        	StringBuilder sortSB = new StringBuilder();
        	for(int i = 0; i < sorts.length; i++){
        		sortSB.append(StringUtil.camelhumpToUnderline(sorts[i])).append(" ").append(orders[i]).append(",");
        	}
            example.setOrderByClause(sortSB.substring(0, sortSB.length()-1));
        }
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(company.getCode())){
        	criteria.andEqualTo("code", company.getCode());
        }
        if(StringUtils.isNotBlank(company.getName())){
        	criteria.andLike("name", "%"+company.getName()+"%");
        }
       
		List<Company> list = this.getMapper().selectByExample(example);
		return new PageInfo<Company>(list);
	}

	/**
	 * active(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param code
	 * void
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	*/
	public String active(String code) throws Exception {
		Company company = new Company();
		company.setCode(code);
		company = this.queryOne(company);
		if(company == null){
			return "companyCode not found:"+code;
		}
		if(company.getActiveSize() >= company.getMaxActiveSize()){
			return "activeSize exceed maxActiveSize, please apply to increase the maxActiveSize";
		}
		//save ActiveHistory
		ActiveHistory active = new ActiveHistory();
		active.setCompanyCode(company.getCode());
		active.setCompanyName(company.getName());
		active.setActiveTime(new Date());
		active.setCreateTime(active.getActiveTime());
		active.setUpdateTime(active.getActiveTime());
		activeHistoryService.save(active);
		//update company's activeSize
		company.setActiveSize(company.getActiveSize()+1);
		company.setUpdateTime(active.getActiveTime());
		this.updateByIdSelective(company);
		//success
		return null;
	}
	
	
}
