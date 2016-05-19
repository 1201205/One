package com.hyc.zhihu.beans;

public class SerialListItem implements java.io.Serializable {
    private static final long serialVersionUID = 7593393371091515857L;
    private String number;
    private String serial_id;
    private String id;

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSerial_id() {
        return this.serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
