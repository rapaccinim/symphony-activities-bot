package com.rapaccinim.activities;

import com.symphony.bdk.core.activity.ActivityMatcher;
import com.symphony.bdk.core.activity.form.FormReplyActivity;
import com.symphony.bdk.core.activity.form.FormReplyContext;
import com.symphony.bdk.core.activity.model.ActivityInfo;
import com.symphony.bdk.core.activity.model.ActivityType;
import com.symphony.bdk.core.service.datafeed.EventException;
import com.symphony.bdk.core.service.message.MessageService;

public class PriceFormReplyActivity extends FormReplyActivity<FormReplyContext> {

    // we need to inject what we need from the BDK
    private MessageService messages;

    // and we need a constructor to initialise the MessageService
    public PriceFormReplyActivity(MessageService messages){
        this.messages = messages;
    }

    // define which form submission should respond to
    @Override
    protected ActivityMatcher<FormReplyContext> matcher() throws EventException {
        return formReplyContext -> formReplyContext.getFormId().equals("price");
    }

    // extract some specific text and
    @Override
    protected void onActivity(FormReplyContext formReplyContext) throws EventException {
        System.out.println("I am here");
        // get the ticker info from the form
        String ticker = formReplyContext.getFormValue("ticker");
        int price = (int) (Math.random() * 777);
        String response = "The price of " + ticker + " is " + price;
        // and send back a message
        messages.send(formReplyContext.getSourceEvent().getStream(), response);
    }

    // add some metadata info
    @Override
    protected ActivityInfo info() {
        return new ActivityInfo().type(ActivityType.FORM)
                .name("Get price")
                .description("Form handler for price form");
    }
}
