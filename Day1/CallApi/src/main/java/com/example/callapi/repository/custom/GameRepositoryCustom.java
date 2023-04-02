package com.example.callapi.repository.custom;

import com.example.callapi.model.dto.RequestDto;
import org.bson.Document;

import java.util.List;

public interface GameRepositoryCustom {
    List<Document> groupBy(RequestDto dto);
}
