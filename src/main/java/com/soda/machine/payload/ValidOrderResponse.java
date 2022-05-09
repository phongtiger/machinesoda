package com.soda.machine.payload;

import lombok.Data;
import org.springframework.web.servlet.ModelAndView;
@Data
public class ValidOrderResponse {
    private ModelAndView modelAndView;
    private boolean isValid;
}
