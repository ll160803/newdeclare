package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopyLastemploy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 完成上一聘期 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopyLastemployService extends IService<ZqBCopyLastemploy> {

        IPage<ZqBCopyLastemploy> findZqBCopyLastemploys(QueryRequest request, ZqBCopyLastemploy zqBCopyLastemploy);

        IPage<ZqBCopyLastemploy> findZqBCopyLastemployList(QueryRequest request, ZqBCopyLastemploy zqBCopyLastemploy);

        void createZqBCopyLastemploy(ZqBCopyLastemploy zqBCopyLastemploy);

        void updateZqBCopyLastemploy(ZqBCopyLastemploy zqBCopyLastemploy);

        void deleteZqBCopyLastemploys(String[]Ids);

        List<ZqBCopyLastemploy> getAll(String userAccount,String dcaYear);
        }
