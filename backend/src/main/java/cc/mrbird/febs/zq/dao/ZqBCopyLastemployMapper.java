package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopyLastemploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 完成上一聘期 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface ZqBCopyLastemployMapper extends BaseMapper<ZqBCopyLastemploy> {
        void updateZqBCopyLastemploy(ZqBCopyLastemploy zqBCopyLastemploy);
        IPage<ZqBCopyLastemploy> findZqBCopyLastemploy(Page page, @Param("zqBCopyLastemploy") ZqBCopyLastemploy zqBCopyLastemploy);
        }
