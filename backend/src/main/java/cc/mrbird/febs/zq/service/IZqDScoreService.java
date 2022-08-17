package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqDScore;
import cc.mrbird.febs.zq.entity.ZqUser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 打分记录 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqDScoreService extends IService<ZqDScore> {

        IPage<ZqDScore> findZqDScores(QueryRequest request, ZqDScore zqDScore);

        IPage<ZqDScore> findZqDScoreList(QueryRequest request, ZqDScore zqDScore);

        void createZqDScore(ZqDScore zqDScore);

        void updateZqDScore(ZqDScore zqDScore);

        void deleteZqDScores(String[]Ids);

        List<ZqDScore> getAll(String userAccount,String dcaYear);

        void insert(Map<String, Object> map);

        IPage<ZqUser> findAllUserInfo(QueryRequest request, ZqDScore khDScore);
        }
