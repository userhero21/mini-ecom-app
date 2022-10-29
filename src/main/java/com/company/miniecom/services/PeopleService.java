package com.company.miniecom.services;

import com.company.miniecom.domains.People;
import com.company.miniecom.dto.PeopleCreateDTO;
import com.company.miniecom.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleService {

    private final PeopleRepository repository;


    public void add(PeopleCreateDTO dto) {
        People build = People.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

        repository.save(build);
    }


}
