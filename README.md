## idempotent
一个简单，灵活，扩展性强，可以自定义存储格式的 **幂等** 框架。

### 实现原理
基本原理是拿到每次请求的幂等值，存储下来，当相同幂等值的请求再次请求的时候。

会根据策略，决定是抛出异常，还是将上次执行的结果直接返回。

基于 Spring AOP，对代码最小侵入方式来实现幂等。使用注解的形式来实现幂等。

同时引入EL表达式，可以指定方法参数某一值作为幂等值。

### 项目结构
![image](https://user-images.githubusercontent.com/35193008/113846601-39ddf400-97c9-11eb-8f22-83bf8b94897d.png)

1. aspect： AOP环绕通知
2. config： 配置类
3. delay： 失败重试延迟队列
4. executor： 封装执行器
5. spel： el表达式
6. storage： 幂等存储类型（目前支持：redis，本地存储）

### 简单使用

只需要在需求保持幂等的方法上加上@Idempotent注解即可
````
  @Idempotent(id = "#id")
  public void testIdempotent(String id){
      System.out.println(id + "进入幂等拉~~~~~");
  }
````


### 扩展策略

1. 存储
> 默认基于Redis存储幂等值和放回结果。
> 
> 实现 IdempotenceStorage 可自定义存储方式。

2. 错误策略
> 1. 业务错误
> 业务错误目前默认是删除幂等值, 根据策略再决定是否重试。
> 
> 2. 机器宕机 
> 通过两段式设置过期时间来避免机器宕机导致幂等值已存在。
> 
> 3. 幂等框架异常
> 直接抛出异常错误。


### 流程图


![image](https://user-images.githubusercontent.com/35193008/112266923-aa582180-8caf-11eb-95c4-b74c64dab454.png)


