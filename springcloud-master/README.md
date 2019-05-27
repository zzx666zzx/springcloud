一、概念：

1、springCloud包含一些功能特性：

配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话

2、springCloud主要组件：

(1)、服务的注册与发现-------Eureka
(2)、远程调用消费者---------Feign与Ribbon
(3)、断路器-----------------Hystrix
(4)、注册网关---------------Zuul

3、Ribbon和Feign不同点。

(1)、启动类使用的注解不同，Ribbon用的是@RibbonClient，Feign用的是@EnableFeignClients。

(2)、服务的指定位置不同，Ribbon是在@RibbonClient注解上声明，Feign则是在定义抽象方法的接口中使用@FeignClient声明。

(3)、调用方式不同，Ribbon需要自己构建http请求，模拟http请求然后使用RestTemplate发送给其他服务，步骤相当繁琐。

  Feign则是在Ribbon的基础上进行了一次改进，采用接口的方式，将需要调用的其他服务的方法定义成抽象方法即可，

  不需要自己构建http请求。不过要注意的是抽象方法的注解、方法签名要和提供服务的方法完全一致。

4、@EnableEurekaServer与@EnableDiscoveryClient注解的区别：

@EnableEurekaServer是基于 spring-cloud-netflix依赖，只能为eureka作用，是专门给Eureka用的 

@EnableDiscoveryClient是基于 spring-cloud-commons依赖，并且在classpath中实现，是给比如zookeeper、consul使用的， 

旧版本的@EnableEurekaServer的源码上面也是也是有@EnableDiscoveryClient注解的。 

@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。

5、Hystrix实现限流、降级、熔断：相关配置配置在properties文件中
#开启熔断机制
feign.hystrix.enabled=true
## hystrix相关配置
## hystrix默认会读取classpath下的config.properties文件，application会覆盖config.properties中的
#配置线程池大小,默认值10个
hystrix.threadpool.default.coreSize=10
#配置线程值等待队列长度,默认值:-1,建议值:-1,表示不等待直接拒绝,
#测试表明线程池使用直接决绝策略+ 合适大小的非回缩线程池效率最高.所以不建议修改此值。当使用非回缩线程池时，
hystrix.threadpool.default.maxQueueSize=200
#队列大小拒绝阈值
hystrix.threadpool.default.queueSizeRejectionThreshold=2
#是否开启超时
hystrix.command.default.execution.timeout.enabled=true
#使用命令调用隔离方式,默认:采用线程隔离,ExecutionIsolationStrategy.THREAD
hystrix.command.default.execution.isolation.strategy=THREAD
#使用线程隔离时，调用超时时间，默认:1秒
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=5000
#使用线程隔离时，是否对命令执行超时的线程调用中断（Thread.interrupt()）操作.默认:true
hystrix.command.default.execution.isolation.thread.interruptOnTimeout=true
hystrix.command.default.execution.isolation.thread.interruptOnFutureCancel=false
#使用信号量隔离时，命令调用最大的并发数,默认:10
hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests=2
#是否开启降级
hystrix.command.default.fallback.enabled=true
#使用信号量隔离时，命令fallback(降级)调用最大的并发数,默认:10
hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests=10
#开启熔断器
hystrix.command.default.circuitBreaker.enabled=true
#断路器强制开启，是否强制开启熔断器阻断所有请求,默认:false,不开启。置为true时，所有请求都将被拒绝，直接到fallback
hystrix.command.default.circuitBreaker.forceOpen=false
#断路器强制关闭，是否允许熔断器忽略错误,默认false, 不开启
hystrix.command.default.circuitBreaker.forceClosed=false
#滑动窗口的大小，默认为20
hystrix.command.default.circuitBreaker.requestVolumeThreshold=4
#错误率，默认50%
hystrix.command.default.circuitBreaker.errorThresholdPercentage=50
#过多长时间，熔断器再次检测是否开启，默认为5000，即5s钟
hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=10000

hystrix.command.default.metrics.rollingStats.timeInMilliseconds=5000
hystrix.command.default.metrics.rollingStats.numBuckets=10
#是否开启监控统计功能,默认:true
hystrix.command.default.metrics.rollingPercentile.enabled=true
hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds=60000
hystrix.command.default.metrics.rollingPercentile.numBuckets=6
hystrix.command.default.metrics.rollingPercentile.bucketSize=100
hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds=500


二：案例各个服务端口：
1、注册服务：eureka_server:8090   http://localhost:8090/           
2、服务提供者1：eureka_client01:8091   
接口：
http://localhost:8091/hi与http://localhost:8091/hi1?name=zzx
3、服务提供者2：eureka_providor2:8092  
接口：
http://localhost:8092/hi与http://localhost:8092/hi1?name=zzx   
http://localhost:8092/timeout与http://localhost:8092/exception

说明：服务提供者2个是为了模拟集群，同时测试ribbon和feign的负载均衡功能
同时服务提供者2增加了额外两个接口是测试hytrix的超时、异常降级。
其中在feignhystrix服务中的配置文件还包含了
1.Circuit Breaker ：熔断器，熔断只是作用在服务调用这一端，只需改consumer端
2.downgrade：降级，fallback
3.Isolation：限流(隔离)
4.Hystrix metrics：容错计数
等一些配置


