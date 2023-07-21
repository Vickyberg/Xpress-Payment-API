package com.xpress.xpresspayment.data.dtos.requests;

import com.xpress.xpresspayment.models.Details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class PaymentRequest {

    private  long requestId;
    private String uniqueCode;
    private List<Details> details;
}
