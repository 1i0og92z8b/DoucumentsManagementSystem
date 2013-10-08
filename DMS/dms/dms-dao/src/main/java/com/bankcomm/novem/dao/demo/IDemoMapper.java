package com.bankcomm.novem.dao.demo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.demo.QueryDemoBo;
import com.bankcomm.novem.entry.demo.DemoEntry;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-8
 * 
 */
@Repository
public interface IDemoMapper {

	/**
	 * 根据名称模糊查询
	 * 
	 * @param demoName
	 *            开始日期
	 * @return 返回结果集
	 */
//	@Select("SELECT DEMO_ID,DEMO_NO,DEMO_NAME FROM db2idms.DEMO WHERE DEMO_NAME LIKE '%${demoName}%'")
//	List<DemoEntry> queryByName(@Param("demoName") final String demoName);
	
	List<DemoEntry> queryByName(final String demoName);
	
	List<DemoEntry> queryDemoList(final QueryDemoBo queryBo);
	
	int queryDemoCount(final QueryDemoBo queryBo);
	
//	Boolean deleteDemo(final Map<String,List<Integer>> map);
	Boolean deleteDemo(final List<Integer> demoIdList);

}
