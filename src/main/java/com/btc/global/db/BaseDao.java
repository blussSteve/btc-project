package com.btc.global.db;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface BaseDao<T> {
	 /**
     * 添加数据
     *
     * @param obj  泛型对象
     */
    public int insert(T obj);
    
    /**
     * 批量添加数据
     *
     * @param List<T> list 对象集合
     */
    public int batchInsert(@Param(value="list")  List<T> list);

    /**
     * 根据主键删除数据
     *
     * @param id  主键
     */
    public int delete(Long id);
    
    /**
     * 根据不同参数删除
     * @param params 参数
     * @return
     */
    public int deleteByMap(Map<String, Object> params);
    
    /**
     * 根据不同参数删除
     * @param params 参数
     * @return
     */
    public int deleteBatchById(@Param(value="list") String[] list);

    /**
     * 通过id修改数据
     *
     * @param obj 泛型对象
     */
    public int updateAll(T obj);
    
    
    /**
     * 通过id修改数据
     *
     * @param obj 泛型对象
     */
    public int updateNotNull(T obj);
    
    /**
     * 根据id查询
     *
     * @param id 主键
     * @return T 泛型对象
     */
    public T findById(long id);

    /**
     * 根据条件查询
     *
     * @param params  查询参数
     * @return T 泛型对象
     */
    public List<T> find(Map<String, Object> params);

 
    /**
     * 根据条件查询
     *
     * @param params
     * @return
     */
    public List<T> findList(Map<String, Object> params);
    
    /**
     * 分页查询获取list集合
     *
     * @param params
     * @return
     */
    public List<T> findPageList(Map<String, Object> params);

    /**
     * 分页查询获取条数
     *
     * @param params 查询对象
     * @return Integer 条数
     */
    public Integer findPageListCount(Map<String, Object> params);
    
    /**
     * 查询所有数据
     *
     * @param params
     * @return
     */
    public List<T> findAll(Map<String, Object> params);

}
