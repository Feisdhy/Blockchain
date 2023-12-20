package blockchain.entity.account;

import blockchain.util.AccountUtils;
import blockchain.util.TypeTransitionUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@ToString
@TableName("account")
@Accessors(chain = true)
public class ExternalAccount implements Account, Serializable {
    @TableId
    private final String address;

    @Setter
    @TableField
    private Double balance;

    public ExternalAccount() {
        this.address = TypeTransitionUtils.bytesToHex(AccountUtils.generateAddress());
        this.balance = 1e19;
    }

    @Override
    public int getAccountType() {
        return 0;
    }

    public void transfer(Double amount) {
        this.balance += amount;
    }
}
