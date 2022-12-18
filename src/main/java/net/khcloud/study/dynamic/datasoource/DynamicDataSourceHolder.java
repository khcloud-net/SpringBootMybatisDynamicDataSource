package net.khcloud.study.dynamic.datasoource;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicDataSourceHolder {

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static final String DB_MASTER = "master";

    public static final String DB_SLAVE="slave";

    /**
     * @Description: 获取线程的DbType
     * @Param: args
     * @return: String
     * @Author: Object
     * @Date: 2019年11月30日
     */
    public static String getDbType() {
        log.info("getDbType...");
        String db = contextHolder.get();
        if(db==null) {
            db = DB_MASTER;
        }
        return db;
    }
    /**
     * @Description: 设置线程的DbType
     * @Param: args
     * @return: void
     * @Author: Object
     * @Date: 2019年11月30日
     */
    public static void setDbType(String str) {
        log.info("所使用的数据源为:"+str);
        contextHolder.set(str);
    }
    /**
     * @Description: 清理连接类型
     * @Param: args
     * @return: void
     * @Author: Object
     * @Date: 2019年11月30日
     */
    public static void clearDbType() {
        log.info("clearDbType ...");
        contextHolder.remove();
    }
}

