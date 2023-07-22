package com.xpress.xpresspayment.data.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class VTUResponse {

    private  long requestId;
    private  String referenceId;
    private  String responseCode;
    private  String responseMessage;
    private  int data;
}
