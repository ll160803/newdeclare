package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopyGoal;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-24
 */
public interface IZqBCopyGoalService extends IService<ZqBCopyGoal> {

        IPage<ZqBCopyGoal> findZqBCopyGoals(QueryRequest request, ZqBCopyGoal zqBCopyGoal);

        IPage<ZqBCopyGoal> findZqBCopyGoalList(QueryRequest request, ZqBCopyGoal zqBCopyGoal);

        void createZqBCopyGoal(ZqBCopyGoal zqBCopyGoal);

        void updateZqBCopyGoal(ZqBCopyGoal zqBCopyGoal);

        void deleteZqBCopyGoals(String[]Ids);

        List<ZqBCopyGoal> getAll(String userAccount,String dcaYear);
        }
