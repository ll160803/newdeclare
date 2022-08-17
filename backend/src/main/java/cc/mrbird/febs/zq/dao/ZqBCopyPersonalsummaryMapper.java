package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopyPersonalsummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 个人总结 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface ZqBCopyPersonalsummaryMapper extends BaseMapper<ZqBCopyPersonalsummary> {
        void updateZqBCopyPersonalsummary(ZqBCopyPersonalsummary zqBCopyPersonalsummary);
        IPage<ZqBCopyPersonalsummary> findZqBCopyPersonalsummary(Page page, @Param("zqBCopyPersonalsummary") ZqBCopyPersonalsummary zqBCopyPersonalsummary);
        }
