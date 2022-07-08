package com.ktd.service.afw.boot.api.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AgentThreadFactory implements ThreadFactory {

  private final AtomicInteger threadNumber = new AtomicInteger(1);

  private final String namePrefix;

  private final int priority;

  private final boolean daemon;

  private final ThreadGroup group;

  public AgentThreadFactory(String namePrefix) {
    this(namePrefix, null, null, null);
  }

  public AgentThreadFactory(String namePrefix, Integer priority, Boolean daemon,
      ThreadGroup group) {
    this.namePrefix = namePrefix;
    if (priority == null) {
      priority = Thread.NORM_PRIORITY;
    }
    this.priority = priority;
    if (daemon == null) {
      daemon = false;
    }
    this.daemon = daemon;
    if (group == null) {
      SecurityManager s = System.getSecurityManager();
      group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }
    this.group = group;
  }

  public Thread newThread(Runnable r) {
    Thread newThread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
    newThread.setDaemon(this.daemon);
    newThread.setPriority(this.priority);
    return newThread;
  }


}
