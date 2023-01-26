package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountCreateForm;
import com.vti.form.AccountFilterForm;
import com.vti.form.AccountUpdateForm;
import com.vti.form.AuthChangePasswordForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAccountService extends UserDetailsService {
    Page<Account> findAll(Pageable pageable, AccountFilterForm form);

    Account findById(Integer id);

    void create(AccountCreateForm form);

    void updateById(AccountUpdateForm form);

    void deleteById(Integer id);

    Account findByUsername(String username);

    void deleteAll(List<Integer> ids);

    void changePassword(AuthChangePasswordForm form);
}
