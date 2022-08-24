package com.socialmediaclone.socialmediaclone.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;

@Configuration
@PropertySource("classpath:messagepage.properties")
public class PostPage {

    @Value("${maxPageSize}")
    private int maxPageSize;
    @Value("${pageNumber}")
    private int pageNumber;
    @Value("${pageSize}")
    private int pageSize;
    @Value("${sortDirection}")
    private Sort.Direction sortDirection;
    @Value("${sortBy}")
    private String sortBy;

    @Value("${pageNumber}")
    private int pageNumberDef;
    @Value("${pageSize}")
    private int pageSizeDef;

    public PostPage() {
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public void setBeanDefault(){
        setPageNumber(pageNumberDef);
        setPageSize(pageSizeDef);
    }
}
