package cn.tsjcate.support.help.dao;

import java.util.List;

import cn.tsjcate.support.help.entity.HelpInfo;
import org.springframework.stereotype.Repository;

import cn.tsjcate.framework.common.persistence.BaseMybatisDAO;

/**
 * 帮助信息
 */
@Repository
public class HelpInfoDAO extends BaseMybatisDAO {

	private static final String MAPPER_NAMESPACE = "com.tortuousroad.support.help.entity.HelpInfoMapper";
	
	/**
	 * 查询用于在页面显示的帮助信息
	 * @return
	 */
	public List<HelpInfo> getListForShow() {
		return findAll(MAPPER_NAMESPACE + ".selectListForShow");
	}
}