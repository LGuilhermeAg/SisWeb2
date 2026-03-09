package br.edu.ufop.web.sales.business.services;

import br.edu.ufop.web.sales.business.converters.SaleConverter;
import br.edu.ufop.web.sales.controller.dtos.sales.CreateSaleDTO;
import br.edu.ufop.web.sales.controller.dtos.sales.SaleDTO;
import br.edu.ufop.web.sales.controller.dtos.sales.UpdateSaleDTO;
import br.edu.ufop.web.sales.exception.NotFoundException;
import br.edu.ufop.web.sales.infrastructure.entities.EventEntity;
import br.edu.ufop.web.sales.infrastructure.entities.SaleEntity;
import br.edu.ufop.web.sales.infrastructure.repositories.ISaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final ISaleRepository saleRepository;
    private final EventService eventService;

    public List<SaleDTO> getAll() {

        List<SaleEntity> saleEntityList = saleRepository.findAll();

        return saleEntityList.stream()
                .map(SaleConverter::toDTO)
                .toList();

    }

    @Transactional
    public SaleDTO create(CreateSaleDTO createSaleDTO) {

        SaleEntity saleEntity = SaleConverter.toEntity(createSaleDTO);

        Optional<EventEntity> eventEntityOptional = eventService.getById(createSaleDTO.getEventId());

        if (eventEntityOptional.isEmpty()) {
            throw new NotFoundException("Event not found.");
        }

        saleEntity.setEvent(eventEntityOptional.get());

        saleRepository.save(saleEntity);
        return SaleConverter.toDTO(saleEntity);

    }

    public SaleDTO getById(UUID id) {
        SaleEntity entity = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found."));
        return SaleConverter.toDTO(entity);
    }

    @Transactional
    public SaleDTO update(UUID id, UpdateSaleDTO updateSaleDTO) {
        SaleEntity entity = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found."));

        SaleConverter.applyUpdate(entity, updateSaleDTO);

        if (entity.getEvent() == null || entity.getEvent().getId() == null) {
            throw new NotFoundException("Event not found.");
        }

        EventEntity eventEntity = eventService.getById(entity.getEvent().getId())
                .orElseThrow(() -> new NotFoundException("Event not found."));
        entity.setEvent(eventEntity);

        entity = saleRepository.save(entity);
        return SaleConverter.toDTO(entity);
    }

    public void delete(UUID id) {
        SaleEntity entity = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found."));
        saleRepository.delete(entity);
    }

}
