/**
 * TODO
 * 
 */
package com.aitongyi.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author hushuang
 *
 */
@Service("rabbitService")
public class RabbitServiceImpl {
	private static Logger log = LoggerFactory.getLogger(RabbitServiceImpl.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 发送消息
	 * @param msg 消息内容
	 * @param routingKey 路由关键字
	 * void
	 */
	public void setMessage(String msg, String routingKey) {
		rabbitTemplate.convertAndSend(routingKey, msg);
		log.info("rabbitmq--发送消息完成: routingKey[{}]-msg[{}]", routingKey, msg);
	}


    @Bean
    public RabbitTemplate rabbitTemplate2() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    //以下配置RabbitMQ消息服务
    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("1234");
        return connectionFactory;
    }



    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
