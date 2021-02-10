package com.ecode.core.events;

import org.json.simple.JSONObject;
import org.springframework.context.ApplicationEvent;

public class WingNotifyEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public WingNotifyEvent(Object source, JSONObject object) {
        super(source);
    }
}
