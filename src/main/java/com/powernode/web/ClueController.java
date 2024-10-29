package com.powernode.web;

import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.model.TClue;
import com.powernode.query.ClueQuery;
import com.powernode.result.R;
import com.powernode.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClueController {
    @Resource
    private ClueService clueService;

    /**
     * 分页查询线索
     * @param current
     * @return
     */
    @PreAuthorize(value = "hasAnyAuthority('clue:list')")
    @GetMapping("/api/clue")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }
        PageInfo<TClue> info = clueService.getClueByPage(current);
        return R.OK(info);
    }

    /**
     * 导入excel中间中的数据
     * @param file
     * @param token
     * @return
     * @throws IOException
     */
    @PreAuthorize(value = "hasAuthority('clue:import')")
    @PostMapping("/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader(value = Constants.TOKEN_NAME) String token) throws IOException {
        clueService.importExcel(file, token);
        return R.OK();
    }

    /**
     * 验证录入的线索的手机号是否重复，不可重复
     * @param phone
     * @return
     */
    @GetMapping("/api/clue/{phone}")
    public R checkPhone(@PathVariable("phone") String phone) {
        Boolean check = clueService.checkPhone(phone);
        return check ? R.OK() : R.FAIL();
    }

    /**
     * 提交新增的线索
     * @param clueQuery
     * @param token
     * @return
     */
    @PreAuthorize(value = "hasAuthority('clue:add')")
    @PostMapping("/api/clue")
    public R submitClue(ClueQuery clueQuery, @RequestHeader(Constants.TOKEN_NAME) String token) {
        clueQuery.setToken(token);
        int save = clueService.saveClue(clueQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 加载要编辑的线索数据
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasAuthority('clue:view')")
    @GetMapping("/api/clue/detail/{id}")
    public R loadClue(@PathVariable("id") Integer id) {
        TClue tClue = clueService.getClueById(id);
        return R.OK(tClue);
    }

    /**
     * 编辑线索数据
     * @param clueQuery
     * @param token
     * @return
     */
    @PreAuthorize(value = "hasAuthority('clue:edit')")
    @PutMapping("/api/clue")
    public R editClue(ClueQuery clueQuery, @RequestHeader(Constants.TOKEN_NAME) String token) {
        clueQuery.setToken(token);
        int edit = clueService.editClue(clueQuery);
        return edit >= 1 ? R.OK() : R.FAIL();
    }

    @PreAuthorize(value = "hasAuthority('clue:delete')")
    @DeleteMapping(value = "/api/clue/{id}")
    public R deleteUser(@PathVariable(value = "id") Integer id) {
        int del = clueService.delClueById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }
}
