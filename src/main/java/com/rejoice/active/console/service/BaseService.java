package com.rejoice.active.console.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rejoice.active.console.common.util.RejoiceUtil;

@Transactional
public abstract class BaseService<T>{  

    @Autowired
    private Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return this.mapper;
    }

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseService() {
        super();
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType ptype = (ParameterizedType) type;
        this.clazz = (Class<T>) ptype.getActualTypeArguments()[0];
    }

    /**
     * 根据id查询
     * 
     * @param id
     * @return
     */
    public T queryByID(Serializable id) {
    	  // 设置条件
        Example example = new Example(clazz);
        example.createCriteria().andEqualTo("id", id);
        return (T) this.getMapper().selectByExample(example);
    }

    /**
     * 查询所有数据
     * 
     * @return
     */
    public List<T> queryAll() {
        return this.getMapper().select(null);
    }

    /**
     * 根据条件查询
     * 
     * @param t
     * @return
     */
    public List<T> queryListByWhere(T t) {
        return this.getMapper().select(t);
    }

    /**
     * 查询数据总条数
     * 
     * @return
     */
    public Integer queryCount(T t) {
        return this.getMapper().selectCount(t);
    }
    

    /**
     * 根据条件分页查询
     * 
     * @param t
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryPageByWhere(T t, Integer page, Integer rows) {
        // 第一个参数是起始页，第二个参数是，页面显示的数据条数
        PageHelper.startPage(page, rows);
        List<T> list = this.getMapper().select(t);
        return new PageInfo<T>(list);
    }

    /**
     * 根据条件查询一条数据
     * 
     * @param t
     * @return
     */
    public T queryOne(T t) {
        return this.getMapper().selectOne(t);
    }

    /**
     * 保存
     * 
     * @param t
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    public void save(T t) throws Exception {
    	Date currentTime = new Date();
    	Field updateTime = ReflectionUtils.findField(t.getClass(), "updateTime");
    	updateTime.setAccessible(true);
    	updateTime.set(t,currentTime);
    	Field createTime = ReflectionUtils.findField(t.getClass(), "createTime");
    	createTime.setAccessible(true);
    	createTime.set(t,currentTime);
    
        this.getMapper().insert(t);
    }

    /**
     * 保存(忽略空字段）
     * 
     * @param t
     */
    public void saveSelective(T t) {
        this.getMapper().insertSelective(t);
    } 
 
    /**
     * 更新
     * 
     * @param t
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public void updateById(T t) throws Exception {
    	Field updateTime = ReflectionUtils.findField(t.getClass(), "updateTime");
    	updateTime.setAccessible(true);
    	updateTime.set(t, new Date());
        this.getMapper().updateByPrimaryKey(t);
    }

    /**
     * 更新（忽略空字段）
     * 
     * @param t
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    public void updateByIdSelective(T t) throws Exception {
    	Field updateTime = ReflectionUtils.findField(t.getClass(), "updateTime");
    	updateTime.setAccessible(true);
    	updateTime.set(t, new Date());
        this.getMapper().updateByPrimaryKeySelective(t);
    }

  /*  *//** 
     * 根据id删除
     * 
     * @param id
     *//*
    public void deleteById(Serializable id) {
        this.getMapper().deleteByPrimaryKey(id);
    }*/

    /**
     * 根据ids批量删除
     * 
     * @param ids
     */
    public void deleteByIds(List<Serializable> ids) {
        // 设置条件
        Example example = new Example(clazz);
        example.createCriteria().andIn("id", ids);
        // 根据条件删除
        this.getMapper().deleteByExample(example);
    }

    /**
     * 分页查询数据，排序
     * 
     * @param t
     * @param page
     * @param rows
     * @param order
     * @return
     * @throws Exception
     */
    public PageInfo<T> queryListByPageAndOrder(T t, Integer page, Integer rows, String order)
            throws Exception {
        // 加入分页
        PageHelper.startPage(page, rows);

        // 声明一个example
        Example example = new Example(this.clazz);
        if (StringUtils.isNotBlank(order)) {
            example.setOrderByClause(order);
        }

        // 如果条件为null，直接返回
        if (t == null) {
            List<T> list = this.getMapper().selectByExample(example);
            return new PageInfo<T>(list);
        }

        // 声明条件
        Criteria createCriteria = example.createCriteria();
        // 获取t的字段
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 设置为true，可以获取声明的私有字段的值
            field.setAccessible(true);
            if (field.get(t) != null) {
                // 非空的字段的值，加入到条件中
                createCriteria.andEqualTo(field.getName(), field.get(t));
            }
        }

        List<T> list = this.getMapper().selectByExample(example);
        return new PageInfo<T>(list);
    }
    
    
    
    /**
     * 分页查询数据，排序
     * 
     * @param t
     * @param page
     * @param rows
     * @param order
     * @return
     * @throws Exception
     */
    public PageInfo<T> queryListByPageAndOrder(T t, Integer page, Integer rows, String[] sorts,String[] orders)
            throws Exception {
        // 加入分页
        PageHelper.startPage(page, rows);

        // 声明一个example
        Example example = new Example(this.clazz);
        if (RejoiceUtil.isNotBlank(sorts) && RejoiceUtil.isNotBlank(orders)) {
        	StringBuilder sortSB = new StringBuilder();
        	for(int i = 0; i < sorts.length; i++){
        		sortSB.append(StringUtil.camelhumpToUnderline(sorts[i])).append(" ").append(orders[i]).append(",");
        	}
        	sortSB.substring(0, sortSB.length()-1);
            example.setOrderByClause(sortSB.toString());
        }

        // 如果条件为null，直接返回
        if (t == null) {
            List<T> list = this.getMapper().selectByExample(example);
            return new PageInfo<T>(list);
        }

        // 声明条件
        Criteria createCriteria = example.createCriteria();
        // 获取t的字段
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 设置为true，可以获取声明的私有字段的值
            field.setAccessible(true);
            if (field.get(t) != null) {
                // 非空的字段的值，加入到条件中
                createCriteria.andEqualTo(field.getName(), field.get(t));
            }
        }

        List<T> list = this.getMapper().selectByExample(example);
        return new PageInfo<T>(list);
    }

}