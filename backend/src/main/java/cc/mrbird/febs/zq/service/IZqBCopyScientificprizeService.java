package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopyScientificprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 自任职以来科研获奖情况 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopyScientificprizeService extends IService<ZqBCopyScientificprize> {

        IPage<ZqBCopyScientificprize> findZqBCopyScientificprizes(QueryRequest request, ZqBCopyScientificprize zqBCopyScientificprize);

        IPage<ZqBCopyScientificprize> findZqBCopyScientificprizeList(QueryRequest request, ZqBCopyScientificprize zqBCopyScientificprize);

        void createZqBCopyScientificprize(ZqBCopyScientificprize zqBCopyScientificprize);

        void updateZqBCopyScientificprize(ZqBCopyScientificprize zqBCopyScientificprize);

        void deleteZqBCopyScientificprizes(String[]Ids);

        List<ZqBCopyScientificprize> getAll(String userAccount,String dcaYear);
        }
