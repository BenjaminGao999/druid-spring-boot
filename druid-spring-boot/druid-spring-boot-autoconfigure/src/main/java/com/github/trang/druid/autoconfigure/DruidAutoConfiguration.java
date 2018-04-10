package com.github.trang.druid.autoconfigure;

import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.github.trang.druid.autoconfigure.properties.DruidDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.github.trang.druid.autoconfigure.properties.DruidDataSourceProperties.DruidConstants.*;

/**
 * Druid 连接池的自动配置
 *
 * @author trang
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties({DataSourceProperties.class, DruidDataSourceProperties.class})
@Import({DruidDataSourceConfiguration.class, DruidServletConfiguration.class, DruidStatConfiguration.class})
@Slf4j
public class DruidAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_STAT_FILTER_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConfigurationProperties(prefix = DRUID_STAT_FILTER_PREFIX)
    public StatFilter statFilter() {
        log.debug("druid stat-filter init...");
        return new StatFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_WALL_FILTER_PREFIX, name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = DRUID_WALL_CONFIG_PREFIX)
    public WallConfig wallConfig() {
        return new WallConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_WALL_FILTER_PREFIX, name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = DRUID_WALL_FILTER_PREFIX)
    public WallFilter wallFilter(WallConfig wallConfig) {
        log.debug("druid wall-filter init...");
        WallFilter filter = new WallFilter();
        filter.setConfig(wallConfig);
        return filter;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_CONFIG_FILTER_PREFIX, name = "enabled", havingValue = "true")
    public ConfigFilter configFilter() {
        log.debug("druid config-filter init...");
        return new ConfigFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_SLF4J_FILTER_PREFIX, name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = DRUID_SLF4J_FILTER_PREFIX)
    public Slf4jLogFilter slf4jLogFilter() {
        log.debug("druid slf4j-filter init...");
        return new Slf4jLogFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_LOG4J_FILTER_PREFIX, name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = DRUID_LOG4J_FILTER_PREFIX)
    public Log4jFilter log4jFilter() {
        log.debug("druid log4j-filter init...");
        return new Log4jFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_LOG4J2_FILTER_PREFIX, name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = DRUID_LOG4J2_FILTER_PREFIX)
    public Log4j2Filter log4j2Filter() {
        log.debug("druid log4j2-filter init...");
        return new Log4j2Filter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DRUID_COMMONS_LOG_FILTER_PREFIX, name = "enabled", havingValue = "true")
    @ConfigurationProperties(prefix = DRUID_COMMONS_LOG_FILTER_PREFIX)
    public CommonsLogFilter commonsLogFilter() {
        log.debug("druid commons-log-filter init...");
        return new CommonsLogFilter();
    }

}