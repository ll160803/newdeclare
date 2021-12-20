package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySciachievement;
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
 * @since 2021-09-30
 */
public interface IDcaBCopySciachievementService extends IService<DcaBCopySciachievement> {

        IPage<DcaBCopySciachievement> findDcaBCopySciachievements(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement);

        IPage<DcaBCopySciachievement> findDcaBCopySciachievementList(QueryRequest request, DcaBCopySciachievement dcaBCopySciachievement);

        void createDcaBCopySciachievement(DcaBCopySciachievement dcaBCopySciachievement);

        void updateDcaBCopySciachievement(DcaBCopySciachievement dcaBCopySciachievement);

        void deleteDcaBCopySciachievements(String[]Ids);

        List<DcaBCopySciachievement> getAll(String userAccount,String dcaYear, String gwDj);
        }
