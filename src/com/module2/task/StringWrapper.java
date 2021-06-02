package com.module2.task;

public class StringWrapper {
    private String val;

    public StringWrapper(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
