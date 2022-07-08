package com.ktd.service.afw.boot.api.injector.match;

import com.ktd.service.afw.core.exception.AgentException;

public class NameMultiMatch implements ClassMatch {

    private String[] classNames;

    private NameMultiMatch(String... classNames) {
        this.classNames = classNames;
    }

    public static NameMultiMatch byNames(String... classNames) {
        if (classNames == null || classNames.length == 0) {
            throw new AgentException("ClassName不能为空！");
        }
        return new NameMultiMatch(classNames);
    }

    public String[] getClassNames() {
        return classNames;
    }
}
