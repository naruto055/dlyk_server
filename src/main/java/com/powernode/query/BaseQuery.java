package com.powernode.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseQuery {
    private String token;

    private String filterSQL;  // SQL过滤条件
}
