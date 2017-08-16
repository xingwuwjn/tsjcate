package cn.tsjcate.admin.base.aspect;

import cn.tsjcate.admin.base.controller.BaseAdminController;
import cn.tsjcate.framework.common.page.PagingResult;
import cn.tsjcate.framework.common.search.Search;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Controller中方法拦截
 * 找到返回结果和参数相匹配的那个方法，在方法执行完，构造所对应的router
 */
@Component
@Aspect
public class ControllerAspect {

	@Around(value = "execution(public * cn.tsjcate..controller.*Controller.*(..))")
	public Object aroundControllerMethod(final ProceedingJoinPoint pjp) {
		try {
			Object returnVal = pjp.proceed();//返回方法（连接点）执行完成的结果（PagingResult)
//			MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
//			methodSignature.getMethod();
			for (Object obj : pjp.getArgs()) {//获得所有的参数并且遍历
				if (obj instanceof Search && returnVal instanceof PagingResult &&
						((Search)obj).containsRouterCall()) {
					((BaseAdminController)pjp.getTarget()).generateRouterCallButtons(
							(PagingResult)returnVal, (Search)obj);
				}
			}
			return returnVal;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;
	}

}
