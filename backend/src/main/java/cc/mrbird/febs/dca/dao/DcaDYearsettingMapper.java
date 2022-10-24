package cc.mrbird.febs.dca.dao;

import cc.mrbird.febs.dca.entity.DcaDYearsetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 申报年度设置 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-10-20
 */
public interface DcaDYearsettingMapper extends BaseMapper<DcaDYearsetting> {
        void updateDcaDYearsetting(DcaDYearsetting dcaDYearsetting);
        IPage<DcaDYearsetting> findDcaDYearsetting(Page page, @Param("dcaDYearsetting") DcaDYearsetting dcaDYearsetting);
        }
