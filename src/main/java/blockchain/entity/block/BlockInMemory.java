package blockchain.entity.block;

import blockchain.entity.transaction.TransferTransaction;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Accessors(chain = true)
public record BlockInMemory(String hash, List<TransferTransaction> list) implements Block {
}
