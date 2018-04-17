package ru.bellintegrator.wb.exception;

import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class SomeHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        System.out.println("Error in listener" + t);
    }
}
