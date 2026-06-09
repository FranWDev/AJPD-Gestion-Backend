package org.dubini.gestion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories(basePackages = "org.dubini.gestion.repository")
public class JdbcConfiguration extends AbstractJdbcConfiguration {
}
