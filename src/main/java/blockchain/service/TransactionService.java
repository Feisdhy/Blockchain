package blockchain.service;

import blockchain.entity.transaction.TransferTransaction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TransactionService extends IService<TransferTransaction> {
    List<TransferTransaction> getTransactionList(List<String> list);
}
