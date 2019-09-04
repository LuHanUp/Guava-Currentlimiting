# 基于Guava RateLimiter实现接口限流(仅供学习使用)
## 本DEMO采用是拦截器以及AOP的方式(@RateLimit)实现对接口进行限流

*注意:如果要使用@RateLimit注解来实现对接口方法的限流,需要将src\main\java\com\luhan\config\WebmvcConfig.java中@EnableWebMvc和@Configuration注解给注释掉*  
*注意:@RateLimit可以添加在类上(如果添加在类上表示该类下的所有方法都会被限流)*

* 拦截器的方式会对所配置的路由规则接口进行拦截限流处理
* @RateLimit相比于拦截器更加的灵活,如果那个接口需要被限流处理,注解在该接口上添加@RateLimit注解即可
