package cc.mrbird.febs.system.service;

import cc.mrbird.febs.system.entity.TPassBack;
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
 * @since 2023-01-12
 */
public interface ITPassBackService extends IService<TPassBack> {

        IPage<TPassBack> findTPassBacks(QueryRequest request, TPassBack tPassBack);

        IPage<TPassBack> findTPassBackList(QueryRequest request, TPassBack tPassBack);

        void createTPassBack(TPassBack tPassBack);

        void updateTPassBack(TPassBack tPassBack);

        void deleteTPassBacks(String[]Ids);

        List<TPassBack> getAll(String userAccount,String dcaYear);
        }
