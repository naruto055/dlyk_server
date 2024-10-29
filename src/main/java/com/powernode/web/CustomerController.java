package com.powernode.web;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.model.TCustomer;
import com.powernode.query.CustomerQuery;
import com.powernode.result.CustomerExcel;
import com.powernode.result.R;
import com.powernode.service.CustomerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 将线索转换为客户
     * @param customerQuery
     * @param token
     * @return
     */
    @PostMapping("/api/clue/customer")
    public R converCustomer(@RequestBody CustomerQuery customerQuery, @RequestHeader(Constants.TOKEN_NAME) String token) {
        customerQuery.setToken(token);
        Boolean convert = customerService.convertCustomer(customerQuery);
        return convert ? R.OK() : R.FAIL();
    }

    /**
     * 获取客户列表信息
     * @param current
     * @return
     */
    @GetMapping("/api/customers")
    public R getCustomers(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) current = 1;
        PageInfo<TCustomer> pageInfo = customerService.getCustomers(current);
        return R.OK(pageInfo);
    }

    /**
     * 导出excel
     * @param response
     * @throws IOException
     */
    @GetMapping("/api/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam(value = "ids", required = false) String ids) throws IOException {
        // 要想让浏览器弹出下载框，后端需要设置一个响应头的信息
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(Constants.EXCEL_FILE_NAME + System.currentTimeMillis(), "utf-8") + ".xlsx");

        List<String> list = StringUtils.hasText(ids) ? Arrays.asList(ids.split(",")) : new ArrayList<>();

        // 查询数据库，把数据写入excel，再把excel以IO流的形式输出到前端
        List<CustomerExcel> dataList = customerService.getCustomersByExcel(list);
        EasyExcel.write(response.getOutputStream(), CustomerExcel.class)
                .sheet()
                .doWrite(dataList);
    }
}
