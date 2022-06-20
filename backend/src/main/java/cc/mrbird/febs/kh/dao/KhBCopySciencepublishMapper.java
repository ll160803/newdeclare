package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhBCopySciencepublish;
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
 * @since 2022-05-12
 */
public interface KhBCopySciencepublishMapper extends BaseMapper<KhBCopySciencepublish> {
        void updateKhBCopySciencepublish(KhBCopySciencepublish khBCopySciencepublish);
        IPage<KhBCopySciencepublish> findKhBCopySciencepublish(Page page, @Param("khBCopySciencepublish") KhBCopySciencepublish khBCopySciencepublish);
        }
