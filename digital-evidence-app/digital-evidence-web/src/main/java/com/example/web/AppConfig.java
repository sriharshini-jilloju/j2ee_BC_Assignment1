//package com.example.web;
//
//import java.util.Properties;
//
//import javax.annotation.PostConstruct;
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jndi.JndiTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//@Configuration
//@ComponentScan(basePackages = {
//	    "com.example.web",
//	    "org.digital.evidence.ejb.service",
//	    "org.digital.evidence.ejb.dao",
//	    "org.digital.evidence.ejb.entity"
//	})@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "org.digital.evidence.ejb.dao")
//public class AppConfig {
//
//	@Autowired
//	RequestMappingHandlerMapping handlerMapping;
//
//	@PostConstruct
//	public void showMappings() {
//		handlerMapping.getHandlerMethods().forEach((info, method) -> {
//			System.out.println("Mapped: " + info + " -> " + method);
//		});
//	}
//	
//	@Bean
//	public DataSource dataSource() throws NamingException {
//		JndiTemplate jndi = new JndiTemplate();
//		return (DataSource) jndi.lookup("java:/jdbc/EvidenceDS");
//	}
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//		emf.setDataSource(dataSource);
//		emf.setPackagesToScan("org.digital.evidence.ejb.entity");
//		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//		Properties props = new Properties();
//		props.setProperty("hibernate.hbm2ddl.auto", "none");
//		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//		props.setProperty("hibernate.show_sql", "true");
//		props.setProperty("hibernate.format_sql", "true");
//		emf.setJpaProperties(props);
//
//		return emf;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		return new JpaTransactionManager(emf);
//	}
//
//}

// File: web-module/src/main/java/com/example/web/AppConfig.java
package com.example.web;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//import org.digital.evidence.ejb.repository.EvidenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.digital.evidence.ejb.repository", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class AppConfig {

	@Resource(lookup = "java:/jdbc/EvidenceDS")
	private DataSource dataSource;

	// JNDI lookup of WildFly-managed JTA datasource
	@Bean
	public DataSource dataSource() throws NamingException {
		JndiTemplate jndi = new JndiTemplate();
		DataSource ds = (DataSource) jndi.lookup("java:/jdbc/EvidenceDS");
		System.out.println("DataSource created: " + ds);
		return ds;
	}
	
	
	// EntityManagerFactory configured for JTA transactions
	@Bean(name = "entityManagerFactory")
	@DependsOn("dataSource")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
		System.out.println(">>> Creating entityManagerFactory with dataSource = " + dataSource);
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setJtaDataSource(dataSource);
		emf.setPackagesToScan("org.digital.evidence.ejb.entity");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties props = new Properties();
		props.put("hibernate.transaction.jta.platform",
				"org.hibernate.engine.transaction.jta.platform.internal.JBossAppServerJtaPlatform");
		props.put("hibernate.hbm2ddl.auto", "update"); // or update if you want schema auto update
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");
		emf.setJpaProperties(props);

		return emf;
	}

	// JTA transaction manager to integrate with WildFly
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		return new JtaTransactionManager();
	}
	
//	@Bean
//	@DependsOn("entityManagerFactory")
//	public EvidenceRepo evidenceRepo(EntityManagerFactory emf) {
//	    JpaRepositoryFactory factory = new JpaRepositoryFactory(emf.createEntityManager());
//	    return factory.getRepository(EvidenceRepo.class);
//	}
//

	@EventListener
	public void onContextRefresh(ContextRefreshedEvent event) {
		ApplicationContext ctx = event.getApplicationContext();
		System.out.println("==== Spring Beans ====");
		for (String name : ctx.getBeanDefinitionNames()) {
			try {
				Object bean = ctx.getBean(name);
				System.out.println(name + " -> " + bean.getClass().getName());
			} catch (Exception e) {
				System.out.println(name + " -> ERROR: " + e.getMessage());
			}
		}
		System.out.println("======================");
	}
}
