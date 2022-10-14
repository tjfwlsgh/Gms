package com.lgl.gms.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.lgl.gms.webapi.common.mybatis.MyBatisSqlInterceptor;

/**
 * MyBatis 사용한 Database 연결설정
 */
@Configuration
// 2022.02.16 yhlee
// MapperScan에 아래와 같이 com.lgl.gms까지만 설정하면 mapper(dao)를 찾지 못해서
// 그 아래의 com.lgl.gms.webapi.*.persistence.dao 과 같이 설정함
//@MapperScan(value = "com.lgl.gms.webapi", sqlSessionFactoryRef = "apiSqlSessionFactory")
@MapperScan(value = "com.lgl.gms.webapi.*.persistence.dao", sqlSessionFactoryRef = "apiSqlSessionFactory")
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConfiguration {

    @Bean(name = "apiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "apiSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("apiDataSource") DataSource provDataSource,
            ApplicationContext applicationContext) throws java.lang.Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(provDataSource);
        
        // BaseModelInterceptor 장착해서 insert, update시 공통 컬럼의 값이 설정되도록 함.
        sqlSessionFactoryBean.setPlugins(new MyBatisSqlInterceptor());
        
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/**/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "apiSqlSessionTemplate")
    public SqlSessionTemplate parserSqlSessionTemplate(SqlSessionFactory parserSqlSessionFactory)
            throws java.lang.Exception {

        return new SqlSessionTemplate(parserSqlSessionFactory);
    }

    /**
     * 트랜잭션매니저,
     *
     * @return
     * @throws URISyntaxException
     * @throws GeneralSecurityException
     * @throws ParseException
     * @throws IOException
     */
    @Bean(name = "transactionManagerBatis")
    public PlatformTransactionManager transactionManagerBatis()
            throws URISyntaxException, GeneralSecurityException, ParseException, IOException {
        return new DataSourceTransactionManager(defaultDataSource());
    }

}
