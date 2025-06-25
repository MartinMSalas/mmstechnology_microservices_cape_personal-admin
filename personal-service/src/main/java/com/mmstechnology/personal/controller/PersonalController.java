package com.mmstechnology.personal.controller;

import com.mmstechnology.personal.dto.PersonalDto;
import com.mmstechnology.personal.service.PersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService service;

    @GetMapping
    public List<PersonalDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PersonalDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public PersonalDto create(@RequestBody @Valid PersonalDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PersonalDto update(@PathVariable Long id, @RequestBody @Valid PersonalDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
