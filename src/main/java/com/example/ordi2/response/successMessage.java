package com.example.ordi2.response;

public class successMessage
{
    private String successMessage ;

    public successMessage(){};

    public successMessage(String successMessage){
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
