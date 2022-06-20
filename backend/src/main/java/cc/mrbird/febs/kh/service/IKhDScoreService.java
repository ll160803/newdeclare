package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.kh.entity.KhDScore;
import cc.mrbird.febs.kh.entity.KhScore;
import cc.mrbird.febs.kh.entity.KhUser;
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
 * @since 2022-05-12
 */
public interface IKhDScoreService extends IService<KhDScore> {

        IPage<KhDScore> findKhDScores(QueryRequest request, KhDScore khDScore);

        IPage<KhDScore> findKhDScoreList(QueryRequest request, KhDScore khDScore);

        void createKhDScore(KhDScore khDScore);

        void updateKhDScore(KhDScore khDScore);

        void deleteKhDScores(String[]Ids);

        List<KhDScore> getAll(String userAccount,String dcaYear);

       void insert(Map<String, Object> map);

        IPage<KhUser> findAllUserInfo(QueryRequest request, KhDScore khDScore);

    IPage<KhUser> getUserInfoReport(QueryRequest request, KhDScore khDScore);

    List<KhScore> getUserInfoReportScore( KhDScore khDScore);
        }
