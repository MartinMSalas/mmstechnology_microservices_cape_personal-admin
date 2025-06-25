package com.mmstechnology.personal.service;

import com.mmstechnology.personal.dto.PersonalDto;
import com.mmstechnology.personal.mapper.PersonalMapper;
import com.mmstechnology.personal.model.Personal;
import com.mmstechnology.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalService {

    private final PersonalRepository repository;
    private final PersonalMapper mapper;

    public List<PersonalDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public PersonalDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new RuntimeException("Personal no encontrado"));
    }

    public PersonalDto create(PersonalDto dto) {
        Personal saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public PersonalDto update(Long id, PersonalDto dto) {
        if (!repository.existsById(id)) throw new RuntimeException("Personal no encontrado");
        Personal updated = mapper.toEntity(dto);
        updated.setId(id);
        return mapper.toDto(repository.save(updated));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
