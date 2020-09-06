package com.magicguang.nettyhttpserver;

import lombok.Data;

import java.util.Date;

@Data
public class SimpleData {
    private String username;
    private Integer id;
    private Date visitDate;
    private String method;
}
