package com.leader.servlets;

/**
 * Created by ruki on 11.06.2014.
 */
public class Page {
    public static final int LIST_PAGE = 1;
    public int listPage = LIST_PAGE;

    public static final int SITE_INFO_PAGE = 2;
    public int siteInfoPage = SITE_INFO_PAGE;

    public static Page page = new Page();

    private Page() {
    }

    public int getListPage() {
        return listPage;
    }

    public int getSiteInfoPage() {
        return siteInfoPage;
    }

}
