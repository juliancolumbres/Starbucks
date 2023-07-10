package com.example.starbuckscashierapp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Order {

    private String drink ;
    private String milk ;
    private String size ;
    private String total ;
    private String register ;
    private String status ;
    
    public static Order GetNewOrder() {
     	Order o = new Order() ;

    	o.drink = "Caffe Americano" ;
    	o.milk = "Soy Milk" ;
    	o.size = "Venti" ;
    	o.status = "Ready for Payment" ;
    	o.total = "$3.16" ;

    	return o ;
    }


}