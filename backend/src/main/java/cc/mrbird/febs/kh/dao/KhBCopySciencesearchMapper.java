package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhBCopySciencesearch;
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
 * @since 2022-05-12
 */
public interface KhBCopySciencesearchMapper extends BaseMapper<KhBCopySciencesearch> {
        void updateKhBCopySciencesearch(KhBCopySciencesearch khBCopySciencesearch);
        IPage<KhBCopySciencesearch> findKhBCopySciencesearch(Page page, @Param("khBCopySciencesearch") KhBCopySciencesearch khBCopySciencesearch);
        }
