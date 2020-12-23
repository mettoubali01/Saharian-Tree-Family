package com.example.dto;

import com.sun.istack.NotNull;

public class ParentDto {

    @NotNull
    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
