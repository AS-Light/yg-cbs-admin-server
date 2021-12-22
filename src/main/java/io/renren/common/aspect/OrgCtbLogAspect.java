/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.common.aspect;

import com.google.gson.Gson;
import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.HttpContextUtils;
import io.renren.common.utils.IPUtils;
import io.renren.modules.org_ctb.entity.OrgCtbLogEntity;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.org_ctb.service.OrgCtbLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * Ctb业务日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
public class OrgCtbLogAspect {
    @Autowired
    private OrgCtbLogService ctbLogService;

    @Pointcut("@annotation(io.renren.common.annotation.OrgCtbLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveOrgCtbLog(point, time);

        return result;
    }

    private void saveOrgCtbLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        OrgCtbLogEntity ctbLog = new OrgCtbLogEntity();
        OrgCtbLog ctblog = method.getAnnotation(OrgCtbLog.class);
        if (ctblog != null) {
            //注解上的描述
            ctbLog.setOperation(ctblog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        ctbLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = new Gson().toJson(args);
            ctbLog.setParams(params);
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        ctbLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = ((OrgCtbUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
        ctbLog.setUsername(username);

        ctbLog.setTime(time);
        ctbLog.setCreateDate(new Date());
        //保存系统日志
        ctbLogService.save(ctbLog);
    }
}
