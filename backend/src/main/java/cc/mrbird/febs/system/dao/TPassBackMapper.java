package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.system.entity.TPassBack;
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
 * @since 2023-01-12
 */
public interface TPassBackMapper extends BaseMapper<TPassBack> {
        void updateTPassBack(TPassBack tPassBack);
        IPage<TPassBack> findTPassBack(Page page, @Param("tPassBack") TPassBack tPassBack);
        }
