package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.kh.entity.KhBCopyScientificprize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自任职以来科研获奖情况 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-13
 */
public interface KhBCopyScientificprizeMapper extends BaseMapper<KhBCopyScientificprize> {
        void updateKhBCopyScientificprize(KhBCopyScientificprize khBCopyScientificprize);
        IPage<KhBCopyScientificprize> findKhBCopyScientificprize(Page page, @Param("khBCopyScientificprize") KhBCopyScientificprize khBCopyScientificprize);
        }
