package br.edu.ufop.web.sales.business.services;

import br.edu.ufop.web.sales.business.converters.EventConverter;
import br.edu.ufop.web.sales.controller.dtos.events.CreateEventDTO;
import br.edu.ufop.web.sales.controller.dtos.events.EventDTO;
import br.edu.ufop.web.sales.controller.dtos.events.UpdateEventDTO;
import br.edu.ufop.web.sales.exception.NotFoundException;
import br.edu.ufop.web.sales.infrastructure.entities.EventEntity;
import br.edu.ufop.web.sales.infrastructure.repositories.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final IEventRepository eventRepository;

    public List<EventDTO> getAll() {

        List<EventEntity> eventEntityList = eventRepository.findAll();

        return eventEntityList.stream()
                .map(EventConverter::toDTO)
                .toList();

    }

    public EventDTO create(CreateEventDTO createEventDTO) {

        EventEntity eventEntity = EventConverter.toEntity(createEventDTO);
        eventEntity = eventRepository.save(eventEntity);
        return EventConverter.toDTO(eventEntity);

    }

    public Optional<EventEntity> getById(UUID id) {
        return eventRepository.findById(id);
    }

    public EventDTO getDtoById(UUID id) {
        return EventConverter.toDTO(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found.")));
    }

    public EventDTO update(UUID id, UpdateEventDTO updateEventDTO) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found."));

        EventConverter.applyUpdate(entity, updateEventDTO);
        entity = eventRepository.save(entity);

        return EventConverter.toDTO(entity);
    }

    public void delete(UUID id) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found."));
        eventRepository.delete(entity);
    }

}
