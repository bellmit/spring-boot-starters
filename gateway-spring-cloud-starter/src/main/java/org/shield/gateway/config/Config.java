package org.shield.gateway.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class Config {

    private List<String> excludes = new ArrayList<>();

    public Config() {}

    public Config(String excludes) {
        super();
        this.excludes = Arrays.asList(excludes.split(","));
    }

    public Config(List<String> excludes) {
        super();
        this.excludes = excludes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }
}
