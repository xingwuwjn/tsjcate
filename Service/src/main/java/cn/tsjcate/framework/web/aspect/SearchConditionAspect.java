package cn.tsjcate.framework.web.aspect;

import cn.tsjcate.framework.common.search.Condition;
import cn.tsjcate.framework.util.DateUtil;
import cn.tsjcate.framework.web.context.RequestContext;
import cn.tsjcate.framework.common.search.Search;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 *搜索的前置通知
 */
@Component(value = "searchConditionAspect")
public class SearchConditionAspect implements MethodBeforeAdvice {

	private final String SEARCH_PREFIX = "search_";
	
	/* (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */

	/**
	 *
	 * @param method
	 * @param args  参数数组
	 * @param obj controller实例
	 * @throws Throwable
	 */
	@Override
	public void before(Method method, Object[] args, Object obj) throws Throwable {
//		Map<String, Object> conditionMap = new HashMap<>();
		
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Search) {
				List<Condition> conditionList = new ArrayList<>();
//				Set<String> nameSet = new HashSet<>();
				Condition c;
				ServletRequest request = RequestContext.getCurrentContext().getRequest();
				@SuppressWarnings("rawtypes")
				Enumeration enu = request.getParameterNames();//得到所有参数
				while (enu.hasMoreElements()) {
					String name = (String)enu.nextElement();
					String value = request.getParameter(name);
					List<String> params = Arrays.asList("routerName", "methods", "confirmMethods", "gridId");
					if (params.contains(name)) {
						c = new Condition();
						c.setName(name);
						c.setValue(value);
//						conditionMap.put(name, value);
//						nameSet.add(c.getName());
						conditionList.add(c);
					}
					else if (name.toLowerCase().startsWith(SEARCH_PREFIX)
							&& (value != null && (!value.equals("")))) {
						c = new Condition();
						c.setName(name.split("_")[1]);
//						name.replace(SEARCH_PREFIX, "");
						if (value.indexOf(":") > 0 && value.length() == 19) {
							c.setValue(DateUtil.formatTimestampToDate(value));
//						} else if ("on".equals(value)) {
//							value = "1";
//							c.setValue(value);
						} else {
							c.setValue(value);
						}

//						conditionMap.put(name, value);
//						nameSet.add(c.getName());
						conditionList.add(c);
					}
				}
				if (conditionList.size() > 0) {
					((Search)args[i]).setConditionList(conditionList);//将查询条件绑定到search中去
				}
			}
		}
		
	}

}
