package cc.mrbird.febs.kh.dao;

import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.kh.entity.KhDScore;
import cc.mrbird.febs.kh.entity.KhScore;
import cc.mrbird.febs.kh.entity.KhUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 打分记录 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface KhDScoreMapper extends BaseMapper<KhDScore> {
        void updateKhDScore(KhDScore khDScore);
        IPage<KhDScore> findKhDScore(Page page, @Param("khDScore") KhDScore khDScore);
        void insertKhCopy(Map<String,Object> map);

        IPage<KhUser> getAllUserInfo(Page page, @Param("khDScore") KhDScore khDScore);

    IPage<KhUser>  getUserInfoReport(Page page, @Param("khDScore") KhDScore khDScore);
    List<KhScore> getUserInfoReportScore(@Param("khDScore") KhDScore khDScore);
        }
