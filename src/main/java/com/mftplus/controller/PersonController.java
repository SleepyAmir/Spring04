package com.mftplus.controller;

import com.mftplus.dto.PersonDto; // ایمپورت DTO
import com.mftplus.exception.ResourceNotFoundException;
import com.mftplus.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    // --- Helper Method to Load Data (DRY) ---
    // تغییر: نوع خروجی سرویس حالا Page<PersonDto> است
    private void prepareListModel(Model model, int page, int size, String family, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PersonDto> personPage; // تغییر به DTO

        if (family != null && name != null && !family.isBlank()) {
            personPage = personService.findByFamilyAndName(family, name, pageable);
        } else {
            personPage = personService.findAll(pageable);
        }
        model.addAttribute("persons", personPage);
    }

    // --- GET LIST ---
    @GetMapping
    public String getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String family,
            @RequestParam(required = false) String name,
            Model model) {

        prepareListModel(model, page, size, family, name);

        // تغییر: ساخت یک DTO خالی برای فرم جدید بجای Entity
        if (!model.containsAttribute("person")) {
            model.addAttribute("person", new PersonDto());
        }

        return "person/list";
    }

    @GetMapping("/trash")
    public String getTrash(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<PersonDto> deletedPage = personService.findAllDeleted(pageable); // دریافت DTO

        model.addAttribute("persons", deletedPage);
        model.addAttribute("isTrash", true); // فلگ برای تغییر دکمه‌ها در UI
        return "person/list";
    }

    // --- CREATE (POST) ---
    @PostMapping
    public String createPerson(
            // تغییر: دریافت PersonDto از فرم
            @Valid @ModelAttribute("person") PersonDto personDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (result.hasErrors()) {
            prepareListModel(model, page, size, null, null);
            model.addAttribute("showModal", true);
            model.addAttribute("formAction", "/persons");
            return "person/list";
        }

        personService.save(personDto); // ارسال DTO به سرویس
        redirectAttributes.addFlashAttribute("successMessage", "Person created successfully!");
        return "redirect:/persons";
    }

    // --- UPDATE (PUT) ---
    @PutMapping("/{id}")
    public String updatePerson(
            @PathVariable Long id,
            // تغییر: دریافت PersonDto از فرم
            @Valid @ModelAttribute("person") PersonDto personDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page) {

        if (result.hasErrors()) {
            prepareListModel(model, page, 10, null, null);
            model.addAttribute("showModal", true);
            model.addAttribute("formAction", "/persons/" + id);
            personDto.setId(id); // ست کردن ID روی DTO
            return "person/list";
        }

        // بررسی وجود رکورد (سرویس DTO برمی‌گرداند)
        if (personService.findById(id) == null) {
            throw new ResourceNotFoundException("Person not found with id: " + id);
        }

        personDto.setId(id);
        personService.update(personDto); // ارسال DTO برای آپدیت
        redirectAttributes.addFlashAttribute("successMessage", "Person updated successfully!");
        return "redirect:/persons";
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (personService.findById(id) == null) {
            throw new ResourceNotFoundException("Cannot delete. Person not found id: " + id);
        }
        personService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Person deleted (moved to trash).");
        return "redirect:/persons";
    }

    // --- RESTORE ---
    @PostMapping("/restore/{id}")
    public String restorePerson(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        personService.restoreById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Person restored successfully!");
        return "redirect:/persons/trash";
    }
}