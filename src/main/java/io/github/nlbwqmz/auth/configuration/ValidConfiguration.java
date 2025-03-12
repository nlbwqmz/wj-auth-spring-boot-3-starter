package io.github.nlbwqmz.auth.configuration;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import io.github.nlbwqmz.auth.configuration.RateLimiterConfiguration.Strategy;
import io.github.nlbwqmz.auth.core.rateLimiter.RateLimiterCondition;
import io.github.nlbwqmz.auth.core.security.configuration.AlgorithmEnum;
import io.github.nlbwqmz.auth.core.security.configuration.TokenKeyConfiguration;
import io.github.nlbwqmz.auth.exception.rate.RateLimiterException;
import io.github.nlbwqmz.auth.exception.security.TokenFactoryInitException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 校验配置
 */
@Configuration
public class ValidConfiguration implements InitializingBean {

  private final RateLimiterCondition rateLimiterCondition;
  private final TokenKeyConfiguration tokenKeyConfiguration;
  private final AuthAutoConfiguration authAutoConfiguration;

  public ValidConfiguration(@Autowired(required = false) RateLimiterCondition rateLimiterCondition,
      @Autowired(required = false) TokenKeyConfiguration tokenKeyConfiguration,
      AuthAutoConfiguration authAutoConfiguration
  ) {
    this.rateLimiterCondition = rateLimiterCondition;
    this.tokenKeyConfiguration = tokenKeyConfiguration;
    this.authAutoConfiguration = authAutoConfiguration;
  }

  private void checkRateLimiterConfiguration() {
    RateLimiterConfiguration rateLimiter = authAutoConfiguration.getRateLimiter();
    if (rateLimiter.getEnable()) {
      if (rateLimiter.getThreshold() < 1) {
        throw new RateLimiterException(
            "The minimum rate limit threshold is 1, and the default is 100.");
      }
      if (rateLimiter.getStrategy() == Strategy.CUSTOM && rateLimiterCondition == null) {
        throw new RateLimiterException(
            "The rate limiter strategy is CUSTOM,so bean RateLimiterCondition is required.");
      }
    }
  }

  private void checkSecurityConfiguration() {
    SecurityConfiguration security = authAutoConfiguration.getSecurity();
    if (security.getEnable()) {
      AlgorithmEnum algorithm = security.getToken().getAlgorithm();
      Assert.notNull(algorithm, () -> new TokenFactoryInitException("Algorithm must be set."));
      Assert.notNull(tokenKeyConfiguration, () -> new TokenFactoryInitException("TokenKeyConfiguration must be set."));
      String algorithmName = algorithm.name();
      if (StrUtil.startWithIgnoreCase(algorithmName, "HS")) {
        Assert.notBlank(tokenKeyConfiguration.key(), () -> new TokenFactoryInitException("HS algorithm must set key."));
      } else if (StrUtil.startWithIgnoreCase(algorithmName, "RS")) {
        Assert.isTrue(
            StrUtil.isNotBlank(tokenKeyConfiguration.publicKey()) && StrUtil.isNotBlank(tokenKeyConfiguration.privateKey()),
            () -> new TokenFactoryInitException("RS algorithm must set public key and private key."));
      } else {
        throw new TokenFactoryInitException("Unsupported algorithms.");
      }
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    checkRateLimiterConfiguration();
    checkSecurityConfiguration();
  }
}
