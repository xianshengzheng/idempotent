package idempotent;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("idempotent")
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        //或者直接扫描com.linkedbear.spring.annotation.c_scan包
        Main main = (Main) ctx.getBean("main");
        main.testIdempotent("aaa");
        main.testIdempotent("aaa");

    }

    @Idempotent(id = "#id")
    public void testIdempotent(String id){
        System.out.println(id + "进入幂等拉~~~~~");
    }
}
