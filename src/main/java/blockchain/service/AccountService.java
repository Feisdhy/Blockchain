package blockchain.service;

import blockchain.entity.account.ExternalAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AccountService extends IService<ExternalAccount> {
    List<ExternalAccount> getAccountList(List<String> list);
}
