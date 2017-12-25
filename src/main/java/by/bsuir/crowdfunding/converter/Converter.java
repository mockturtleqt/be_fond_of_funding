package by.bsuir.crowdfunding.converter;

public interface Converter<MODEL, DTO> {

    DTO convertModelToDto(MODEL model);
}
