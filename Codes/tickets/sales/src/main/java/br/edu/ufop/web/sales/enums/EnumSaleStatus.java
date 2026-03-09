package br.edu.ufop.web.sales.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumSaleStatus {

    EM_ABERTO(1, "Em aberto"),
    PAGO(2, "Pago"),
    CANCELADO(3, "Cancelado"),
    ESTORNADO(4, "Estornado");

    private Integer id;
    private String description;

    public static EnumSaleStatus getById(Integer id) {
        for (EnumSaleStatus status : EnumSaleStatus.values()) {
            if (status.getId().equals(id)) {
                return status;
            }
        }
        return null;
    }

}
