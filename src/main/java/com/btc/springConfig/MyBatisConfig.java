//package com.btc.springConfig;
///**
// * <p>Title: MyBatisConfig.java</p>
// * @Package com.hello.boot1.config
// * <p>Description: TODO(用一句话描述该文件做什么)</p>
// * @author guminglei
// * @since 2017年10月18日 下午4:06:40
// * @version V1.0
// */
//
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.wall.WallConfig;
//import com.alibaba.druid.wall.WallFilter;
//
//
//
///**
// * <p>Description: TODO(用一句话描述该文件做什么)</p>
// * @author guminglei
// * @version V1.0
// */
//@Configuration
//@MapperScan(basePackages = {"com.btc.mapper.*"}, sqlSessionFactoryRef = "boot1SqlSessionFactory")
//public class MyBatisConfig{
//
//    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
//    // 精确到目录，以便跟其他数据源隔离
//    static final String MAPPER_LOCATION ="classpath*:mapper/*.xml";
//
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String user;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClass;
//    @Value("${spring.datasource.initialSize}")
//    private int initialSize;
//    @Value("${spring.datasource.minIdle}")
//    private int minIdle;
//    @Value("${spring.datasource.maxActive}")
//    private int maxActive;
//    @Value("${spring.datasource.maxWait}")
//    private int maxWait;
//    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
//    private long timeBetweenEvictionRunsMillis;
//    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//    @Value("${spring.datasource.validationQuery}")
//    private String validationQuery;
//    @Value("${spring.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//    @Value("${spring.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//    @Value("${spring.datasource.testOnReturn}")
//    private boolean testOnReturn;
//    @Value("${spring.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${spring.datasource.filters}")
//    private String filters;
//    @Value("${spring.datasource.connectionProperties}")
//    private String connectionProperties;
//    @Autowired
//    private WallFilter wallFilter;
//
//    
//    @Bean(name = "dataSource")
//    public DataSource makeDataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(driverClass);
//        dataSource.setUrl(url);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);
//        dataSource.setInitialSize(initialSize);
//        dataSource.setMinIdle(minIdle);
//        dataSource.setMaxActive(maxActive);
//        dataSource.setMaxWait(maxWait);
//        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        dataSource.setValidationQuery(validationQuery);
//        dataSource.setTestWhileIdle(testWhileIdle);
//        dataSource.setTestOnBorrow(testOnBorrow);
//        dataSource.setTestOnReturn(testOnReturn);
//        dataSource.setPoolPreparedStatements(poolPreparedStatements);
//        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        try {
//            dataSource.setFilters(filters);
//        } catch (SQLException e) {
//            logger.info("carrierDruidDataSource filters Exceptions",e);
//        }
//
//        
//        List<com.alibaba.druid.filter.Filter> filters = new ArrayList<com.alibaba.druid.filter.Filter>();
//        filters.add(wallFilter);
//        dataSource.setProxyFilters(filters);
//        
//        dataSource.setConnectionProperties(connectionProperties);
//        return dataSource;
//    }
//   
//
//    @Bean(name = "wallConfig")
//    public WallConfig wallFilterConfig(){
//        WallConfig wc = new WallConfig ();
//        wc.setMultiStatementAllow(true);
//        return wc;
//    }
//
//    @Bean(name = "wallFilter")
//    @DependsOn("wallConfig")
//    public  WallFilter wallFilter(WallConfig wallConfig){
//        WallFilter wfilter = new WallFilter ();
//        wfilter.setConfig(wallConfig);
//        return wfilter;
//    }
//    @Bean(name = "bootTransactionManager")
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(makeDataSource());
//    }
//
//    @Bean(name="boot1SqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("dataSource") DataSource bootDataSource)
//            throws Exception {
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setCallSettersOnNulls(true);
//
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setConfiguration(configuration);
//        bean.setDataSource(bootDataSource);
//
//
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            Resource[] resources1 = resolver.getResources("classpath*:com/xy/ssm/mapper/bk/*.xml");
//            Resource[] resources2 = resolver.getResources(MAPPER_LOCATION);
//            int length = resources1.length;
//            resources1 = Arrays.copyOf(resources1,resources1.length+resources2.length);
//             System.arraycopy(resources2,0,resources1,length,resources2.length);
//            bean.setMapperLocations(resources1);
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//}
