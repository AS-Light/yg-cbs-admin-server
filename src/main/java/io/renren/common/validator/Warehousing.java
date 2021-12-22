package io.renren.common.validator;

import io.renren.common.exception.RRException;

/**
 * @author chenning
 * @description 仓储数据效验
 * @date 2020/3/5 16:40
 */
public abstract class Warehousing {
    /**
     * @description 小于异常
     * @author chenning
     * @date 2020/3/5 16:41
     */
    public static void isStatusLessThan(Integer status, Integer value, String message) {
        if (status < value) {
            throw new RRException(message);
        }
    }

    /**
     * @description 大于异常
     * @author chenning
     * @date 2020/3/5 16:41
     */
    public static void isStatusMoreThanThe(Integer status, Integer value, String message) {
        if (status >= value) {
            throw new RRException(message);
        }
    }
}
