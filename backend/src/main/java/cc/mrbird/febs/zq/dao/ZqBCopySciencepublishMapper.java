package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopySciencepublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 科研论文 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface ZqBCopySciencepublishMapper extends BaseMapper<ZqBCopySciencepublish> {
        void updateZqBCopySciencepublish(ZqBCopySciencepublish zqBCopySciencepublish);
        IPage<ZqBCopySciencepublish> findZqBCopySciencepublish(Page page, @Param("zqBCopySciencepublish") ZqBCopySciencepublish zqBCopySciencepublish);
        }
