package springBoot.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
//设置拦截的内容
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
								.addPathPatterns("/admin/**")
								.excludePathPatterns("/admin")
								.excludePathPatterns("/admin/login");	//由于没有加exclude时候，登陆时admin/login也会给排除掉，随意需要排除我们想要的
		
		super.addInterceptors(registry);
	}
	
}
