package io.github.nlbwqmz.auth.core.chain;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableSet;
import io.github.nlbwqmz.auth.common.AuthThreadLocal;
import io.github.nlbwqmz.auth.common.FilterRange;
import io.github.nlbwqmz.auth.common.SecurityInfo;
import io.github.nlbwqmz.auth.configuration.AuthAutoConfiguration;
import io.github.nlbwqmz.auth.configuration.XssConfiguration;
import io.github.nlbwqmz.auth.core.xss.XssRequestWrapper;
import io.github.nlbwqmz.auth.exception.xss.XssException;
import io.github.nlbwqmz.auth.utils.AuthCommonUtil;
import io.github.nlbwqmz.auth.utils.MatchUtils;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 魏杰
 * @since 0.0.1
 */
@Order(2)
@Component
public class XssAuthChain implements AuthChain {

  private final XssConfiguration xssConfiguration;
  private ImmutableSet<SecurityInfo> xssIgnored;
  private ImmutableSet<SecurityInfo> xssOnly;
  @Value("${server.servlet.context-path:}")
  private String contextPath;

  public XssAuthChain(AuthAutoConfiguration authAutoConfiguration) {
    this.xssConfiguration = authAutoConfiguration.getXss();
  }

  public void setXss(Set<SecurityInfo> xssSet, Set<SecurityInfo> xssIgnoredSet) {
    Set<String> only = xssConfiguration.getOnly();
    Set<String> ignored = xssConfiguration.getIgnored();
    if (CollUtil.isNotEmpty(only)) {
      xssSet.add(
          SecurityInfo.builder().patterns(AuthCommonUtil.addUrlPrefix(only, contextPath))
              .build());
    }
    if (CollUtil.isNotEmpty(ignored)) {
      xssIgnoredSet.add(SecurityInfo.builder()
          .patterns(AuthCommonUtil.addUrlPrefix(ignored, contextPath)).build());
    }
    xssOnly = ImmutableSet.copyOf(xssSet);
    xssIgnored = ImmutableSet.copyOf(xssIgnoredSet);
  }

  @Override
  public void doFilter(ChainManager chain) {
    HttpServletRequest request = AuthThreadLocal.getRequest();
    if ((xssConfiguration.getQueryEnable() || xssConfiguration.getBodyEnable()) && isDoXss(request)) {
      AuthThreadLocal.setRequest(new XssRequestWrapper(request, xssConfiguration.getQueryEnable(),
          xssConfiguration.getBodyEnable()));
    }
    chain.doAuth();
  }

  /**
   * 是否执行xss过滤
   *
   * @param request 请求
   */
  private boolean isDoXss(HttpServletRequest request) {
    if (request != null) {
      FilterRange defaultFilterRange = xssConfiguration.getFilterRange();
      String uri = request.getRequestURI();
      String method = request.getMethod();
      switch (defaultFilterRange) {
        case ALL:
          return !MatchUtils.matcher(xssIgnored, uri, method);
        case NONE:
          return MatchUtils.matcher(xssOnly, request.getRequestURI(), request.getMethod());
        default:
          throw new XssException("The xss configuration defaultFilterRange cannot match");
      }
    } else {
      return false;
    }
  }
}
