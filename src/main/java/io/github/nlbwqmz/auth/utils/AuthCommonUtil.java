package io.github.nlbwqmz.auth.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthCommonUtil {

  /**
   * 数组不为空，且每一个元素都不为空
   */
  public static boolean isAllNotBlank(String[] array) {
    if (ArrayUtil.isEmpty(array)) {
      return false;
    }
    for (String item : array) {
      if (StrUtil.isBlank(item)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 集合不为空，且每一个元素都不为空
   */
  public static boolean isAllNotBlank(Collection<String> collection) {
    if (CollUtil.isEmpty(collection)) {
      return false;
    }
    for (String item : collection) {
      if (StrUtil.isBlank(item)) {
        return false;
      }
    }
    return true;
  }

  public static Set<String> addUrlPrefix(Set<String> set, String prefix) {
    prefix = Optional.ofNullable(prefix).orElse("");
    if (CollUtil.isEmpty(set)) {
      return CollUtil.newHashSet();
    }
    Set<String> result = new HashSet<>();
    for (String item : set) {
      if (item.startsWith(StrUtil.SLASH)) {
        result.add(prefix + item);
      } else {
        result.add(prefix + StrUtil.SLASH + item);
      }
    }
    return result;
  }

  public static boolean containsIgnoreCase(Collection<String> collection, String target) {
    if (CollUtil.isEmpty(collection)) {
      return false;
    }
    for (String str : collection) {
      if (str.equalsIgnoreCase(target)) {
        return true;
      }
    }
    return false;
  }

}
