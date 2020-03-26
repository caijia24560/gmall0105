package com.atguigu.gmall.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @param
 * @return
 */
public class PmsBaseSaleAttr implements Serializable {

    @Id
    @Column
    String id ;

    @Column
    String name;

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
