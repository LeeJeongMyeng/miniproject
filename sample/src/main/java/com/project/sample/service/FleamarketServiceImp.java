package com.project.sample.service;

import com.project.sample.dao.FleamarketDao;
import com.project.sample.dto.FleaMarketDto2;
import com.project.sample.dto.FleamarketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FleamarketServiceImp implements FleamarketService {

    private final FleamarketDao dao;
    private FileService fileservice;
    @Autowired
    public FleamarketServiceImp(FleamarketDao dao,FileService fileservice) {
        this.dao = dao;
        this.fileservice=fileservice;
    }

    @Override
    public int reg_FleaMarket(FleamarketDto fleamarketDto) {
        System.out.println("reg_FleaMarket_Service");

        return dao.reg_FleaMarket(fleamarketDto);
    }

    @Override
    public int reg_FleaMarket_files(List<MultipartFile> files) {
        //파일 업로드 처리
        int fno = dao.FleaMarket_get_fno_max();
        System.out.println(fno);
        FleamarketDto fleamarketDto = new FleamarketDto();

        fleamarketDto.setFno(fno);


        for(MultipartFile file:files){
            String origin_file_name = file.getOriginalFilename().replace(" ","");
            fleamarketDto.setOrigin_file_name(origin_file_name);
            //여기서 파일 업로드 되면서 UUID파일이름뽑아옴
            fleamarketDto.setUuid_file_name(fileservice.Insprofileimg(file));
            dao.reg_FleaMarket_file(fleamarketDto);
        }
        return 0;
    }

    @Override
    public FleaMarketDto2 get_FleaMarket_List(FleamarketDto fleamarketDto) {

        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();
        //필요한 페이지나 글 갯수 등 정의
        int currentPage = 1; //현재 페이지

        int totCnt = dao.get_FleaMarket_totCnt(); //게시글 전체 갯수

        int onePageCnt = 6; //한 페이지 보여질 갯수

        int totPage = (int)Math.ceil((double)totCnt/onePageCnt); //총 페이지 갯수
        System.out.println(totCnt+":"+onePageCnt+"="+totPage);
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum

        int en_rownum = currentPage * onePageCnt; //끝 rownum

        //검색 내용 및 페이지에 맞는 게시글 들고와서 담기
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList(dao.get_FleaMarket_List(fleamarketDto));



        fleaMarketDto2.setCurrentPage(currentPage);
        //fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        //게시글 리스트 담기



        return fleaMarketDto2;
    }

//    @Override
//    public FleaMarketDto2 get_FleaMarket_List() {
//
//        int totCnt = dao.get_FleaMarket_totCnt(); //현재 전체 갯수
//        int onePageCnt = 6; //한 페이지 보여질 갯수
//        int totPage = (int) Math.ceil(totCnt/onePageCnt); //총 페이지 갯수
//        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();
//
//
//
//        //게시글 리스트 담기
//        fleaMarketDto2.setFleamarketDtoList(dao.get_FleaMarket_List());
//
//
//        return ;
//    }


}
