package com.example.starbuckscashierapp;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Command {

    private String action ;
    private String message ;
    private String stores ;
    private String register ;
    private String drink ;
    private String milk ;
    private String size ; 
    
}



