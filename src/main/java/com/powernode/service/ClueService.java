package com.powernode.service;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TClue;
import com.powernode.query.ClueQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClueService {

    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(MultipartFile file, String token) throws IOException;

    Boolean checkPhone(String phone);

    int saveClue(ClueQuery clueQuery);

    TClue getClueById(Integer id);

    int editClue(ClueQuery clueQuery);

    int delClueById(Integer id);
}
