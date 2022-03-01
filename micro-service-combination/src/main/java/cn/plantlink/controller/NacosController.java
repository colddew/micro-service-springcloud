package cn.plantlink.controller;

import cn.plantlink.service.EchoService;
import cn.plantlink.service.dubbo.IHelloService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NacosController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @DubboReference(group = "springcloud")
    private IHelloService iHelloService;

    @RequestMapping(value = "/restEcho/{string}", method = RequestMethod.GET)
    public String restEcho(@PathVariable String string) {
        return restTemplate.getForObject("http://server3/echo/" + string, String.class);
    }

    @RequestMapping(value = "/feignEcho/{string}", method = RequestMethod.GET)
    public String feignEcho(@PathVariable String string) {
        return echoService.echo(string);
    }

    /**
     * Seata接入步骤
     * 1）
     * 2）修改file.conf的数据库配置
     * 3）创建数据库seata并新建三张表branch_table、global_table、lock_table
     * 4）各业务库创建undo_log表，用于AT模式XID记录
     * 5）执行nacos-config.sh同步config文件到Nacos
     * 6）配置registry-conf并启动Seata Server
     * 7）各服务单独设置seata.tx-service-group（同service.vgroupMapping.xxx-group）等配置项
     * 8）启动服务注册到Seata协调者
     * pom引入依赖包，seata-spring-boot-starter的版本与Seata Server的版本一致
     * 9）启动类添加@EnableAutoDataSourceProxy，分布式事务方法添加@GlobalTransactional注解
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public String seata() throws Exception {
        return "seata";
    }

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable String name) {
        return iHelloService.sayHello(name);
    }
}