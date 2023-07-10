package com.example.starbuckscashierapp;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@Controller
@RequestMapping("/cashier")
public class CashierController {

    @Value("${api.url}")
    private String API_URL;

    @Value("${api.key}")
    private String API_KEY;

    @GetMapping
    public String getAction( @ModelAttribute("command") Command command, 
                            Model model, HttpSession session) {

        String message = "" ;

        command.setRegister( "5012349" ) ;
        message = "Starbucks Reserved Order" + "\n\n" +
            "Register: " + command.getRegister() + "\n" +
            "Status:   " + "Ready for New Order"+ "\n" ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "starbucks" ;

    }


    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "" ;
        String message = "";

        log.info( "Action: " + action ) ;
        command.setRegister( command.getStores() ) ;
        log.info( "Command: " + command ) ;

        /* Process Post Action */
        if ( action.equals("Place Order") ) {

            resourceUrl = API_URL + "/order/register/" + command.getRegister() + "?apikey=" + API_KEY;

            Order orderRequest = new Order() ;
            orderRequest.setDrink(command.getDrink()) ;
            orderRequest.setMilk(command.getMilk()) ;
            orderRequest.setSize(command.getSize());
            HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderRequest) ;
            
            try {
                ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class);
                Order newOrder = newOrderResponse.getBody();
                message = "Starbucks Reserved Order" + "\n\n" +
                    "Drink: " + newOrder.getDrink() + "\n" +
                    "Milk:  " + newOrder.getMilk() + "\n" +
                    "Size:  " + newOrder.getSize() + "\n" +
                    "Total: " + newOrder.getTotal() + "\n" +
                    "\n" +
                    "Register: " + newOrder.getRegister() + "\n" +
                    "Status:   " + newOrder.getStatus() + "\n" ;
            } catch (HttpStatusCodeException e) {
                log.info("Unable to place order, error code: " + e.getStatusCode());
                message = "Starbucks Reserved Order" + "\n\n" +
                    "Register: " + command.getRegister() + "\n" +
                    "Status:   " + "Active order currently exists."+ "\n" ;
            }        


        }
        else if ( action.equals("Get Order") ) {
      
            resourceUrl = API_URL + "/order/register/" + command.getRegister() + "?apikey=" + API_KEY;
    
            try {
                ResponseEntity<Order> activeOrderResponse = restTemplate.getForEntity(resourceUrl, Order.class);
                Order activeOrder = activeOrderResponse.getBody();
                log.info( activeOrder.toString() );
                message = "Starbucks Reserved Order" + "\n\n" +
                    "Drink: " + activeOrder.getDrink() + "\n" +
                    "Milk:  " + activeOrder.getMilk() + "\n" +
                    "Size:  " + activeOrder.getSize() + "\n" +
                    "Total: " + activeOrder.getTotal() + "\n" +
                    "\n" +
                    "Register: " + activeOrder.getRegister() + "\n" +
                    "Status:   " + activeOrder.getStatus() + "\n" ;
            } catch (HttpStatusCodeException e) {
                log.info("Unable to get order, error code: " + e.getStatusCode());
                message = "Starbucks Reserved Order" + "\n\n" +
                    "Register: " + command.getRegister() + "\n" +
                    "Status:   " + "No active order. Ready for New Order"+ "\n" ;
            }        
        
        } 
        else if ( action.equals("Clear Order") ) {

            resourceUrl = API_URL + "/order/register/" + command.getRegister() + "?apikey=" + API_KEY;

            try {
                restTemplate.delete(resourceUrl);
                message = "Starbucks Reserved Order" + "\n\n" +
                    "Register: " + command.getRegister() + "\n" +
                    "Status:   " + "Order cleared. Ready for New Order"+ "\n" ;            
            } catch (HttpStatusCodeException e) {
                message = "Starbucks Reserved Order" + "\n\n" +
                    "Register: " + command.getRegister() + "\n" +
                    "Status:   " + "No active order. Ready for New Order"+ "\n" ;            
            }        
    
        }         
        command.setMessage( message ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "starbucks" ;

    }
    

}
