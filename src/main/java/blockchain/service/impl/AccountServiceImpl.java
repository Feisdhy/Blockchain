package blockchain.service.impl;

import blockchain.entity.account.Account;
import blockchain.entity.account.ExternalAccount;
import blockchain.mapper.AccountMapper;
import blockchain.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, ExternalAccount> implements AccountService {
    @Resource
    AccountMapper mapper;

    @Override
    public List<ExternalAccount> getAccountList(List<String> list) {
        QueryWrapper<ExternalAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("*")
                .in("address", list);
        return mapper.selectList(queryWrapper);
    }
}
