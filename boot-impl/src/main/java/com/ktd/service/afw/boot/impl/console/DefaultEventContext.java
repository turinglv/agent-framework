package com.ktd.service.afw.boot.impl.console;

import com.ktd.service.afw.boot.api.command.EventContext;
import com.ktd.service.afw.boot.api.command.EventListener;
import com.ktd.service.afw.boot.api.command.EventPublisher;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.command.Event;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultEventContext implements EventContext, EventPublisher {

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    private final List<EventListener> listeners = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void addListener(EventListener eventListener) {
        if (eventListener == null) {
            return;
        }
        lock.lock();
        try {
            listeners.add(eventListener);
        } catch (Exception e) {
            LOG.error("注册CommandHandler发生异常：{}", eventListener, e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int removeListener(EventListener eventListener) {
        int removeCount = 0;
        if (eventListener == null) {
            return removeCount;
        }
        lock.lock();
        try {
            Iterator<EventListener> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                EventListener listener = iterator.next();
                if (listener == eventListener) {
                    iterator.remove();
                    removeCount++;
                }
            }
        } catch (Exception e) {
            LOG.error("移除EventListener发生异常:{}", eventListener, e);
        } finally {
            lock.unlock();
        }
        return removeCount;
    }

    @Override
    public int removeListener(String idr) {
        int removeCount = 0;
        if (idr == null || idr.isEmpty()) {
            return removeCount;
        }
        lock.lock();
        try {
            Iterator<EventListener> iterator = listeners.iterator();
            while (iterator.hasNext()) {
                EventListener listener = iterator.next();
                if (idr.equals(listener.getIdr())) {
                    iterator.remove();
                    removeCount++;
                }
            }
        } catch (Exception e) {
            LOG.error("移除EventListener发生异常:{}", idr, e);
        } finally {
            lock.unlock();
        }
        return removeCount;
    }


    @Override
    public void publishEvent(Event event) {
        for (EventListener listener : listeners) {
            if (listener.supportsEvent(event)) {
                LOG.info("[Event] 开始消费 {}", listener.getIdr(), event);
                boolean success = listener.handle(event);
                LOG.info("[Event] 结束消费 {}", success, listener.getIdr(), event);
            } else {
                LOG.debug("[Event] 不接收 {}", listener.getIdr(), event);
            }
        }
    }

}
