package com.cheng.helper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

@Configuration
public class MybatisPlusConfig {
    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    @Profile({ "chengql", "test" }) // 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);// 执行最大时长，超过自动停止运行，有助于发现问题
        performanceInterceptor.setFormat(true);// <!--SQL是否格式化 默认false-->
        return performanceInterceptor;
    }

    //  /**
    //   * mybatis-plus SQL执行分析插件 作用是分析 处理 DELETE UPDATE 语句， 防止小白或者恶意 delete update
    //   * 全表操作！ 注意！该插件只用于开发环境，不建议生产环境使用。。。
    //   */
    //  @Bean
    //  public SqlExplainInterceptor sqlExplainInterceptor() {
    //      SqlExplainInterceptor SqlExplainInterceptor = new SqlExplainInterceptor();
    //      SqlExplainInterceptor.setStopProceed(false);
    //      return SqlExplainInterceptor;
    //  }
    /**
     * 全局配置注入LogicSqlInjector 不推荐yml里配置
     */
    @Bean
    public LogicSqlInjector logicSqlInjector() {
        LogicSqlInjector logicSqlInjector = new LogicSqlInjector();
        return logicSqlInjector;
    }

    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
        return optimisticLockerInterceptor;
    }

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        //      paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
        //          @Override
        //          public boolean doFilter(MetaObject metaObject) {
        //              MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
        //              // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
        //              if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
        //                  return true;
        //              } else if (ms.getId().contains("com.baomidou.springboot.mapper.TCrowdfundingMapper")) {
        //                  return true;
        //              }
        //              return false;
        //          }
        //      });
        return paginationInterceptor;
    }

}
