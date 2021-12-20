package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopySureachievement;
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
 * @since 2021-09-30
 */
public interface IDcaBCopySureachievementService extends IService<DcaBCopySureachievement> {

        IPage<DcaBCopySureachievement> findDcaBCopySureachievements(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement);

        IPage<DcaBCopySureachievement> findDcaBCopySureachievementList(QueryRequest request, DcaBCopySureachievement dcaBCopySureachievement);

        void createDcaBCopySureachievement(DcaBCopySureachievement dcaBCopySureachievement);

        void updateDcaBCopySureachievement(DcaBCopySureachievement dcaBCopySureachievement);

        void deleteDcaBCopySureachievements(String[]Ids);

        List<DcaBCopySureachievement> getAll(String userAccount,String dcaYear, String gwDj);
        }
