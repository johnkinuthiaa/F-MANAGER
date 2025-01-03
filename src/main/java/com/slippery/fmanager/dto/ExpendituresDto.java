package com.slippery.fmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.fmanager.models.Expenditures;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpendituresDto {
    private String message;
    private String errorMessage;
    private int statusCode;
    private Expenditures expenditure;
    private List<Expenditures> allExpenditures;
}
