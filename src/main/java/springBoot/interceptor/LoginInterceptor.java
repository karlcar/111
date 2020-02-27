package springBoot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/admin");
			return false;
		}
		return true;		//直接访问admin下面例如blog页面，会直接拦截并重定向会admin页面，这里还需要设置拦截的配置，是拦截admin下面的
	}
	
}
