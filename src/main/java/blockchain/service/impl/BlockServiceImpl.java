package blockchain.service.impl;

import blockchain.entity.block.BlockInDataBase;
import blockchain.mapper.BlockMapper;
import blockchain.service.BlockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlockServiceImpl extends ServiceImpl<BlockMapper, BlockInDataBase> implements BlockService {
}
