package io.github.nlbwqmz.auth.core.security.handler;

import io.github.nlbwqmz.auth.core.security.configuration.Logical;
import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证
 *
 * @author 魏杰
 * @since 0.0.1
 */
public class AuthenticateInterceptorHandler implements InterceptorHandler {

  @Override
  public boolean authorize(HttpServletRequest request, HttpServletResponse response, String[] shouldPermission,
      Logical logical,
      Set<String> userPermission) {
    return true;
  }

  @Override
  public String authenticate(HttpServletRequest request,
      HttpServletResponse response, String header) {
    return request.getHeader(header);
  }

  @Override
  public boolean isAuthorize() {
    return false;
  }
}
