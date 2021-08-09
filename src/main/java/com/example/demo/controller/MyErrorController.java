package com.example.demo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class MyErrorController implements ErrorController  {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyErrorController.class);

    @RequestMapping("/error")
    public ModelAndView renderErrorPage(HttpServletRequest request) {
        Throwable exception =(Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        log.error("An error happened", exception);
        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = " Please, check your request and try again later";
        int httpErrorCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

        switch (httpErrorCode) {
            case 401:
            case 403:{
                errorMsg = "\nYou are not athorized to seeing this page." +
                        "\nPlease use another credentials or choose other page";
                break;
            }
            case 400:
            case 404: {
                errorMsg = "\nThere is no such page.\n" + errorMsg;
                break;
            }
            case 500: {
                errorMsg = "\nInternal Server Error.\n" + errorMsg;
                break;
            }
            default:{
                errorMsg = exception.getMessage() + errorMsg;
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

}
