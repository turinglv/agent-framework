package com.ktd.service.afw.agent;

import java.net.URL;

public class TestCommonUtil {

    private static final String JAR_FILE_NAME = "afw-agent-1.0.0.RELEASE-bin.jar";

    public static String getTestAgentPath() {
        URL url = AfwAgent.class.getClassLoader().getResource(JAR_FILE_NAME);
        if (url == null) {
            throw new IllegalArgumentException("没有找到Afw-agent.jar，请");
        }
        String fileName = url.getFile();
        if (fileName.contains(":") && (fileName.startsWith("/") || fileName.startsWith("\\"))) {
            fileName = fileName.substring(1);
        }
        return fileName;
    }
}
