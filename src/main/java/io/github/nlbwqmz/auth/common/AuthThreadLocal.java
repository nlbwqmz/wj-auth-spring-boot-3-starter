package io.github.nlbwqmz.auth.common;

import java.util.Date;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 线程变量
 *
 * @author 魏杰
 * @since 0.0.1
 */
public class AuthThreadLocal {

  private static final ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();
  private static final ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<>();
  private static final ThreadLocal<String> subjectLocal = new ThreadLocal<>();
  private static final ThreadLocal<Long> expireLocal = new ThreadLocal<>();
  private static final ThreadLocal<Date> expireDateLocal = new ThreadLocal<>();

  public static HttpServletRequest getRequest() {
    return requestLocal.get();
  }

  public static void setRequest(HttpServletRequest request) {
    requestLocal.set(request);
  }

  public static HttpServletResponse getResponse() {
    return responseLocal.get();
  }

  public static void setResponse(HttpServletResponse response) {
    responseLocal.set(response);
  }

  public static String getSubject() {
    return subjectLocal.get();
  }

  public static void setSubject(String subject) {
    subjectLocal.set(subject);
  }

  public static long getExpire() {
    return expireLocal.get();
  }

  public static void setExpire(long expire) {
    expireLocal.set(expire);
  }

  public static Date getExpireDate() {
    return expireDateLocal.get();
  }

  public static void setExpireDate(Date expireDate) {
    expireDateLocal.set(expireDate);
  }

  public static void removeAll() {
    requestLocal.remove();
    responseLocal.remove();
    subjectLocal.remove();
    expireLocal.remove();
    expireDateLocal.remove();
  }
}
