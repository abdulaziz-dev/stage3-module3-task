package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.dto.TagResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagController implements BaseController<TagRequestDTO, TagResponseDTO, Long> {

    private final BaseService<TagRequestDTO, TagResponseDTO, Long> service;

    @Autowired
    public TagController(BaseService<TagRequestDTO, TagResponseDTO, Long> service) {
        this.service = service;
    }

    @Override
    public List<TagResponseDTO> readAll() {
        return service.readAll();
    }

    @Override
    public TagResponseDTO readById(Long id) {
        return service.readById(id);
    }

    @Override
    public TagResponseDTO create(TagRequestDTO createRequest) {
        return service.create(createRequest);
    }

    @Override
    public TagResponseDTO update(TagRequestDTO updateRequest) {
        return service.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }
}
