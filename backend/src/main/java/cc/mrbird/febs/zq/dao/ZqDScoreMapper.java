package cc.mrbird.febs.zq.dao;

import cc.mrbird.febs.zq.entity.ZqDScore;
import cc.mrbird.febs.zq.entity.ZqUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 打分记录 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface ZqDScoreMapper extends BaseMapper<ZqDScore> {
        void updateZqDScore(ZqDScore zqDScore);
        IPage<ZqDScore> findZqDScore(Page page, @Param("zqDScore") ZqDScore zqDScore);
        void insertZqCopy(Map<String,Object> map);
        IPage<ZqUser> getAllUserInfo(Page page, @Param("zqDScore") ZqDScore zqDScore);
        }
