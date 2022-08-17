package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopyEmploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 任职培养 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface ZqBCopyEmployMapper extends BaseMapper<ZqBCopyEmploy> {
        void updateZqBCopyEmploy(ZqBCopyEmploy zqBCopyEmploy);
        IPage<ZqBCopyEmploy> findZqBCopyEmploy(Page page, @Param("zqBCopyEmploy") ZqBCopyEmploy zqBCopyEmploy);
        }
