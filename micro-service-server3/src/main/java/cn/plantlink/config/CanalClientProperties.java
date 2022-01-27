package cn.plantlink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author colddew
 * @Date 2022-01-26
 */
@Configuration
public class CanalClientProperties {

    @Value("${canal.zkServers}")
    private String zkServers;

    @Value("${canal.destination.example}")
    private String canalDestinationExample;

    @Value("${canal.destination.info}")
    private String canalDestinationInfo;

    @Value("${canal.client.switch}")
    private int canalClientSwitch;

    public String getZkServers() {
        return zkServers;
    }

    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }

    public String getCanalDestinationExample() {
        return canalDestinationExample;
    }

    public void setCanalDestinationExample(String canalDestinationExample) {
        this.canalDestinationExample = canalDestinationExample;
    }

    public String getCanalDestinationInfo() {
        return canalDestinationInfo;
    }

    public void setCanalDestinationInfo(String canalDestinationInfo) {
        this.canalDestinationInfo = canalDestinationInfo;
    }

    public int getCanalClientSwitch() {
        return canalClientSwitch;
    }

    public void setCanalClientSwitch(int canalClientSwitch) {
        this.canalClientSwitch = canalClientSwitch;
    }
}
