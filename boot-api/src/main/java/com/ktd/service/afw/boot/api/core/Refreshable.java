package com.ktd.service.afw.boot.api.core;

import java.util.Map;

public interface Refreshable {

    void refresh(Map<String, String> props);
}
