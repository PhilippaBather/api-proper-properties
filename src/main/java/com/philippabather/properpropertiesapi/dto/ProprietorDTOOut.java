package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * ProprietorDTOOut - El objeto de la transferencia de datos (DTO) de la clase Proprietor ('propietario') al cliente/consumo
 * de API; extiende UserDTOOut.
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProprietorDTOOut extends UserDTOOut {

    private long id;
    private int numProperties;
    private boolean isAgency;
    private List<RentalDTOOut> rentalPropertyList;
    private List<SaleDTOOut> salePropertyList;

}
