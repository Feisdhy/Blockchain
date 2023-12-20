package blockchain.mapper;

import blockchain.entity.block.BlockInDataBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockMapper extends BaseMapper<BlockInDataBase> {

}
