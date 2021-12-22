package io.renren.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
    /**
     * 线程池：阿里不推荐 Executors.来创建线程池,而是推荐手动创建 ThreadPoolExecutor 这样更加直观,参数也可自定义,也会防止OOM
     * 保持时间0L
     */
    public static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("cbs_%d").build();
    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            4,
            Runtime.getRuntime().availableProcessors(),
            2,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            threadFactory,
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    public static final String KEY = "key";

    public static final String NAME = "name";

    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 超级管理员用户名
     */
    public static final String SUPER_ADMIN_NAME = "admin";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 业务框架类型
     */
    public enum Org {
        /**
         * 系统框架
         */
        SYS("sys"),
        /**
         * 进出口业务框架
         */
        CBS("cbs"),
        /**
         * 报关行
         */
        CTB("ctb");

        private String value;

        Org(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Org findByValue(String value) {
            for (Org org : Org.values()) {
                if (org.value.equals(value)) {
                    return org;
                }
            }
            return null;
        }
    }

}
