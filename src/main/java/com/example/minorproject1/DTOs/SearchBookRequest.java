package com.example.minorproject1.DTOs;

import javax.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchBookRequest {
    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;
    @NotBlank
    private String operator;

    private boolean available;

    private static Set<String> allowedKeys = new HashSet<>();
    private static Map<String, List<String>> allowedOperatorMap = new HashMap<>();

    SearchBookRequest(){

        allowedKeys.addAll(Arrays.asList("name","author_name","genre","pages","id"));

        allowedOperatorMap.put("name",Arrays.asList("=","like"));
        allowedOperatorMap.put("author_name",Arrays.asList("="));
        allowedOperatorMap.put("genre",Arrays.asList("="));
        allowedOperatorMap.put("pages",Arrays.asList("=",">","<",">=","<="));
        allowedOperatorMap.put("id",Arrays.asList("="));
    }


    public boolean validate(){

        if(!allowedKeys.contains(this.searchKey)){
            return false;
        }

        List<String> validOperators = allowedOperatorMap.get(this.searchKey);
        if (!validOperators.contains(this.operator)){
            return false;
        }

        return true;
    }
}
