package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDYearsetting;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 申报年度设置 服务类
 * </p>
 *
 * @author viki
 * @since 2022-10-20
 */
public interface IDcaDYearsettingService extends IService<DcaDYearsetting> {

        IPage<DcaDYearsetting> findDcaDYearsettings(QueryRequest request, DcaDYearsetting dcaDYearsetting);

        IPage<DcaDYearsetting> findDcaDYearsettingList(QueryRequest request, DcaDYearsetting dcaDYearsetting);

        void createDcaDYearsetting(DcaDYearsetting dcaDYearsetting);

        void updateDcaDYearsetting(DcaDYearsetting dcaDYearsetting);

        void deleteDcaDYearsettings(String[]Ids);

        }
