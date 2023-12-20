package blockchain.service.impl;

import blockchain.entity.account.ExternalAccount;
import blockchain.entity.transaction.TransferTransaction;
import blockchain.mapper.AccountMapper;
import blockchain.mapper.TransactionMapper;
import blockchain.service.AccountService;
import blockchain.service.TransactionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, TransferTransaction> implements TransactionService {
    @Resource
    TransactionMapper mapper;

    @Override
    public List<TransferTransaction> getTransactionList(List<String> list) {
        QueryWrapper<TransferTransaction> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("*")
                .in("hash", list);
        return mapper.selectList(queryWrapper);
    }
}
