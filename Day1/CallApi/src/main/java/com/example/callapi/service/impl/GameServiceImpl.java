package com.example.callapi.service.impl;

import com.example.callapi.model.dto.RequestDto;
import com.example.callapi.model.entity.ResponseData;
import com.example.callapi.model.response.Response;
import com.example.callapi.model.response.Status;
import com.example.callapi.repository.custom.GameRepositoryCustom;
import com.example.callapi.service.GameService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepositoryCustom repositoryCustom;

    @Override
    public Response getAll(RequestDto requestDto) {
        long betweenDate = requestDto.getEnd().getTime() - requestDto.getStart().getTime();
        if (betweenDate < 0) {
            return Response.builder()
                    .status(Status.builder().code(-2).msg("Start before end").build())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        if (betweenDate > 7_889_400_000L) {
            return Response.builder()
                    .status(Status.builder().code(-2).msg("between start end less than 3 month").build())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        List<Document> list = repositoryCustom.groupBy(requestDto);
        int size = list.size();

        return Response.builder()
                .status(Status.builder().code(0).msg("success").build())
                .timestamp(LocalDateTime.now())
                .data(ResponseData.builder().total(size > 0 ? size : 0).data(list).build())
                .build();
    }
}
