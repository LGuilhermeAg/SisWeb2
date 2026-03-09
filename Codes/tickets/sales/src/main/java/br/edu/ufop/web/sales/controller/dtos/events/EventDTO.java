package br.edu.ufop.web.sales.controller.dtos.events;

import br.edu.ufop.web.sales.enums.EnumEventType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

    private UUID id;

    private String description;

    private EnumEventType type;

    private LocalDateTime date;

    private LocalDateTime startSales;
    private LocalDateTime endSales;

    private Float price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
