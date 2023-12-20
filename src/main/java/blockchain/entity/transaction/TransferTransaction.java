package blockchain.entity.transaction;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@TableName("transaction")
@Accessors(chain = true)
public record TransferTransaction(
        @TableId String hash,
        @TableField(value = "address_from") String addressFrom,
        @TableField(value = "address_to") String addressTo,
        @TableField Double amount
) implements Transaction, Serializable {
    @Override
    public int getTransactionType() {
        return 0;
    }
}