4、ribbon消费者(调用生产者接口)+负载均衡：ribbon:8093
接口：http://localhost:8093/hi/zzx

5、ribbon消费者(调用生产者接口)+服务降级(hystrix):ribbonhystrix:8096
接口：http://localhost:8096/timeout与http://localhost:8096/exception

6、feign消费者(调用生产者接口)+负载均衡:feigh:8095
接口：http://localhost:8095/hi?name=zzx

7、feign消费者(调用生产者接口)+服务降级(hystrix):feignhystrix:8094
接口：http://localhost:8094/timeout与http://localhost:8094/exception


8、zuul网关路由控制:zuul:8100
在项目zuul的application.properties文件中配置了两个映射服务分别为：
zuul.routes.service-feign=/api-feign/** 
service-feign---------对应上面6、fegin:8095服务，可以使用
接口：http://localhost:8100/api-feign/hi?name=zzx 调用fegin:8095服务中的 http://localhost:8095/hi?name=zzx 接口
http://localhost:8100/api-feign/zuul

zuul.routes.service-ribbon=/api-ribbon/**
service-ribbon--------对应上面4、ribbon:8093服务，可以使用
接口：http://localhost:8100/api-ribbon/hi/zzx 调用ribbon:8093服务中的 http://localhost:8093/hi/zzx 接口


同时feign和ribbon远程调用的服务也会暴露：
取默认服务id：service-hi
接口：
http://localhost:8100/service-hi/hi1?name=zzx
http://localhost:8100/service-hi/hi
要想屏蔽远程访问的情况使用：
使用zuul.ignored-services=service-hi
再次访问：
http://localhost:8100/service-hi/hi1?name=zzx
http://localhost:8100/service-hi/hi
就会报错！

关于zuul中的application.properties文件配置说明：
(1)、网关路由配置情况
方式一：
zuul.routes.api-feign.path=/api-feign/**
zuul.routes.api-feign.serviceId=service-feign
方式二：
zuul.routes.service-feign=/api-feign/**
方式一和二效果相同
同时可以配置多个网关路由。

(2)、屏蔽生产者接口：
zuul.ignored-services=service-hi
其中service-hi为生产者的spring.application.name服务名

(3)、路由匹配规则：
通配符	含义			举例			解释
?	匹配任意单个字符	/feign-consumer/?	匹配/feign-consumer/a,/feign-consumer/b,/feign-consumer/c等
*	匹配任意数量的字符	/feign-consumer/*	匹配/feign-consumer/aaa,feign-consumer/bbb,/feign-consumer/ccc等，无法匹配/feign-consumer/a/b/c
**	匹配任意数量的字符	/feign-consumer/*	匹配/feign-consumer/aaa,feign-consumer/bbb,/feign-consumer/ccc等，也可以匹配/feign-consumer/a/b/c

(4)、屏蔽某个接口：
比如需要屏蔽接口：http://localhost:8100/api-feign/zuul 则设置：
zuul.ignored-patterns=/**/zuul/**

(5)、统一加访问前缀:
zuul.prefix=/zzx
使用网关调用接口时，全部加上 http://localhost:8100/zzx 访问

(6)、其它情况查看properties文件中的详细介绍


9、配置中心：config_server:8097

(1)、介绍：在spring cloud项目中，都会配置一个配置中心去管理各个服务的配置文件，而往往配置文件不会放到本地配置中心，一般都会放到git上

(2)、过程：各个服务的配置文件------->配置中心配置-------->存放在git上的配置中心上

(3)、使用：config_server项目使用git作为配置中心，
首先需要注册一个github,并且新建一个project，增加配置文件
这里我的github是：https://github.com/zzx666zzx/springcloud-config-server.git
里面有个文件夹为：test,里面两个文件夹为：application-dev.yml，application-test.yml
(4)、配置：config_server项目里的application.properties文件中有各个参数说明，其中用户名和密码填写自己的
需要注意的就是访问方法：以下是几种访问方法的介绍
#在浏览器中输入如下URL，可以访问到配置文件
#/{application}/{profile}/{label}
#/{application}-{profile}.yml
#/{label}/{application}-{profile}.yml
#/{application}-{profile}.properties
#/{label}/{application}-{profile}.properties

#下面通过具体例子说明以上url的意思。如果我们的配置文件名称cloud-config-simple2.yml，则其和URL中各个字段对应的值为:
#application: cloud-config
#profile: simple2
#label: master

使用测试接口：
http://localhost:8097/application/dev/master
http://localhost:8097/application/test/master



10、配置中心客户端(调用配置中心config_server)：config_test:8099

这里需要注意一点将application.properties文件重命名为bootstrap.properties,
配置详情在bootstrap.properties文件中有介绍
接口：http://localhost:8099/config


11、案例源码路径：

https://github.com/zzx666zzx/springcloud.git
