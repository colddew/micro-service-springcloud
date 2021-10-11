package cn.plantlink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author duhanchen
 * @Date 2021-10-10
 */
@RefreshScope
@Configuration
public class DynamicProperties {

    @Value("${server.local.config}")
    private String serverLocalConfig;

    public String getServerLocalConfig() {
        return serverLocalConfig;
    }

    public void setServerLocalConfig(String serverLocalConfig) {
        this.serverLocalConfig = serverLocalConfig;
    }
}
