package com.luckymiao.common;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class GlobalControllerFilter extends OncePerRequestFilter{

	private String encoding;  
    private boolean forceEncoding = false;  
  
    /** 
     * Set the encoding to use for requests. This encoding will be passed into a 
     * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call. 
         *  设置编码为request使用。这个编码将被传递给 
         *  javax.servlet.http.HttpServletRequest#setCharacterEncoding调用。 
     * <p>Whether this encoding will override existing request encodings 
     * (and whether it will be applied as default response encoding as well) 
     * depends on the {@link #setForceEncoding "forceEncoding"} flag. 
         *  这个编码是否覆盖现有请求编码（以及是否会被应用为默认响应编码）取决于“forceEncoding”的设置。 
     */  
    public void setEncoding(String encoding) {  
        this.encoding = encoding;  
    }  
  
    /** 
     * Set whether the configured {@link #setEncoding encoding} of this filter 
     * is supposed to override existing request and response encodings. 
         * 设置该过滤器的encoding配置是否应该覆盖现有的请求和响应编码。 
     * <p>Default is "false", i.e. do not modify the encoding if 
     * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()} 
     * returns a non-null value. Switch this to "true" to enforce the specified 
     * encoding in any case, applying it as default response encoding as well. 
         *  默认是“false”，即当javax.servlet.http.HttpServletRequest#getCharacterEncoding() 
         *  返回非空值时，不修改编码。将它切换到“true”，在任何情况下，都强制使用指定的编码， 
         *  也将它作为默认响应编码。 
     * <p>Note that the response encoding will only be set on Servlet 2.4+ 
     * containers, since Servlet 2.3 did not provide a facility for setting 
     * a default response encoding. 
         *  注意，响应编码只能在Servlet 2.4以上才会被设置， 
         *  因为Servlet 2.3没有提供一个工具来设置一个默认的响应编码。 
     */  
    public void setForceEncoding(boolean forceEncoding) {  
        this.forceEncoding = forceEncoding;  
    }  
  
    @Override  
    protected void doFilterInternal(  
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  
            throws ServletException, IOException {  
  
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {  
            request.setCharacterEncoding(this.encoding);  
            if (this.forceEncoding) {  
                response.setCharacterEncoding(this.encoding);  
            }  
        }  
        filterChain.doFilter(request, response);  
    }  
	
}
