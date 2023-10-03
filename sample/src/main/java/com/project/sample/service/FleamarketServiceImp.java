package com.project.sample.service;

import com.project.sample.common.AESImp;
import com.project.sample.common.FileService;
import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FleamarketServiceImp implements FleamarketService {

    private final FleamarketDao fleamarketDao;
    private final MemberDao memberDao;
    private FileService fileservice;
    private final AESImp aes;

    @Autowired
    public FleamarketServiceImp(FleamarketDao fleamarketDao, FileService fileservice, MemberDao memberDao, AESImp aes) {
        this.fleamarketDao = fleamarketDao;
        this.fileservice=fileservice;
        this.memberDao=memberDao;
        this.aes = aes;
    }

    //게시글 등록 -> 해당 게시글 번호 들고 복귀
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int reg_FleaMarket(FleamarketDto fleamarketDto,List<MultipartFile> files,String method) {
        int post_id_max=0;

        /*
            insert라면 게시글 정보를 DB에 등록 후, 바로 게시글 번호를 뽑아와서
            매개변수로 들어온 fleamarketDto의 비어있는 post_id에 다가 할당해준다.
            해당 로직 마지막에 해당 post_id와 함께 들어온 파일의 정보를 DB에 심어주기 위함
         */

            if(method.equals("insert")){

                fleamarketDao.reg_FleaMarket(fleamarketDto);

                fleamarketDto.setPost_id( fleamarketDao.FleaMarket_get_fno_max());
                //method = update 일경우
            }else{
                //게시글 업데이트
                System.out.println("업데이트 실행");
                fleamarketDao.upt_FleaMarket(fleamarketDto);

                int post_id = fleamarketDto.getPost_id();
                if (files != null && !files.isEmpty()) {


                    //파일 리스트 부터 가져옴
                    List<Fleamarket_Files> fleamarket_files = fleamarketDao.get_FleaMarket_files(post_id);

                    System.out.println(post_id);

                    for(Fleamarket_Files file:fleamarket_files){
                        System.out.println(file.getUuid_file_name());
                    }

                    //DB정보삭제
                    fleamarketDao.del_FleaMarket_files(post_id);

                    //삭제처리
                    for(Fleamarket_Files file_Info:fleamarket_files){
                        System.out.println("파일삭제처리");
                        fileservice.DeleteFile(file_Info.getUuid_file_name());
                    }
                }
                post_id_max = fleamarketDto.getPost_id();
            }

            if (files != null && !files.isEmpty()) {
                for(MultipartFile file:files){
                    String origin_file_name = file.getOriginalFilename().replace(" ","");
                    fleamarketDto.setOrigin_file_name(origin_file_name);

                    //파일이름 중복 예방으로 uuid생성
                    String uuid = UUID.randomUUID().toString();
                    String uuid_file_name = uuid+"_"+file.getOriginalFilename().replace(" ","");

                    //파일이름 세팅 후, DB저장
                    fleamarketDto.setUuid_file_name(uuid_file_name);
                    fleamarketDao.reg_FleaMarket_file(fleamarketDto);

                    //파일 업로드
                    fileservice.Insprofileimg(file,uuid_file_name);
                }
            }
        return post_id_max;
    }

    //메인페이지에 보여질 플리마켓 게시글 리스트+썸네일 사진 하나씩
    @Transactional(readOnly = true)
    @Override
    public FleaMarketDto2 get_FleaMarket_List(FleamarketDto fleamarketDto) {
        System.out.println("get_FleaMarket_List 시작");

        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();

        //페이징용 글 갯수 등 정의
        int onePageCnt = 6; //한 페이지 보여질 갯수
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        int totCnt = fleamarketDao.get_FleaMarket_totCnt(fleamarketDto); //게시글 전체 갯수
        int totPage = (int)Math.ceil((double)totCnt/onePageCnt); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        //검색 내용 및 ROWNUM에 맞는 게시글 들고와서 담기
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList(fleamarketDao.get_FleaMarket_List(fleamarketDto));

        //Front에서 사용할 페이징정보
        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        System.out.println(totPage);

        //게시글 리스트 담기
        return fleaMarketDto2;
    }

    //게시글 상세조회
    @Transactional(readOnly = true)
    @Override
    public Map<String,Object> get_FleaMarket(int post_id) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("FleaMarket", fleamarketDao.get_FleaMarket( post_id ));
        map.put("FleaMarket_files", fleamarketDao.get_FleaMarket_files( post_id ));

        return map;
    }


    //게시글삭제
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int del_FleaMarket(int post_id) {
        // 신청글 삭제
        fleamarketDao.del_Application_FM(post_id);
        //게시글삭제
        fleamarketDao.del_FleaMarket(post_id);

        //파일 삭제를 위해서 게시글 파일 정보를 미리뽑아옴
        List<Fleamarket_Files> fleamarket_files = fleamarketDao.get_FleaMarket_files(post_id);
        //DB정보삭제
        fleamarketDao.del_FleaMarket_files(post_id);
        //파일삭제처리
        for(Fleamarket_Files file_Info:fleamarket_files){
            fileservice.DeleteFile(file_Info.getUuid_file_name());
        }
        return 0;
    }



    //신청하기
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int application_FM( int post_id, String user_id ) {

        int result = 0;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("post_id",post_id);
        map.put("user_id",user_id);
        //중복 신청 확인
        int Checknum = fleamarketDao.Check_Application( map );
        //신청
        if( Checknum == 0 ) {
            result = fleamarketDao.application_FM( map );
        }

        return result;
    }



    //특정 게시글에 대한 신청자 목록 확인
    @Transactional(readOnly = true)
    @Override
    public Map<String,Object> get_application_FM(Application_FM applicationFm) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String , Object> map = new HashMap<>();

        //각 상태에 대한 할당
        String[] states = {"대기","승인","거절"};
        String[] states_mapname = {"wait","approval","reject"};
        
        //데이터 가져오기
        for( int i = 0; i  <states.length; i++ ){

            // 대기/승인/거절 각 돌면서 List들고오기
            applicationFm.setState(states[i]);
            List<Application_FM> applicationFmList = fleamarketDao.get_application_FM(applicationFm);

            //복호화처리(유저정보List -> 복호화할 Field선정 -> 복호화 ->setter호출)
            for( Application_FM a : applicationFmList ){
               a= (Application_FM) aes.get_Origin_Info(a);
            }

            //개인정보 일부 가리기
            for( Application_FM a : applicationFmList ){
                //이름 별처리
                a.setName(aes.ProtectName(a.getName()));
               //이메일 별처리
                a.setEmail(aes.ProtectEmail(a.getEmail()));
               //폰번 별처리
                a.setPhone_number(aes.ProtectPhoneNumber(a.getPhone_number()));
               //주소 구까지 처리
                a.setAddress(aes.Protectaddress(a.getAddress()));
            }

            map.put( states_mapname[i] + "_appliication_FM" , applicationFmList );
        }

        return map;
    }

    //승인/대기/거부시 업데이트
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public int upt_application_FM( Application_FM applicationFm ) {
        int count = 0;
        //특정 유저에 대해 승인/거절/대기 상대 업테이드
        int result = fleamarketDao.upt_application_FM( applicationFm );

        if(result==1){

            //승인 갯수 세기
            count = fleamarketDao.get_Application_count( applicationFm.getPost_id() );

            //해당 게시글의 curCnt(현재승인갯수) 업데이트
            applicationFm.setCount(count);

            //해당 게시글의 승인 갯수 업데이트
            fleamarketDao.upt_apl_FM_Count(applicationFm);

        }

        return count;
    }


    //본인이 작성한 플리마켓 게시글
    @Transactional(readOnly = true)
    @Override
    public FleaMarketDto2 get_My_FleaMarket( FleamarketDto fleamarketDto ) {
        System.out.println("get_FleaMarket_List 실행");
        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();

        int onePageCnt = 10; //한 페이지 보여질 갯수
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        int totCnt = fleamarketDao.get_My_FleaMarket_Count( fleamarketDto ); //게시글 전체 갯수
        int totPage = (int)Math.ceil( (double)totCnt/onePageCnt ); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        //검색전 ROWNUM 세팅
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList( fleamarketDao.get_My_FleaMarket(fleamarketDto) );

        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        return fleaMarketDto2;
    }

    //본인이 신청한 플리마켓 글 목록 확인
    @Transactional(readOnly = true)
    @Override
    public FleaMarketDto2 get_My_Application(FleamarketDto fleamarketDto){
        System.out.println( "get_FleaMarket_List 시작" );

        FleaMarketDto2 fleaMarketDto2 = new FleaMarketDto2();

        //페이징 처리구간
        int onePageCnt = 10; //한 페이지 보여질 갯수
        int currentPage = fleamarketDto.getCurrentPage(); //현재 페이지
        int totCnt = fleamarketDao.get_My_Application_Count(fleamarketDto); //게시글 전체 갯수
        int totPage = (int)Math.ceil( (double)totCnt/onePageCnt ); //총 페이지 갯수
        int st_rownum = (currentPage-1) * onePageCnt +1; //시작 rounum
        int en_rownum = currentPage * onePageCnt; //끝 rownum

        //검색 내용 및 ROWNUM에 맞는 게시글 들고와서 담기
        fleamarketDto.setSt_rownum(st_rownum);
        fleamarketDto.setEn_rownum(en_rownum);
        fleaMarketDto2.setFleamarketDtoList(fleamarketDao.get_My_Application(fleamarketDto));


        //Front에서 사용할 페이징숫자들
        fleaMarketDto2.setCurrentPage(currentPage);
        fleaMarketDto2.setTotPage(totPage);
        fleaMarketDto2.setOnePageCnt(onePageCnt);

        return fleaMarketDto2;
    }

//=======================================================================================


}
