package com.project.sample.service;

import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FleamarketServiceImp implements FleamarketService {

    private final FleamarketDao fleamarketDao;
    private final MemberDao memberDao;
    private FileService fileservice;
    @Autowired
    public FleamarketServiceImp(FleamarketDao fleamarketDao, FileService fileservice, MemberDao memberDao) {
        this.fleamarketDao = fleamarketDao;
        this.fileservice=fileservice;
        this.memberDao=memberDao;
    }

    //게시글 등록 -> 해당 게시글 번호 들고 복귀
    @Override
    public int reg_FleaMarket(FleamarketDto fleamarketDto,String method) {

        if(method.equals("insert")){
            fleamarketDao.reg_FleaMarket(fleamarketDto);
            return fleamarketDao.FleaMarket_get_fno_max();
        //method = update 일경우
        }else{
            //게시글 업데이트
            System.out.println("수정ser");
            fleamarketDao.upt_FleaMarket(fleamarketDto);
            return fleamarketDto.getFno();
        }
    }
    // 이미지 등록+ 파일 업로드
    @Override
    public int reg_FleaMarket_files(List<MultipartFile> files,int fno,String method) {

        //업데이트 일경우 기존 파일 삭제 진행
        if(method.equals("update")){
            //파일 리스트 부터 뽑아옴
            del_Fleamarket_Files(fno);
            //DB파일 리스트 삭제
            fleamarketDao.del_FleaMarket_files(fno);
        }

        //파일 업로드 처리
        FleamarketDto fleamarketDto = new FleamarketDto();

        fleamarketDto.setFno(fno);

        for(MultipartFile file:files){
            String origin_file_name = file.getOriginalFilename().replace(" ","");
            fleamarketDto.setOrigin_file_name(origin_file_name);
            //여기서 파일 업로드 되면서 UUID파일이름뽑아옴
            fleamarketDto.setUuid_file_name(fileservice.Insprofileimg(file));
            fleamarketDao.reg_FleaMarket_file(fleamarketDto);
        }
        return 0;
    }

    //메인페이지에 보여질 게시글 리스트+썸네일 사진 하나씩
    @Override
    public FleaMarketDto2 get_FleaMarket_List(FleamarketDto fleamarketDto) {

        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();
        //필요한 페이지나 글 갯수 등 정의
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        System.out.println("현재페이지:"+currentPage);
        System.out.println("검색단어:"+fleamarketDto.getTitle());
        int totCnt = fleamarketDao.get_FleaMarket_totCnt(fleamarketDto); //게시글 전체 갯수
        int onePageCnt = 3; //한 페이지 보여질 갯수
        int totPage = (int)Math.ceil((double)totCnt/onePageCnt); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum
        System.out.println("get_FleaMarket_List");
        //검색 내용 및 페이지에 맞는 게시글 들고와서 담기
        fleamarketDto.setSt_rownum(st_rownum);
        System.out.println("현재페이지:"+st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        System.out.println("현재페이지:"+en_rownum);
        fleaMarketDto2.setFleamarketDtoList(fleamarketDao.get_FleaMarket_List(fleamarketDto));
        System.out.println("검색글 갯수:"+fleaMarketDto2.getFleamarketDtoList().size());

        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        //게시글 리스트 담기
        return fleaMarketDto2;
    }
    //상세조회
    @Override
    public FleamarketDto get_FleaMarket(int fno) {

        return fleamarketDao.get_FleaMarket(fno);
    }
    //상세조회의 이미지 파일들
    @Override
    public List<Fleamarket_Files> get_FleaMarket_files(int fno) {
        return fleamarketDao.get_FleaMarket_files(fno);
    }
    //게시글삭제
    @Override
    public int del_FleaMarket(int fno) {
        //아래 파일 삭제 메서드 진행
        del_Fleamarket_Files(fno);
        // 신청글 삭제
        fleamarketDao.del_Application_FM(fno);
        //게시글삭제
        return fleamarketDao.del_FleaMarket(fno);
    }

    //신청하기
    @Override
    public int application_FM(int fno, String userno) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fno",fno);
        map.put("userno",userno);

        return fleamarketDao.application_FM(map);
    }

    //중복 확인
    @Override
    public int Check_Application(int fno, String userno) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fno",fno);
        map.put("userno",userno);


        return fleamarketDao.Check_Application(map);
    }

    //파일 삭제처리 공통 메서드
    public void del_Fleamarket_Files(int fno){
        //파일 리스트 부터 뽑아옴
        List<Fleamarket_Files> fleamarket_files = fleamarketDao.get_FleaMarket_files(fno);
        //파일 삭제
        for(Fleamarket_Files file:fleamarket_files){
            fileservice.DeleteFile(file.getUuid_file_name());
        }

        fleamarketDao.del_FleaMarket_files(fno);
    }


}
