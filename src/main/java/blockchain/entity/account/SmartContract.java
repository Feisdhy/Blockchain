package blockchain.entity.account;

import blockchain.util.AccountUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@ToString
@Accessors(chain = true)
public class SmartContract implements Account, Serializable {
    private final byte[] address;

    @Setter
    private Double balance;

    public SmartContract() {
        this.address = AccountUtils.generateAddress();
        this.balance = 1e19;
    }

    @Override
    public int getAccountType() {
        return 1;
    }

    public void transfer(Double amount) {
        this.balance += amount;
    }
}
