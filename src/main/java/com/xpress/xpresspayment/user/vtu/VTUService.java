package com.xpress.xpresspayment.user.vtu;

import com.xpress.xpresspayment.data.dtos.requests.VTURequest;
import com.xpress.xpresspayment.data.dtos.responses.VTUResponse;

public interface VTUService {

VTUResponse purchaseAirtime(VTURequest vtuRequest);
}
