package com.example.callapi.repository.custom.impl;

import com.example.callapi.model.dto.RequestDto;
import com.example.callapi.repository.custom.GameRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
@Repository
public class GameRepositoryCustomImpl implements GameRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Document> groupBy(RequestDto dto) {
        LimitOperation limit = Aggregation.limit(dto.getLimit());

        GroupOperation group = Aggregation.group(dto.getDimensions().toArray(new String[0]));
        for (String metric : dto.getMetrics()) {
            group = group.sum(metric).as(metric);
            group = group.count().as("count");
        }

        MatchOperation match = Aggregation.match(Criteria.where("date").gte(dto.getStart())
                .andOperator(Criteria.where("date").lte(dto.getEnd())));

        List<String> p = new ArrayList<>();
        p.addAll(dto.getDimensions());
        p.addAll(dto.getMetrics());
        p.remove("date");

        ProjectionOperation projectionOperation = project()
                .andInclude(p.toArray(new String[0]))
                .and(ArithmeticOperators.Round.roundValueOf("eCPM").place(1)).as("eCPM")
                .andInclude("count");

        if (dto.getDimensions().size() > 1) {
            projectionOperation = projectionOperation.andExclude("_id");
            projectionOperation = projectionOperation.andExpression("date").dateAsFormattedString("%d-%m-%Y").as("date");
        }

        Aggregation aggregation = newAggregation(
                match,
                group,
                projectionOperation,
                limit
        );
        List<Document> result = mongoTemplate.aggregate(aggregation, "mo_data1", Document.class).getMappedResults();
        return result;
    }
}
