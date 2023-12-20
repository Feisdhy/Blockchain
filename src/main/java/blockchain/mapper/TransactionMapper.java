package blockchain.mapper;

import blockchain.entity.account.ExternalAccount;
import blockchain.entity.transaction.TransferTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TransactionMapper extends BaseMapper<TransferTransaction> {
}
