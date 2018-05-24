package com.inga.model;

import java.io.Serializable;

/**
 *
 * Date  2018/1/15
 * Time  上午10:17
 * Author bingbing.wang@corp.elong.com
 */
public class User implements Serializable {

    private String id;

    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
