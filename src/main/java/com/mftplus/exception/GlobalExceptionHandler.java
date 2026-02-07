package com.mftplus.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // مدیریت خطای پیدا نشدن رکورد
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/persons";
    }

    // مدیریت خطاهای عمومی سیستم (NullPointer, Database, etc)
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        // در سیستم‌های تجاری واقعی، اینجا باید لاگ بزنید (Log.error)
        ex.printStackTrace(); 
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return "error"; // اشاره به فایل templates/error.html (باید بسازید)
    }
}