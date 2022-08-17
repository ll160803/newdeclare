package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopyEmploy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 任职培养 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopyEmployService extends IService<ZqBCopyEmploy> {

        IPage<ZqBCopyEmploy> findZqBCopyEmploys(QueryRequest request, ZqBCopyEmploy zqBCopyEmploy);

        IPage<ZqBCopyEmploy> findZqBCopyEmployList(QueryRequest request, ZqBCopyEmploy zqBCopyEmploy);

        void createZqBCopyEmploy(ZqBCopyEmploy zqBCopyEmploy);

        void updateZqBCopyEmploy(ZqBCopyEmploy zqBCopyEmploy);

        void deleteZqBCopyEmploys(String[]Ids);

        List<ZqBCopyEmploy> getAll(String userAccount,String dcaYear);
        }
