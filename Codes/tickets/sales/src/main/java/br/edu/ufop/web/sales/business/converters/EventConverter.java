package br.edu.ufop.web.sales.business.converters;

import br.edu.ufop.web.sales.controller.dtos.events.CreateEventDTO;
import br.edu.ufop.web.sales.controller.dtos.events.EventDTO;
import br.edu.ufop.web.sales.controller.dtos.events.UpdateEventDTO;
import br.edu.ufop.web.sales.enums.EnumEventType;
import br.edu.ufop.web.sales.infrastructure.entities.EventEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventConverter {

    public static EventDTO toDTO(EventEntity eventEntity) {

        return EventDTO.builder()
                .id(eventEntity.getId())
                .description(eventEntity.getDescription())
                .type(eventEntity.getType())
                .date(eventEntity.getDate())
                .startSales(eventEntity.getStartSales())
                .endSales(eventEntity.getEndSales())
                .price(eventEntity.getPrice())
                .createdAt(eventEntity.getCreatedAt())
                .updatedAt(eventEntity.getUpdatedAt())
                .build();

    }

    public static EventEntity toEntity(CreateEventDTO createEventDTO) {
        return EventEntity.builder()
                .description(createEventDTO.getDescription())
                .type(EnumEventType.getById(createEventDTO.getType()))
                .date(createEventDTO.getDate())
                .startSales(createEventDTO.getStartSales())
                .endSales(createEventDTO.getEndSales())
                .price(createEventDTO.getPrice())
                .build();
    }

    public static void applyUpdate(EventEntity entity, UpdateEventDTO dto) {
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getType() != null) {
            entity.setType(EnumEventType.getById(dto.getType()));
        }
        if (dto.getDate() != null) {
            entity.setDate(dto.getDate());
        }
        if (dto.getStartSales() != null) {
            entity.setStartSales(dto.getStartSales());
        }
        if (dto.getEndSales() != null) {
            entity.setEndSales(dto.getEndSales());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
    }

}