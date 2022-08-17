package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqBCopyGoal;
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
 * @since 2022-06-24
 */
public interface ZqBCopyGoalMapper extends BaseMapper<ZqBCopyGoal> {
        void updateZqBCopyGoal(ZqBCopyGoal zqBCopyGoal);
        IPage<ZqBCopyGoal> findZqBCopyGoal(Page page, @Param("zqBCopyGoal") ZqBCopyGoal zqBCopyGoal);
        }
