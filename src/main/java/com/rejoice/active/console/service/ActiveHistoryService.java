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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rejoice.active.console.common.util.RejoiceUtil;
import com.rejoice.active.console.entity.ActiveHistory;
/**
 * 
 *
 * ActiveHistoryService
 * 
 * @author rejoice 948870341@qq.com
 * @date 2017年6月11日 下午6:28:46
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class ActiveHistoryService extends BaseService<ActiveHistory> {

	public PageInfo<ActiveHistory> queryListByPageAndOrder(ActiveHistory activeHistory, Integer page,
			Integer rows,String sorts[],String orders[]) {
		// 第一个参数是起始页，第二个参数是，页面显示的数据条数
        PageHelper.startPage(page, rows);
        Example example = new Example(ActiveHistory.class);
        if (RejoiceUtil.isNotBlank(sorts) && RejoiceUtil.isNotBlank(orders)) {
        	StringBuilder sortSB = new StringBuilder();
        	for(int i = 0; i < sorts.length; i++){
        		sortSB.append(StringUtil.camelhumpToUnderline(sorts[i])).append(" ").append(orders[i]).append(",");
        	}
            example.setOrderByClause(sortSB.substring(0, sortSB.length()-1));
        }
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(activeHistory.getCompanyCode())){
        	criteria.andEqualTo("companyCode", activeHistory.getCompanyCode());
        }
        if(StringUtils.isNotBlank(activeHistory.getCompanyName())){
        	criteria.andLike("companyName", "%"+activeHistory.getCompanyName()+"%");
        }
       
		List<ActiveHistory> list = this.getMapper().selectByExample(example);
		return new PageInfo<ActiveHistory>(list);
	}
	
	
}
