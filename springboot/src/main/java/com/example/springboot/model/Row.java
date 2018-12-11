package com.example.springboot.model;

public class Row {
    private Integer id;
    private String tabId;
    private String rowId;
    private String rowVal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRowVal() {
        return rowVal;
    }

    public void setRowVal(String rowVal) {
        this.rowVal = rowVal;
    }
}
