package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBSciachievement;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 主要科研业绩 服务类
 * </p>
 *
 * @author viki
 * @since 2021-09-14
 */
public interface IDcaBSciachievementService extends IService<DcaBSciachievement> {

        IPage<DcaBSciachievement> findDcaBSciachievements(QueryRequest request, DcaBSciachievement dcaBSciachievement);

        IPage<DcaBSciachievement> findDcaBSciachievementList(QueryRequest request, DcaBSciachievement dcaBSciachievement);

        void createDcaBSciachievement(DcaBSciachievement dcaBSciachievement);

        void updateDcaBSciachievement(DcaBSciachievement dcaBSciachievement);

        void deleteDcaBSciachievements(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
