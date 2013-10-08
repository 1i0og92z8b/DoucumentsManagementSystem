package com.bankcomm.novem.dao.demo;

import java.util.List;

import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.dao.ISingleTableDao;
import com.bankcomm.novem.entry.demo.DemoEntry;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-14
 * 
 */
public interface IDemoDao extends ISingleTableDao<DemoEntry> {
	
	/**
	 * 查询项目列表
	 * @param queryBo
	 * @return
	 */
	List<DemoEntry> queryDemoList(final QueryDemoBo queryBo);
	
	/**
	 * 查询项目详情
	 * @param demoId
	 * @return
	 */
	DemoEntry queryDemoDetail(final Integer demoId);
	
	
	/**
	 * 新增项目
	 * @param entry
	 * @return
	 */
	Integer addDemo(final DemoEntry entry);
	
	/**
	 * 修改项目
	 * @param entry
	 * @return
	 */
	Boolean updateDemo(final DemoEntry entry);
	
	/**
	 * 删除项目
	 * @param entry
	 * @return
	 */
	Boolean deleteDemo(final DemoEntry entry);
	
	/**
	 * 根据Id删除项目
	 * @param entry
	 * @return
	 */
	Boolean deleteDemo(final List<Integer> demoIdList);
	
	/**
	 * 根据编号查询
	 * 
	 * @param demoNo
	 *            编号
	 * @return 结果集
	 */
	List<DemoEntry> queryByNo(final String demoNo);

	/**
	 * 根据名称模糊查询
	 * 
	 * @param demoName
	 *            名称
	 * @return 结果集
	 */
	List<DemoEntry> queryByName(final String demoName);
}
