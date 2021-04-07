package idempotent.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("idempotent")
@EnableAspectJAutoProxy
public class ComponentScanConfiguration {
    
}