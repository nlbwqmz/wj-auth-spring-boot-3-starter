package io.github.nlbwqmz.auth.core.security.handler;

import io.github.nlbwqmz.auth.core.security.configuration.Logical;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 匿名
 *
 * @author 魏杰
 * @since 0.0.1
 */
public class AnonymizeInterceptorHandler implements InterceptorHandler {

  @Override
  public boolean authorize(HttpServletRequest request, HttpServletResponse response, String[] shouldPermission,
      Logical logical,
      Set<String> userPermission) {
    return true;
  }

  @Override
  public String authenticate(HttpServletRequest request, HttpServletResponse response,
      String header) {
    return null;
  }

  @Override
  public boolean isVerifyToken() {
    return false;
  }

  @Override
  public boolean isRefreshToken() {
    return false;
  }

  @Override
  public boolean isAuthorize() {
    return false;
  }
}
