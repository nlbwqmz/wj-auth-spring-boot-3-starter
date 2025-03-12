package io.github.nlbwqmz.auth.configuration;

import cn.hutool.core.util.BooleanUtil;
import io.github.nlbwqmz.auth.utils.BannerUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Auth 配置类
 *
 * @author 魏杰
 * @since 0.0.1
 */

@Getter
@Setter
@ConfigurationProperties(AuthAutoConfiguration.AUTH_PREFIX)
public class AuthAutoConfiguration implements InitializingBean {

  public final static String AUTH_PREFIX = "wj-auth";

  /**
   * banner
   */
  private Boolean banner = true;

  /**
   * 授权认证配置
   */
  @NestedConfigurationProperty
  private SecurityConfiguration security = new SecurityConfiguration();

  /**
   * xss配置
   */
  @NestedConfigurationProperty
  private XssConfiguration xss = new XssConfiguration();
  /**
   * 跨域配置
   */
  @NestedConfigurationProperty
  private CorsConfiguration cors = new CorsConfiguration();

  /**
   * 限流配置
   */
  @NestedConfigurationProperty
  private RateLimiterConfiguration rateLimiter = new RateLimiterConfiguration();

  @Override
  public void afterPropertiesSet() throws Exception {
    if (BooleanUtil.isTrue(banner)) {
      BannerUtil.printBanner();
    }
  }



}
