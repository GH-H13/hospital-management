package com.hospital;

import com.hospital.util.DBUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 医院住院管理系统 - 应用程序初始化类
 * 用于初始化数据库连接池
 */
@WebListener
public class HospitalApplication implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化数据库连接池
        DBUtil.init();
        System.out.println("数据库连接池已初始化");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 关闭数据库连接池
        DBUtil.shutdown();
        System.out.println("数据库连接池已关闭");
    }
}
