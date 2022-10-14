package com.lgl.gms.webapi.common.mybatis;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.extern.slf4j.Slf4j;
/* 
 * insert, update : BaseModel 타입에 대하여 로그인 사용자의 userId, workIp를 자동 설정
 * */
@Slf4j
@Intercepts(@Signature(type = Executor.class, method = "update", args={MappedStatement.class, Object.class}))
public class MyBatisSqlInterceptor implements Interceptor {
	
	@Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        // get sql
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // get parameter , this is the target object that you want to handle
        Object parameter = invocation.getArgs()[1];
        
		// log.info("===>>> BaseModelInterceptor > sqlCommandType : "+ sqlCommandType);
		// log.info("===>>> BaseModelInterceptor > parameter : "+ parameter);
        
        // parameter가 BaseModel인 경우 field값 초기화(sql 파라메터 세팅)
        if (parameter instanceof BaseModel) {
            //init
        	BaseModel baseModel = (BaseModel) parameter;
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            	// regNo와 workIp가 모두 null인경우만 초기화
            	if (StringUtils.isBlank(baseModel.getRegNo()) && 
            			StringUtils.isBlank(baseModel.getWorkIp())){
            		baseModel.initRegModel();
            	}
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            	if (StringUtils.isBlank(baseModel.getRegNo()) && 
            			StringUtils.isBlank(baseModel.getWorkIp())){
            		baseModel.initUpdModel();
            	}
            }
        } /*
        else{
            log.info("param: {}", parameter);
        }
        */

        return invocation.proceed();

    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
