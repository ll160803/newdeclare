package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopyScientificprize;
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
 * @since 2022-06-24
 */
public interface ZqBCopyScientificprizeMapper extends BaseMapper<ZqBCopyScientificprize> {
        void updateZqBCopyScientificprize(ZqBCopyScientificprize zqBCopyScientificprize);
        IPage<ZqBCopyScientificprize> findZqBCopyScientificprize(Page page, @Param("zqBCopyScientificprize") ZqBCopyScientificprize zqBCopyScientificprize);
        }
