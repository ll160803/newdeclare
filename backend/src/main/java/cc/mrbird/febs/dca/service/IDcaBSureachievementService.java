package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBSureachievement;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 医疗认可 服务类
 * </p>
 *
 * @author viki
 * @since 2021-09-15
 */
public interface IDcaBSureachievementService extends IService<DcaBSureachievement> {

        IPage<DcaBSureachievement> findDcaBSureachievements(QueryRequest request, DcaBSureachievement dcaBSureachievement);

        IPage<DcaBSureachievement> findDcaBSureachievementList(QueryRequest request, DcaBSureachievement dcaBSureachievement);

        void createDcaBSureachievement(DcaBSureachievement dcaBSureachievement);

        void updateDcaBSureachievement(DcaBSureachievement dcaBSureachievement);

        void deleteDcaBSureachievements(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
