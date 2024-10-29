package com.powernode.service;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TCustomer;
import com.powernode.query.CustomerQuery;
import com.powernode.result.CustomerExcel;

import java.util.List;

public interface CustomerService {
    Boolean convertCustomer(CustomerQuery customerQuery);

    PageInfo<TCustomer> getCustomers(Integer current);

    List<CustomerExcel> getCustomersByExcel(List<String> idList);
}
