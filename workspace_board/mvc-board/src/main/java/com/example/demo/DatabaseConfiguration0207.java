package com.example.demo;

import javax.sql.DataSource;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Log4j2
@Configuration // 스캔한다. - ApplicationContext container
//@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/application.yml")
//@MapperScan(basePackages = "com.example.demo.mapper")
public class DatabaseConfiguration0207 {
	// 메서드를 통해서 객체를 주입받는다 - 고급 - 다형성, 추상적인 코드 전개하기 - 유지보수할 때  수정할 코드가 줄어든다.
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	// 메서드를 통해서 객체를 주입하기 - DI(Dependency Injection) 와 AOP (Aspect Oriented Programming) - 로그, 로깅, 인증, 인가 ..
	// DataSource는 원격객체를 접근할 때 사용하는 클래스 이다.

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		log.info("datasource : {}", dataSource);
		return dataSource;
	}
	@Autowired // 자동으로 연관있는 클래스를 연결해준다. - 읽기와 쓰기가 가능하다.
	private ApplicationContext applicationContext; // 스프링 컨테이너가 관리하는 저장소 - 읽기와 쓰기 가능하다.

	// @Bean 어노테이션을 붙이면 스프링 컨테이너가(DispatchServlet) 객체 라이프사이클 관리 - 객체 주입
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		// POJO에서는 SqlSessionFactory -> SqlSessionFactoryBean
		// 역할 : 물리적으로 떨어져 있는 오라클 서버와 연결통로를 개설
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		// DataSource는 원격객체를 관리할 목적으로 제공된다.
		sqlSessionFactoryBean.setDataSource(dataSource);
		//classpath는 src/main/resources이고 해당 쿼리가 있는 xml 위치는 본인의 취향대로 위치키시고 그에 맞도록 설정해주면 된다.
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper0207/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	// POJO : SqlSession 대신 SqlSessionTemplate지원
	// SqlSessionFactoryBean먼저 생성된다. -> SqlSessionTemplate 생성된다.
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}	
  
}
