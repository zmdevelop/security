package cn.com.taiji.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * @description 一个自定义的filter，
 * 	必须包含authenticationManager,accessDecisionManager,securityMetadataSource三个属性，
    	我们的所有控制将在这三个类中实现
    	最核心的代码就是invoke方法中的InterceptorStatusToken token = super.beforeInvocation(fi);这一句，
    	即在执行doFilter之前，进行权限的检查，而具体的实现已经交给accessDecisionManager
 */
public class TaijiFilterSecurityInterceptor extends AbstractSecurityInterceptor
implements Filter {
	//与security-config.xml里的myFilter的属性securityMetadataSource对应，
    // 其他的两个组件，已经在AbstractSecurityInterceptor定义
	private FilterInvocationSecurityMetadataSource  fisMetadataSource;





	public FilterInvocationSecurityMetadataSource getFisMetadataSource() {
		return fisMetadataSource;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#getSecureObjectClass()
	 */
	@Override
	public Class<?> getSecureObjectClass() {
		 // 下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return fisMetadataSource;
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}

	private void invoke(FilterInvocation fi) throws IOException,
	ServletException {
		// object为FilterInvocation对象
		//super.beforeInvocation(fi);
		// 源码
		// 1.获取请求资源的权限
		// 执行
//		Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
//		Collection<ConfigAttribute> attributes = fisMetadataSource.getAttributes(fi);
////		// 2.是否拥有权限
//	this.getAccessDecisionManager().decide(authenticated, fi, attributes);
		Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
	HttpSession s=	fi.getRequest().getSession();
		if(authenticated==null&&s.getAttribute("SPRING_SECURITY_CONTEXT")!=null){
			SecurityContextHolder.setContext((SecurityContext) s.getAttribute("SPRING_SECURITY_CONTEXT"));
		}
	        InterceptorStatusToken token = super.beforeInvocation(fi);  
	        try {  
	            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }finally{  
	            super.afterInvocation(token,null);  
	        }  
	

	}

	public void init(FilterConfig config) throws ServletException {

	}


	public void setFisMetadataSource(FilterInvocationSecurityMetadataSource fisMetadataSource) {
		this.fisMetadataSource = fisMetadataSource;
	}
}
