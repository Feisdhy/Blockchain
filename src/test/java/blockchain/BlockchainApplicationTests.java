package blockchain;

import blockchain.entity.account.ExternalAccount;
import blockchain.entity.block.BlockInDataBase;
import blockchain.entity.block.BlockInMemory;
import blockchain.entity.transaction.TransferTransaction;
import blockchain.mapper.AccountMapper;
import blockchain.mapper.TransactionMapper;
import blockchain.service.AccountService;
import blockchain.service.BlockService;
import blockchain.service.TransactionService;
import blockchain.util.HashUtils;
import blockchain.util.TransactionUtils;
import blockchain.util.TypeTransitionUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@SpringBootTest
class BlockchainApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    AccountService accountService;

    @Resource
    TransactionService transactionService;

    @Resource
    BlockService blockService;

    @Test
    void saveAccount() {
        for (int i = 0; i < 10; i ++ ) {
            List<ExternalAccount> accounts = new ArrayList<>();
            for (int j = 0; j < 100000; j ++ ) accounts.add(new ExternalAccount());
            log.info("开始插入第" + (i + 1) + "批数据");
            long start = System.currentTimeMillis();
            if (accountService.saveBatch(accounts)) log.info("插入成功！");
            else log.info("插入失败！");
            long end = System.currentTimeMillis();
            log.info("插入所需时间：" + (end - start) + "微秒");
        }
    }

    @Test
    void saveTransaction() {
        log.info("开始获取账户信息！");
        List<ExternalAccount> accounts = accountService.list();
        log.info("获取账户信息完成！");
        List<TransferTransaction> transactions = new ArrayList<>();
        for (int i = 0; i < 5; i ++ ) {
            for (int j = 0; j < 100000; j ++ ) {
                SecureRandom random1 = new SecureRandom(), random2 = new SecureRandom();
                int idx1 = random1.nextInt(1000000), idx2 = random2.nextInt(1000000);
                if (idx1 == idx2) j -- ;
                else {
                    TransferTransaction transaction = new TransferTransaction(
                            TypeTransitionUtils.bytesToHex(TransactionUtils.generateHash()),
                            accounts.get(idx1).getAddress(),
                            accounts.get(idx2).getAddress(),
                            1e15
                    );
                    transactions.add(transaction);
                }
            }
            log.info("开始插入第" + (i + 1) + "批数据");
            long start = System.currentTimeMillis();
            if (transactionService.saveBatch(transactions)) log.info("插入成功！");
            else log.info("插入失败！");
            long end = System.currentTimeMillis();
            log.info("插入所需时间：" + (end - start) + "微秒");
            transactions.clear();
        }
    }

    @Test
    void saveBlockInDataBase() {
        log.info("开始获取交易信息！");
        List<TransferTransaction> transactions = transactionService.list();
        log.info("获取交易信息完成！");
        List<BlockInDataBase> blocks = new ArrayList<>();
        for (int i = 0, idx = 0; i < 1000; i ++ ) {
            String hash = TypeTransitionUtils.bytesToHex(HashUtils.generateHash());
            for (int j = 0; j < 500; j ++ , idx ++ ) {
                BlockInDataBase block = new BlockInDataBase(hash, transactions.get(idx).hash());
                blocks.add(block);
            }
            log.info("开始插入第" + (i + 1) + "批数据");
            long start = System.currentTimeMillis();
            if (blockService.saveBatch(blocks)) log.info("插入成功！");
            else log.info("插入失败！");
            long end = System.currentTimeMillis();
            log.info("插入所需时间：" + (end - start) + "微秒");
            blocks.clear();
        }
    }

    private Map<String, List<String>> getBlocks() {
        List<BlockInDataBase> blocks = blockService.list();
        Map<String, List<String>> map = new HashMap<>();
        blocks.forEach(block -> {
            String bhash = block.getBhash(), thash = block.getThash();
            if (map.containsKey(bhash)) map.get(bhash).add(thash);
            else {
                List<String> list = new ArrayList<>();
                list.add(thash);
                map.put(bhash, list);
            }
        });
        System.gc();
        return map;
    }

    private List<BlockInMemory> getBlockInMemory() {
        Map<String, List<String>> map = getBlocks();
        List<BlockInMemory> list = new ArrayList<>();
        map.forEach((key, value) -> {
            BlockInMemory block = new BlockInMemory(key, transactionService.getTransactionList(value));
            list.add(block);
        });
        return list;
    }

    @Test
    void executionEditionOne() {
        log.info("开始获取区块信息！");
        List<BlockInMemory> blocks = getBlockInMemory();
        log.info("获取区块信息完成！");
        Map<String, ExternalAccount> map = new TreeMap<>();
        List<ExternalAccount> commitList = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            // log.info("区块" + (i + 1) + "信息");
            BlockInMemory block = blocks.get(i);

            List<String> searchList = new ArrayList<>();

            long time1 = System.nanoTime();
            block.list().forEach(transaction -> {
                String from = transaction.addressFrom(), to = transaction.addressTo();
                if (!map.containsKey(from)) searchList.add(from);
                else commitList.add(map.get(from));
                if (!map.containsKey(to)) searchList.add(to);
                else commitList.add(map.get(to));

            });
            long result1 = System.nanoTime() - time1;
            // log.info("获取未命中的账户时间：" + result1);

            time1 = System.nanoTime();
            List<ExternalAccount> accounts = accountService.getAccountList(searchList);
            accounts.forEach(account -> {
                map.put(account.getAddress(), account);
                commitList.add(account);
            });
            long result2 = System.nanoTime() - time1;
            // log.info("访问外存，并将未命中的账户存储到内存的时间：" + result2);

            time1 = System.nanoTime();
            block.list().forEach(transaction -> {
                double amount = transaction.amount();
                String from = transaction.addressFrom(), to = transaction.addressTo();
                ExternalAccount account1 = map.get(from), account2 = map.get(to);
                account1.transfer(-amount);
                account2.transfer(+amount);
            });
            long result3 = System.nanoTime() - time1;
            // log.info("执行时间：" + result3);

            long result4 = 0;
            if ((i + 1) % 5 == 0) {
                time1 = System.nanoTime();
                accountService.updateBatchById(commitList);
                result4 = System.nanoTime() - time1;
                // log.info("提交时间：" + result4);
                commitList.clear();
            }

            if ((i + 1) % 100 == 0) {
                // map.clear();
                System.gc();
            }

            // System.out.println();
            log.info("区块" + (i + 1) + "信息： " + result1 + " " + result2 + " " + result3 + " " + result4);
        }
    }
}
