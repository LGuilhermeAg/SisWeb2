package br.edu.ufop.web.sales.controller.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventDTO {

    private String description;

    private Integer type;

    private LocalDateTime date;

    private LocalDateTime startSales;
    private LocalDateTime endSales;

    private Float price;

}
