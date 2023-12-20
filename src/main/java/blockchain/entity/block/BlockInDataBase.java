package blockchain.entity.block;

import blockchain.entity.transaction.TransferTransaction;
import blockchain.util.HashUtils;
import blockchain.util.TypeTransitionUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@ToString
@TableName("block")
@Accessors(chain = true)
public class BlockInDataBase implements Block {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private final String bhash;

    @TableField
    private final String thash;

    public BlockInDataBase(String bhash, String thash) {
        this.bhash = bhash;
        this.thash = thash;
    }
}