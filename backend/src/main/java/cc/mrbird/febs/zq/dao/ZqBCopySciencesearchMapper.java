package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopySciencesearch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 科研项目 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface ZqBCopySciencesearchMapper extends BaseMapper<ZqBCopySciencesearch> {
        void updateZqBCopySciencesearch(ZqBCopySciencesearch zqBCopySciencesearch);
        IPage<ZqBCopySciencesearch> findZqBCopySciencesearch(Page page, @Param("zqBCopySciencesearch") ZqBCopySciencesearch zqBCopySciencesearch);
        }
