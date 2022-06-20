package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhBCopyPublicarticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-13
 */
public interface KhBCopyPublicarticleMapper extends BaseMapper<KhBCopyPublicarticle> {
        void updateKhBCopyPublicarticle(KhBCopyPublicarticle khBCopyPublicarticle);
        IPage<KhBCopyPublicarticle> findKhBCopyPublicarticle(Page page, @Param("khBCopyPublicarticle") KhBCopyPublicarticle khBCopyPublicarticle);
        }
