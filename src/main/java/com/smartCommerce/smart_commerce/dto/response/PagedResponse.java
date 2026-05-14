package com.smartCommerce.smart_commerce.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagedResponse<T> {

    private List<T> content;        
    private int pageNumber;         
    private int pageSize;           
    private long totalElements;     
    private int totalPages;         
    private boolean last;           
    private boolean first;          

    // Static factory method
    public static <T> PagedResponse<T> from(Page<T> page) {

        return PagedResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .first(page.isFirst())
                .build();
    }
}