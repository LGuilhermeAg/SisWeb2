package br.edu.ufop.web.sales.controller;

import br.edu.ufop.web.sales.business.services.EventService;
import br.edu.ufop.web.sales.controller.dtos.events.CreateEventDTO;
import br.edu.ufop.web.sales.controller.dtos.events.EventDTO;
import br.edu.ufop.web.sales.controller.dtos.events.UpdateEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody CreateEventDTO createEventDTO) {
        return ResponseEntity.ok(eventService.create(createEventDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getDtoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable UUID id, @RequestBody UpdateEventDTO updateEventDTO) {
        return ResponseEntity.ok(eventService.update(id, updateEventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
