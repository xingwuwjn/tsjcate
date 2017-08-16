package cn.tsjcate.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tsjcate.framework.base.context.SpringApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.tsjcate.framework.util.StringUtil;
import cn.tsjcate.framework.web.helper.BaseHelper;

/**
 * HelperInterceptor
 */
public class HelperInterceptor implements HandlerInterceptor {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		this.injectHelper(modelAndView, object);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		return true;
	}
	
	/**
	 * 将helper注入到Model中
	 * @param modelAndView
	 * @param object
	 */
	private void injectHelper(ModelAndView modelAndView, Object object) {
		if (null == modelAndView || null == modelAndView.getModel() || (!(object instanceof HandlerMethod))) {
			return;
		}

		HandlerMethod handlerMethod = (HandlerMethod) object;
		String simpleClassName = handlerMethod.getBeanType().getSimpleName();//获得触发此拦截器de类名称
		String controllerName = StringUtil.convertFirstChar2LowerCase(simpleClassName.replace("Controller", ""));
		String actionName = handlerMethod.getMethod().getName();//取得具体操作XxxAction中方法的Method对象

		String helperBeanId = controllerName + "Helper";//indexHelper

		modelAndView.getModel().put("controller", controllerName);
		modelAndView.getModel().put("action", actionName);
        //如果重定向则将model清除
		if (modelAndView.getViewName().startsWith("redirect:")) {
			modelAndView.getModel().clear();
		}

		BaseHelper helper = null;
		try {
			helper = SpringApplicationContext.getBean(helperBeanId);//获得对应的helper
		} catch (Exception e) {//如果没有获得此helper则获取baseSiteHelper
			//防御性容错
			helper = SpringApplicationContext.getBean("baseSiteHelper");
		}

		if (null != helper) {
			modelAndView.getModel().put("helper", helper);
		}
	}
}