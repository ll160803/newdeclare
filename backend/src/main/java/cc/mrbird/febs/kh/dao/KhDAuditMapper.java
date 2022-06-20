package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhDAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 打分人 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface KhDAuditMapper extends BaseMapper<KhDAudit> {
        void updateKhDAudit(KhDAudit khDAudit);
        IPage<KhDAudit> findKhDAudit(Page page, @Param("khDAudit") KhDAudit khDAudit);
        }
