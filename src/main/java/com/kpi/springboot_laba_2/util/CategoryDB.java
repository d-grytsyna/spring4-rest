package com.kpi.springboot_laba_2.util;

import com.kpi.springboot_laba_2.entity.Category;

import java.util.HashMap;

public class CategoryDB {
    private int uniqueId;
    private HashMap<Integer, Category> categoryDB;

    public CategoryDB(){
        uniqueId = 1;
        categoryDB = new HashMap<>();
    }

    public int getUniqueId(){
        return uniqueId;
    }

    public int createUniqueId(){
        return uniqueId++;
    }

    public HashMap<Integer, Category> getCategoryDB() {
        return categoryDB;
    }

    public void setCategoryDB(HashMap<Integer, Category> categoryDB) {
        this.categoryDB = categoryDB;
    }
}
