package cn.tsjcate.framework.web.helper;

import cn.tsjcate.framework.web.context.RequestContext;

/**
 * BaseHelper
 */
public class BaseHelper {
	
	/**
	 * 
	 * @param options
	 * @param defaultValue
	 * @return
	 */
	public String generateOptionsForSelect(String[][] options, String defaultValue) {
		StringBuffer sb = new StringBuffer(options.length * 35);
		for (String[] option : options) {
			sb.append("<option value=\"").append(option[0]).append("\"");
			if ((null != defaultValue) && (defaultValue.equals(option[0]))) {
				sb.append(" selected");
			}
			sb.append(">").append(option[1]).append("</option>");
		}
		return sb.toString();
	}
	//得到当前请求地址如http://localhost:8080/admin
	public final String getContextPath() {
		return RequestContext.getCurrentContext().getContextPath();
	}
	
}
