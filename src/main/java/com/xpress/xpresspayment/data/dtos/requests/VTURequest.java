package com.xpress.xpresspayment.data.dtos.requests;

import com.xpress.xpresspayment.models.Details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class VTURequest {

    private  long requestId;
    @UuidGenerator
    private String uniqueCode = UUID.randomUUID().toString();
    private List<Details> details;
}
