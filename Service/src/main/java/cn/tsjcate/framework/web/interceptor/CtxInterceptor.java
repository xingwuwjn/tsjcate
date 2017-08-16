package cn.tsjcate.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
public class CtxInterceptor implements HandlerInterceptor {

	public static final String CONTEXT_NAME = "ctx";
	
	public static final String SCHEME_SUFFEX = "://";
	
	public static final String SEPARATE_SERVER_PORT = ":";
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object args, ModelAndView modelAndView) throws Exception {
		if (modelAndView == null || modelAndView.getModel() == null) {
			return;
		}
		StringBuffer ctx = new StringBuffer();
		ctx.append(request.getScheme());//  http
		ctx.append(SCHEME_SUFFEX);//      ://
		ctx.append(request.getServerName());// ip地址（域名）
		if (request.getServerPort() != 80) {
			ctx.append(SEPARATE_SERVER_PORT);
			ctx.append(request.getServerPort());
		}
		ctx.append(request.getContextPath());//添加站点的根目录
		//到这里就是(如)    http://localhost:8080/admin
		modelAndView.getModel().put(CONTEXT_NAME, ctx.toString());

		//重定向操作特殊处理
		if (modelAndView.getViewName().startsWith("redirect:")) {
			modelAndView.getModel().clear();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		return true;
	}
	
}
