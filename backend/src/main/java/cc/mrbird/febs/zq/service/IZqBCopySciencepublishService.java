package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopySciencepublish;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 科研论文 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopySciencepublishService extends IService<ZqBCopySciencepublish> {

        IPage<ZqBCopySciencepublish> findZqBCopySciencepublishs(QueryRequest request, ZqBCopySciencepublish zqBCopySciencepublish);

        IPage<ZqBCopySciencepublish> findZqBCopySciencepublishList(QueryRequest request, ZqBCopySciencepublish zqBCopySciencepublish);

        void createZqBCopySciencepublish(ZqBCopySciencepublish zqBCopySciencepublish);

        void updateZqBCopySciencepublish(ZqBCopySciencepublish zqBCopySciencepublish);

        void deleteZqBCopySciencepublishs(String[]Ids);

        List<ZqBCopySciencepublish> getAll(String userAccount,String dcaYear);
        }
