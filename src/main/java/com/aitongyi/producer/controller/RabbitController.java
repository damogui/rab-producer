/**
 * TODO
 * 
 */
package com.aitongyi.producer.controller;

import com.aitongyi.producer.Model.UserCustomer;
import com.aitongyi.producer.service.RabbitServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hushuang
 * 
 */
@Controller
@RequestMapping("/rabbit")
public class RabbitController {
	private static Logger log = LoggerFactory.getLogger(RabbitController.class);

	@Autowired
	private RabbitServiceImpl rabbitService;

	@RequestMapping(value = "/setMessage", method = RequestMethod.POST)
	@ResponseBody
	public String setMessage(String msg, String type) {
		log.info("rabbitmq--收到待发送消息: type[{}]-msg[{}]", type, msg);
		rabbitService.setMessage(msg, type);
		return "{\"success\":1}";
	}

    @RequestMapping(value = "/setObj", method = RequestMethod.POST)
    @ResponseBody
    public String setObj(String msg, String type) throws IOException {
        /**
         * 创建连接连接到MabbitMQ
         */
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("127.0.0.1");
        factory.setUsername("test");
        factory.setPassword("1234");
        factory.setVirtualHost("test");
        //创建一个连接
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建一个频道
        Channel channel = connection.createChannel();
        //指定一个队列
        //设置队列的属性第一个参数为队列名。第二个参数为是否创建一个持久队列，第三个是否创建一个专用的队列，
        //第四个参数为是否自动删除队列，第五个参数为其他属性（结构参数）
        channel.queueDeclare("test", false, false, false, null);
        //发送的消息

        List<UserCustomer> list=new ArrayList<UserCustomer>();
        for (int i=1;i<10;i++){

            UserCustomer userCustomer=new UserCustomer();
            userCustomer.setName(String.format("名字%s",i));
            userCustomer.setAge(18);
            userCustomer.setAddress("地址"+i);
            userCustomer.setPhone("我是电话"+i);
            list.add(userCustomer);
        }





        String message ="开始发送消息"; //"hello world!+开始运行实例";
        //一、往队列中发出一条消息
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());//发送文字
        //二、往队列中传递一个对象
        // channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(userCustomer));//发送对象

        //三、往队列中传递一个对象（json）通过ObjectMapper

        //将Java对象匹配JSON结构
//        SimpleMessageConverter
        ObjectMapper mapper=new ObjectMapper();
        String message2=mapper.writeValueAsString(list);
        //channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        channel.basicPublish("", "test", null, message2.getBytes());//发送对象



        System.out.println("Sent '" + message + "'");
        //关闭频道和连接
        channel.close();
        connection.close();
        return "{\"success\":1}";
    }





}
