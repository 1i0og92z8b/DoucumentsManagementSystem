package com.bankcomm.novem.biz.demo;

import java.util.List;

import com.bankcomm.novem.bo.demo.DemoBo;
import com.bankcomm.novem.bo.demo.QueryDemoBo;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-8
 * 
 */
public interface IDemoBiz {
	
	/**
	 * 查询项目列表
	 * @param queryBo
	 * @return
	 */
	List<DemoBo> queryDemoList(final QueryDemoBo queryBo);
	
	/**
	 * 查询项目详情
	 * @param demoId
	 * @return
	 */
	DemoBo queryDemoDetail(final Integer demoId);
	
	/**
	 * 新增项目
	 * @param demoBo
	 */
	void addDemo(final DemoBo demoBo);
	
	/**
	 * 修改项目
	 * @param demoBo
	 */
	void updateDemo(final DemoBo demoBo);
	
	/**
	 * 删除项目
	 * @param demoBo
	 */
	void deleteDemo(final DemoBo demoBo);
	
	/**
	 * 根据Id删除项目
	 * @param demoIdList
	 */
	void deleteDemoByIdList(final List<Integer> demoIdList);
	
	/**
	 * 根据主键删除记录
	 * 
	 * @param demoId
	 *            主键
	 * @return 是否成功
	 */
	boolean deleteData(final long demoId);

	/**
	 * 更新记录
	 * 
	 * @param demoBo
	 *            DEMO实体
	 * @return 数量
	 */
	boolean updateData(final DemoBo demoBo);

	/**
	 * 新增记录
	 * 
	 * @param demoBo
	 *            DEMO实体
	 * @return 主键
	 */
	long insertData(final DemoBo demoBo);

	/**
	 * 根据编号查询
	 * 
	 * @param demoNo
	 *            DEMO编号
	 * @return 结果集
	 */
	List<DemoBo> selectData(final String demoNo);
}
