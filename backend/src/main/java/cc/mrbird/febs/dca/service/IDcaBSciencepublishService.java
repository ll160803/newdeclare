package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;
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
 * @since 2020-10-20
 */
public interface IDcaBSciencepublishService extends IService<DcaBSciencepublish> {

        IPage<DcaBSciencepublish> findDcaBSciencepublishs(QueryRequest request, DcaBSciencepublish dcaBSciencepublish);

        IPage<DcaBCopySciencepublish> findDcaBCopySciencepublishs(QueryRequest request, DcaBSciencepublish dcaBSciencepublish);
        IPage<DcaBSciencepublish> findDcaBSciencepublishList(QueryRequest request, DcaBSciencepublish dcaBSciencepublish);

        void createDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish);

        void updateDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish);

        void deleteDcaBSciencepublishs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        void deleteRealByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);

        void updateStateByUserAccount(String userAccount);
        }
