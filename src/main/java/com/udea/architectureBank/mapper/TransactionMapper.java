package com.udea.architectureBank.mapper;

import com.udea.architectureBank.DTO.TransactionDTO;
import com.udea.architectureBank.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDTO toDTO(Transaction transaction);
}
