package com.project.sample.service;

import com.project.sample.dto.Notic;
import com.project.sample.dto.Notic2;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


public interface NoticService {

    public Notic2 get_Notic_List(Notic notic);
    public Map<String,Object> get_Notic(int notice_id);
}
