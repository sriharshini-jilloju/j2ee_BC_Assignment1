//package org.digital.evidence.ejb.config;
//
//import java.util.Properties;
//
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
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
//
//@Configuration
//@ComponentScan(basePackages = {
//	    "org.digital.evidence.ejb.service",
//	    "org.digital.evidence.ejb.entity"
//	})
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "org.digital.evidence.ejb.dao")
//public class EJBAppConfig {
//
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
