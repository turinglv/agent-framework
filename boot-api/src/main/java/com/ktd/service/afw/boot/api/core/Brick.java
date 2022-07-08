package com.ktd.service.afw.boot.api.core;

import com.ktd.service.afw.boot.api.command.Listenable;
import com.ktd.service.afw.core.api.Idrable;
import com.ktd.service.afw.core.api.Switchable;

public interface Brick extends Bootable, Refreshable, Metricable, Idrable, Switchable, Listenable {

}
