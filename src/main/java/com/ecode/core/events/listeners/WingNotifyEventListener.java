package com.ecode.core.events.listeners;

import com.ecode.core.events.WingNotifyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WingNotifyEventListener implements ApplicationListener<WingNotifyEvent> {

    @Override
    public void onApplicationEvent(WingNotifyEvent event) {
        try {
            Thread.sleep(10000);
            System.out.println("WingNotifyEventListener Work");
//            System.out.println(wingBotEvent.getSource());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
