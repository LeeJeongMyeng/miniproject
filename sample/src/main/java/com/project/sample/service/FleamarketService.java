package com.project.sample.service;

import com.project.sample.dto.FleamarketDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FleamarketService {

    public int reg_FleaMarket(FleamarketDto fleamarketDto);
    public int reg_FleaMarket_files(List<MultipartFile> files);
}
