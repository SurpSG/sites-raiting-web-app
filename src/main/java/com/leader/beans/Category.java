package com.leader.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruki on 09.07.2014.
 */
public class Category {
    public static final String CATEGORY_PARAM_NAME = "category";

    private int categoryID;
    private String categoryName;
    private int categoryParentID;
    private List<Category> children;

    public Category(int categoryID, String categoryName, int categoryParentID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryParentID = categoryParentID;
        children = new ArrayList<Category>();
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryParentID() {
        return categoryParentID;
    }

    public List<Category> getChildren() {
        return children;
    }


    public boolean addSubcategory(Category category){
        if(categoryID == category.getCategoryParentID()){
            children.add(category);
            return true;
        }

        boolean result = false;
        for (Category child:children){
            result |= child.addSubcategory(category);
            if(result){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryID=" + categoryID +
                ", categoryName='" + categoryName + '\'' +
                ", categoryParentID=" + categoryParentID +
                ", subcategories=" + children +
                '}';
    }
}
