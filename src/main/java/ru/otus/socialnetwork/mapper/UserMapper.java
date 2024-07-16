package ru.mmtr.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mmtr.common.dto.object.invoice.InvoiceDto;
import ru.mmtr.common.dto.object.invoice.InvoiceShortDto;


@Mapper(componentModel = "spring")
public abstract class InvoiceMapper {

    @Mapping(target = ".", source = ".")
    public abstract InvoiceShortDto mapToInvoiceShortDto(InvoiceDto invoice);
}