package com.socialmediaclone.socialmediaclone.utilities;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;

@PropertySource("classpath:messagepage.properties")
@Configuration // TODO is configuration correct?
public class MessagePage {

    @Value("${maxPageSize}")
    private int maxPageSize;
    @Value("${pageNumber}")
    private int pageNumber;
    @Value("${pageSize}")
    private int pageSize;
    @Value("${sortDirection}")
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    @Value("${sortBy}")
    private String sortBy = "dateTime";
    @Value("${pageNumber}")
    private int pageNumberDef;
    @Value("${pageSize}")
    private int pageSizeDef;


    public MessagePage() {}


    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
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

    public void setBeanDefault(){
        setPageNumber(pageNumberDef);
        setPageSize(pageSizeDef);
    }
}


