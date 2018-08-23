package spring.boot.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * @author 武海升
 * @date 2018/8/21 10:07
 * @desc
 *    Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 *    Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 *    Queue:消息的载体,每个消息都会被投到一个或多个队列。
 *    Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 *    Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 *    vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 *    Producer:消息生产者,就是投递消息的程序.
 *    Consumer:消息消费者,就是接受消息的程序.
 *    Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";


    public static final String FANOUT_EXCHANGE = "my-mq-fanout-exchange";


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
        FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
        HeadersExchange ：通过添加属性key-value匹配
        DirectExchange:按照routingkey分发到指定队列
        TopicExchange:多关键字匹配
       3.一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。 queueA 、queueB
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_A);
    }

    @Bean
    public DirectExchange oneToManyExchange() {
        return new DirectExchange(EXCHANGE_B);
    }



    /**
     * 获取队列A
     * @return
     */
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }
    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }
    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }


    @Bean
    public Binding defaultExchangeBinding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }

    @Bean
    public Binding defaultExchangeBindingB(){
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_B);
    }


    @Bean
    public Binding oneToManyExchangeBinding() {
        return BindingBuilder.bind(queueB()).to(oneToManyExchange()).with(RabbitConfig.ROUTINGKEY_B);
    }

    @Bean
    public Binding oneToManyExchangeBindingC(){
        return BindingBuilder.bind(queueC()).to(oneToManyExchange()).with(RabbitConfig.ROUTINGKEY_C);
    }


    /**
     * Fanout Exchange 广播模式
     */
    //配置fanout_exchange
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConfig.FANOUT_EXCHANGE);
    }

    //把所有的队列都绑定到这个交换机上去
    @Bean
    Binding bindingExchangeA(Queue queueA,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(Queue queueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueC).to(fanoutExchange);
    }





}
