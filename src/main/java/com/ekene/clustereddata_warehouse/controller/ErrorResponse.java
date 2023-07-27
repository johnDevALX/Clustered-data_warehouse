package com.ekene.clustereddata_warehouse.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private HttpStatus responseCode;

}
