package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhBCopyPersonalsummary;
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
 * @since 2022-05-12
 */
public interface KhBCopyPersonalsummaryMapper extends BaseMapper<KhBCopyPersonalsummary> {
        void updateKhBCopyPersonalsummary(KhBCopyPersonalsummary khBCopyPersonalsummary);
        IPage<KhBCopyPersonalsummary> findKhBCopyPersonalsummary(Page page, @Param("khBCopyPersonalsummary") KhBCopyPersonalsummary khBCopyPersonalsummary);
        }
