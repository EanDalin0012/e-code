package com.ecode.core.template;

import com.ecode.core.dto.ErrorMessage;

public class ResponseData<B> {

    private B body;
    private ErrorMessage error;

    public ResponseData() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResponseData(B body, ErrorMessage error) {
        super();
        this.body = body;
        this.error = error;
    }

    public B getBody() {
        return body;
    }

    public void setBody(B body) {
        this.body = body;
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }


}
