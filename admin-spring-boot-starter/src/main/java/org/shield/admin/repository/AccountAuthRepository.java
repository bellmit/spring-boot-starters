package org.shield.admin.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import org.shield.admin.enums.AccountAuthSource;
import org.shield.admin.mapper.AdminAccountAuthMapper;
import org.shield.admin.model.AdminAccountAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import cn.hutool.core.collection.CollUtil;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Repository
public class AccountAuthRepository {

    @Autowired
    AdminAccountAuthMapper authMapper;

    /**
     * 根据认证方式和认证信息查询账号编号
     *
     * @param source
     * @param sourceId
     * @return
     */
    public Set<String> findAccountsBySource(Integer source, String sourceId) {
        if (ObjectUtils.isEmpty(sourceId)) {
            return new HashSet<>();
        }
        Condition condition = new Condition(AdminAccountAuth.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("source", source);
        criteria.andEqualTo("sourceId", sourceId);
        return authMapper.selectByCondition(condition).stream().map(AdminAccountAuth::getAccountId)
                .collect(Collectors.toSet());
    }


    /**
     * 根据用户名或手机号查询账号编号
     *
     * @param username
     * @param phone
     * @return
     */
    public Set<String> findAccounsByUsernamePhone(String username, String phone) {
        Set<String> usernameSet = findAccountsBySource(AccountAuthSource.USERNAME.getValue(), username);
        Set<String> phoneSet = findAccountsBySource(AccountAuthSource.PHONE.getValue(), phone);

        if (!ObjectUtils.isEmpty(username) && !ObjectUtils.isEmpty(phone)) {
            return CollUtil.intersectionDistinct(usernameSet, phoneSet);
        }
        return CollUtil.unionDistinct(usernameSet, phoneSet);
    }

    /**
     * 根据账号编号列表查询认证方式
     *
     * @param accounts
     * @return
     */
    public List<AdminAccountAuth> findAuthsByAccounts(Set<String> accounts) {
        if(accounts.isEmpty()){
            return new ArrayList<>();
        }
        Condition condition = new Condition(AdminAccountAuth.class);
        Criteria criteria = condition.createCriteria();
        criteria.andIn("accountId", accounts);
        Integer[] arr = {AccountAuthSource.USERNAME.getValue(), AccountAuthSource.PHONE.getValue()};
        criteria.andIn("source", Arrays.stream(arr).collect(Collectors.toList()));
        return authMapper.selectByCondition(condition);
    }
}
