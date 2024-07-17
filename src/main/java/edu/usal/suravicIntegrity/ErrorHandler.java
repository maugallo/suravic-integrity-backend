package edu.usal.suravicIntegrity;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorHandler {

    public static List<String> loadErrorMessages(BindingResult bindingResult){
        List<String> errorList = new ArrayList<String>();

        bindingResult.getFieldErrors().forEach((error) -> {
            errorList.add(error.getDefaultMessage());
        });

        return errorList;
    }

    public static void printErrorMessages(List<String> errorList) {
        errorList.forEach((error) -> {
            System.out.println(error);
        });
    }

    public static String getErrorMessages(List<String> errorList) {
        return String.join("\n", errorList);
    }

}
